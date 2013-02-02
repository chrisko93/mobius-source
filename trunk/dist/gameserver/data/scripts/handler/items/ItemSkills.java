/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package handler.items;

import gnu.trove.set.hash.TIntHashSet;
import lineage2.gameserver.data.xml.holder.ItemHolder;
import lineage2.gameserver.model.Creature;
import lineage2.gameserver.model.Playable;
import lineage2.gameserver.model.Player;
import lineage2.gameserver.model.Skill;
import lineage2.gameserver.model.items.ItemInstance;
import lineage2.gameserver.network.serverpackets.SystemMessage;
import lineage2.gameserver.network.serverpackets.components.SystemMsg;
import lineage2.gameserver.templates.item.ItemTemplate;

/**
 * @author Mobius
 * @version $Revision: 1.0 $
 */
public class ItemSkills extends ScriptItemHandler
{
	/**
	 * Field _itemIds.
	 */
	private final int[] _itemIds;
	
	/**
	 * Constructor for ItemSkills.
	 */
	public ItemSkills()
	{
		TIntHashSet set = new TIntHashSet();
		for (ItemTemplate template : ItemHolder.getInstance().getAllTemplates())
		{
			if (template == null)
			{
				continue;
			}
			for (Skill skill : template.getAttachedSkills())
			{
				if (skill.isHandler())
				{
					set.add(template.getItemId());
				}
			}
		}
		_itemIds = set.toArray();
	}
	
	/**
	 * Method useItem.
	 * @param playable Playable
	 * @param item ItemInstance
	 * @param ctrl boolean
	 * @return boolean * @see lineage2.gameserver.handler.items.IItemHandler#useItem(Playable, ItemInstance, boolean)
	 */
	@Override
	public boolean useItem(Playable playable, ItemInstance item, boolean ctrl)
	{
		Player player;
		if (playable.isPlayer())
		{
			player = (Player) playable;
		}
		else if (playable.isPet())
		{
			player = playable.getPlayer();
		}
		else
		{
			return false;
		}
		Skill[] skills = item.getTemplate().getAttachedSkills();
		if (item.getTemplate().isCapsuled())
		{
			if (!player.getInventory().destroyItem(item, 1))
			{
				player.sendPacket(SystemMsg.INCORRECT_ITEM_COUNT);
				return false;
			}
			player.sendPacket(new SystemMessage(SystemMessage.YOU_USE_S1).addItemName(item.getItemId()));
		}
		for (int i = 0; i < skills.length; i++)
		{
			Skill skill = skills[i];
			Creature aimingTarget = skill.getAimingTarget(player, player.getTarget());
			if (skill.checkCondition(player, aimingTarget, ctrl, false, true))
			{
				player.getAI().Cast(skill, aimingTarget, ctrl, false);
			}
			else if (i == 0)
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Method getItemIds.
	 * @return int[] * @see lineage2.gameserver.handler.items.IItemHandler#getItemIds()
	 */
	@Override
	public int[] getItemIds()
	{
		return _itemIds;
	}
}