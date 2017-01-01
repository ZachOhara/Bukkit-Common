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

package io.github.zachohara.bukkit.simpleplugin.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import io.github.zachohara.bukkit.simpleplugin.plugin.SimplePlugin;
import io.github.zachohara.bukkit.simpleplugin.plugin.main.SimplePluginMain;

/**
 * The {@code PlayerUtil} class outlines some useful static methods that are relevant to
 * players in the game.
 *
 * @author Zach Ohara
 */
public final class PlayerUtil {

	/**
	 * The {@code PlayerUtil} class should not be instantiable.
	 */
	private PlayerUtil() {

	}

	/**
	 * The UUID of the player that is the acting admin for the server that this library
	 * (and its plugins) are running on. This should be specified in the {@code config.yml}
	 * file for SimplePlugin.
	 */
	public static final UUID getAdminUUID() {
		return (UUID) SimplePlugin.getPluginInstance(SimplePluginMain.class).getConfig()
				.get("admin-uuid");
	}

	/**
	 * The in-game name of the player that is the acting admin for the server that this
	 * library (and its plugins) are running on. This should be specified in the
	 * {@code config.yml} file for SimplePlugin.
	 */
	public static final String getAdminName() {
		return (String) SimplePlugin.getPluginInstance(SimplePluginMain.class).getConfig()
				.get("admin-name");
	}

	/**
	 * Gets a {@code Player} object representing the admin of this server. {@code null} is
	 * returned if the admin is currently offline.
	 *
	 * @return the admin of this server.
	 */
	public static Player getAdmin() {
		return Bukkit.getPlayer(PlayerUtil.getAdminUUID());
	}

	/**
	 * Determines if a given player is the admin of this server by comparing UUID values.
	 *
	 * @param other the player to compare to the admin.
	 * @return {@code true} if the given player is the local admin; {@code false}
	 * otherwise.
	 */
	public static boolean playerIsAdmin(Player other) {
		return other.getUniqueId().equals(PlayerUtil.getAdminUUID());
	}

	/**
	 * Determines if the local admin is online.
	 *
	 * @return {@code true} if the admin is currently online; {@code false} otherwise;
	 */
	public static boolean adminIsOnline() {
		return PlayerUtil.getAdmin() != null;
	}

	/**
	 * Sends a given message to the admin of this server, if and only if the admin is
	 * currently online.
	 *
	 * @param message the message to be sent to the admin.
	 */
	public static void sendAdmin(String message) {
		if (PlayerUtil.adminIsOnline()) {
			PlayerUtil.getAdmin().sendMessage(message);
		}
	}

	/**
	 * Sends a given message not only to the admin of the server, but also logs it in the
	 * console.
	 *
	 * @param message the message that should be sent.
	 */
	public static void sendAllAdmins(String message) {
		PlayerUtil.sendAdmin(message);
		Bukkit.getConsoleSender().sendMessage(message);
	}

}
