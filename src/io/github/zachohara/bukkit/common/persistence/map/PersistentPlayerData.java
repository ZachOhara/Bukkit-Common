/* Copyright (C) 2015 Zach Ohara
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.zachohara.bukkit.common.persistence.map;

import io.github.zachohara.bukkit.common.persistence.PersistentKeyedMap;
import io.github.zachohara.bukkit.common.plugin.CommonPlugin;

import java.io.IOException;
import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * A {@code PersistentPlayerData} object is responsible for loading and storing any given
 * information about a specified player as an external file, so that it remains persistent
 * through server restarts. At its core, a {@code PersistentPlayerData} object is
 * essentially a {@code PersistentKeyedMap} with the map key being a Player, and the map
 * data being some object that is generated for a player.
 *
 * @param <D> the data object that is generated for any player.
 * @author Zach Ohara
 */
public abstract class PersistentPlayerData<D extends Serializable> extends PersistentKeyedMap<Player, D> {

	/**
	 * {@code true} if the data should be stored by a player's UUID, or {@code false} if
	 * the data should be stored by a player's account name. Storing data by account name
	 * is more convienent, but the account name for any given player could potentially
	 * change in the future. By default, this value is {@code false}. At the end of
	 * construction, all player data is immediately saved.
	 */
	private boolean useUUID;

	public PersistentPlayerData(CommonPlugin owner, String filename) {
		super(owner, filename);
		this.useUUID = false;
		this.saveAllPlayerData();
	}

	/**
	 * Sets whether the data in this map is stored by player UUID.
	 *
	 * @param useUUID {@code true} if the data should be stored by a player's UUID, or
	 * {@code false} if the data should be stored by a player's account name.
	 * @see #useUUID
	 */
	public void useUUID(boolean useUUID) {
		this.useUUID = useUUID;
	}

	/**
	 * Gets whether the data in this map is stored by player UUID.
	 *
	 * @return {@code true} if the data should be stored by a player's UUID, or
	 * {@code false} if the data should be stored by a player's account name.
	 * @see #useUUID
	 */
	public boolean getUseUUID() {
		return this.useUUID;
	}

	/**
	 * Generates a map key for a given player. If the {@code useUUID} instance variable is
	 * true, the map key will be the player's UUID; otherwise, the map key will be the
	 * player's account name.
	 *
	 * @param p the {@code Player} to generate a map key for.
	 * @return the generated map key for the given {@code Player}.
	 * @see #useUUID
	 */
	@Override
	public String generateMapKey(Player p) {
		if (this.useUUID) {
			return p.getUniqueId().toString();
		} else {
			return p.getName();
		}
	}

	/**
	 * Saves relevant data for all players currently connected to the server.
	 */
	public void saveAllPlayerData() {
		this.saveAllKeyedData(Bukkit.getOnlinePlayers());
	}

	/**
	 * Calls the {@link #saveAllPlayerData()} method of this class before returning control
	 * to the overridden method. This method ensures that all data will be saved to the map
	 * before the map is saved to a file.
	 */
	@Override
	public void saveToFile() throws IOException {
		this.saveAllPlayerData();
		super.saveToFile();
	}

}
