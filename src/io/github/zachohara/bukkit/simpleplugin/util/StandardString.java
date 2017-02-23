/* Copyright (C) 2017 Zach Ohara
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

/**
 * The {@code StandardString} class contains some common strings that are exchanged between the
 * server and the players using it.
 *
 * @author Zach Ohara
 */
public final class StandardString {

	/**
	 * The {@code StandardString} class should not be instantiable.
	 */
	private StandardString() {

	}

	/**
	 * The width, in characters, of the default player-side chat box.
	 */
	public static final int PLAYER_CHAT_WIDTH = 53;

	/**
	 * The width, in characters, of the default console window.
	 */
	public static final int CONSOLE_WIDTH = 63;

	/**
	 * The message that is sent to players when a command they have submitted did not have
	 * enough arguments sent with it.
	 */
	public static final String ERROR_TOO_FEW_ARGS_MESSAGE =
			"Not enough arguments! Try using @name/help %c";

	/**
	 * The message that is sent to players when a command they have submitted had too many
	 * arguments sent with it.
	 */
	public static final String ERROR_TOO_MANY_ARGS_MESSAGE =
			"Too many arguments! Try using @name/help %c";

	/**
	 * The message that is sent to players when the target player they have specified as a
	 * command argument is not a valid, currently online player.
	 */
	public static final String ERROR_TARGET_OFFLINE_MESSAGE =
			"%gt either is not online right now or doesn't exist.";

	/**
	 * The message that is sent to players when the target player they have specified as a
	 * command argument is not online, and could not be found in any offline records.
	 */
	public static final String ERROR_TARGET_DNE_MESSAGE = "No records were found for %gt";

	/**
	 * The message that is sent to players when they send a command with an attached target
	 * player, and a target player can only be used with the command if the sender has
	 * operator / moderator privialeges, but the sender does not have operator privialeges.
	 */
	public static final String ERROR_TARGET_ONLY_IF_OP =
			"You must be an OP to use this command on someone else";

	/**
	 * The message that is sent to players when they try to target the admin with a command
	 * that the admin is protected against.
	 */
	public static final String ERROR_ADMIN_PROTECTED_MESSAGE = "You cannot use this command on"
			+ " the all-powerful %admin!\nOverlord %admin has been notified of your futile attempt!";

	/**
	 * The message that is sent to admins to inform them that a player has attempted to a
	 * command on them, but that the admin was protected from the command.
	 */
	public static final String ERROR_ADMIN_PROTECTED_ADMIN_NOTIFICATION =
			"%s has tried to use /%c on overlord %admin!";

	/**
	 * The message that is sent to players when they try to use a command that requires a
	 * Bukkit permission node that they do not have.
	 */
	public static final String ERROR_NO_PERMISSION_MESSAGE =
			"You do not have permission to use this command";

	/**
	 * The message that is sent to players when they try to use a command that requires
	 * operator / moderator priviages on the server, but the player is not an operator.
	 */
	public static final String ERROR_NOT_OP_MESSAGE = "You must be an OP to use this command";

	/**
	 * The message that is sent to the conosle when the console sends a command that can
	 * only be used by an in-game player.
	 */
	public static final String ERROR_PLAYER_ONLY_MESSAGE =
			"This command is only usable as a player";

	/**
	 * The message that is sent to players when they try to use a command that only the
	 * admin is allowed to use.
	 */
	public static final String ERROR_ADMIN_ONLY_MESSAGE = "Only the all-powerful "
			+ "%admin may use this command!\nOverlord %admin has been notified of your futile attempt!";

	/**
	 * The message that is sent to players when they try to use a command that only the
	 * console is allowed to use.
	 */
	public static final String ERROR_CONSOLE_ONLY_MESSAGE =
			"This command is only usable by the console";

	/**
	 * The message that is sent to admins to inform them that a player has tried to use a
	 * command that is reserved for the admins.
	 */
	public static final String ERROR_ADMIN_ONLY_ADMIN_NOTIFICATION =
			"%s has tried to use %c on %gt";

}
