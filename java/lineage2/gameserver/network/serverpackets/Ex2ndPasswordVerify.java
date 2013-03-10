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
package lineage2.gameserver.network.serverpackets;

/**
 * @author Mobius
 * @version $Revision: 1.0 $
 */
public class Ex2ndPasswordVerify extends L2GameServerPacket
{
	/**
	 * Field PASSWORD_OK.
	 */
	public static final int PASSWORD_OK = 0x00;
	/**
	 * Field PASSWORD_WRONG.
	 */
	public static final int PASSWORD_WRONG = 0x01;
	/**
	 * Field PASSWORD_BAN.
	 */
	public static final int PASSWORD_BAN = 0x02;
	/**
	 * Field _wrongTentatives.
	 */
	private final int _wrongTentatives;
	/**
	 * Field _mode.
	 */
	private final int _mode;
	
	/**
	 * Constructor for Ex2ndPasswordVerify.
	 * @param mode int
	 * @param wrongTentatives int
	 */
	public Ex2ndPasswordVerify(int mode, int wrongTentatives)
	{
		_mode = mode;
		_wrongTentatives = wrongTentatives;
	}
	
	/**
	 * Method writeImpl.
	 */
	@Override
	protected void writeImpl()
	{
		writeEx(0x10a);
		writeD(_mode);
		writeD(_wrongTentatives);
	}
}