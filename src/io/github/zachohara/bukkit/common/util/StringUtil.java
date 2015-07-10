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

package io.github.zachohara.bukkit.common.util;

import io.github.zachohara.bukkit.common.command.CommandInstance;

import org.bukkit.ChatColor;
import org.bukkit.Location;

/**
 * The {@code StringUtil} class provides static constants and methods that are useful in
 * dealing with messages and other strings that are exchanged between the server and the
 * players using it.
 * 
 * @author Zach Ohara
 */
public class StringUtil {

	/**
	 * The color that the server admin's name should appear in.
	 */
	public static final ChatColor ADMINCOLOR = ChatColor.LIGHT_PURPLE;

	/**
	 * The color that standard text should appear in.
	 */
	public static final ChatColor TEXTCOLOR = ChatColor.AQUA;

	/**
	 * The color that player's and command's names should appear in.
	 */
	public static final ChatColor NAMECOLOR = ChatColor.WHITE;

	/**
	 * The color that error messages should appear in.
	 */
	public static final ChatColor ERRORCOLOR = ChatColor.RED;

	/**
	 * The color that location coordinates should appear in.
	 */
	public static final ChatColor LOCATIONCOLOR = ChatColor.GREEN;


	/**
	 * The message that is sent to players when they do not have permission to use a
	 * given command.
	 */ 
	public static final String ERROR_NOT_OP_MESSAGE = "You must be an OP to use this command";

	/**
	 * The message that is sent to players when a command they have submitted did not
	 * have enough arguments sent with it.
	 */
	public static final String ERROR_TOO_FEW_ARGS_MESSAGE = "Not enough arguments! Try using @name/help @text%c";

	/**
	 * The message that is sent to players when a command they have submitted had too many
	 * arguments sent with it.
	 */
	public static final String ERROR_TOO_MANY_ARGS_MESSAGE = "Too many arguments! Try using @name/help @text%c";

	/**
	 * The message that is sent to players when the target player they have specified as
	 * a command argument is not a valid player.
	 */
	public static final String ERROR_TARGET_OFFLINE_MESSAGE = "%gt either is not online right now or"
			+ " doesn't exist.";
	
	/**
	 * The message that is sent to players when the target they have selected was not found
	 * in any way.
	 */
	public static final String ERROR_TARGET_DNE_MESSAGE = "No records were found for %gt";
	
	public static final String ERROR_TARGET_IF_OP = "You must be an OP to use this command on someone else";

	/**
	 * The message that is sent to the conosle when the console sends a command that can
	 * only be used by an in-game player.
	 */
	public static final String ERROR_PLAYER_ONLY_MESSAGE = "This command is only usable as a player";

	/**
	 * The message that is sent to players when they try to use a command that only the
	 * admin can use.
	 */
	public static final String ERROR_ADMIN_ONLY_MESSAGE = "Only the all-powerful "
			+ "%admin may use this command!\nOverlord %admin has been notified of your futile attempt!";

	/**
	 * The message that is sent to admins to inform them of a misuse of a command.
	 */
	public static final String ERROR_ADMIN_ONLY_ADMIN_NOTIFICATION = "%s has tried to use @name/%c on %gt";

	/**
	 * The message that is sent to players when they try to target the admin with a command
	 * that the admin is protected against.
	 */
	public static final String ERROR_ADMIN_PROTECTED_MESSAGE = "You cannot use this command on"
			+ " the all-powerful %admin!\nOverlord %admin has been notified of your futile attempt!";

	/**
	 * The message that is sent to admins to inform them of a misuse of a command.
	 */
	public static final String ERROR_ADMIN_PROTECTED_ADMIN_NOTIFICATION = "%s has tried to use @name/%c on "
			+ "overlord %admin!";


	/**
	 * Gets nicely formatted String with the coordinates of the given location.
	 * @param loc the location to be formatted into a String.
	 * @return a formatted String with the coordinates of the given location.
	 */
	public static String getLocationString(Location loc) {
		String locString = LOCATIONCOLOR + "(" + loc.getBlockX() + ", "
				+ loc.getBlockY() + ", "
				+ loc.getBlockZ() + ")"
				+ TEXTCOLOR + " in " + LOCATIONCOLOR;
		String worldName = loc.getWorld().getName();
		boolean isNether = worldName.endsWith("_nether");
		boolean isTheEnd = worldName.endsWith("_the_end");
		if (isNether)
			locString += "the nether";
		else if (isTheEnd)
			locString += "the end";
		else
			locString += "the overworld";
		return locString;
	}

	/**
	 * Parse and color a message, and substitute any of the supported shortcuts.
	 * @param message the message to be parsed.
	 * @param source the {@code CommandInstance} object that this message is attached to.
	 * @return a colored and formatted version of the given message.
	 * @see {@link #parseText(String, ChatColor, CommandInstance)}
	 */
	public static String parseString(String message, CommandInstance source) {
		return parseText(message, TEXTCOLOR, source);
	}

	/**
	 * Parse and color an error message, and substitute any of the supported shortcuts.
	 * @param message the error message to be parsed.
	 * @param source the {@code CommandInstance} object that this message is attached to.
	 * @return a colored and formatted version of the given error message.
	 * @see {@link #parseText(String, ChatColor, CommandInstance)}
	 */
	public static String parseError(String message, CommandInstance source) {
		return parseText(message, ERRORCOLOR, source);
	}

	/**
	 * Parse a given message, substitute any of the supported shortcuts, and color the
	 * message to be the given color.
	 * @param message the message to parse.
	 * @param color the color that the message should appear in.
	 * @param source the {@code CommandInstance} object that this message is attached to.
	 * @return a colored and formatted version of the given message.
	 */
	private static String parseText(String message, ChatColor color, CommandInstance source) {
		
		// TODO: line-wrapping
		
		final String[][] parsingKeys = {
				{"%admin", ADMINCOLOR + PlayerUtil.getAdminName()},
				{"%sloc", source.isFromPlayer() ?
						getLocationString(source.getSenderPlayer().getLocation())
						: "[no location]"},
				{"%tloc", source.hasTarget() ?
						getLocationString(source.getTargetPlayer().getLocation())
						: "[no location]"},
				{"%s", source.getSenderName()},
				{"%t", source.getTargetName()},
				{"%gt", source.getGivenTarget()},
				{"%c", source.getName()},
		};
		
		final String[][] colorKeys = {
				{"@admin", ADMINCOLOR.toString()},
				{"@name", NAMECOLOR.toString()},
				{"@text", TEXTCOLOR.toString()},
				{"@error", ERRORCOLOR.toString()},
				{"@location", LOCATIONCOLOR.toString()}
		};

		message = color + message;

		// parse the message for information substitutions
		for (String[] parseKey : parsingKeys) {
			String substitute = NAMECOLOR + parseKey[1] + color;
			while (message.indexOf(parseKey[0]) != -1) {
				int index = message.indexOf(parseKey[0]);
				String a = message.substring(0, index);
				String b = message.substring(index + parseKey[0].length());
				message = a + substitute + b;
			}
		}

		// parse the message again for styling
		for (String[] colorKey : colorKeys) {
			String substitute = colorKey[1];
			while (message.indexOf(colorKey[0]) != -1) {
				int index = message.indexOf(colorKey[0]);
				String a = message.substring(0, index);
				String b = message.substring(index + colorKey[0].length());
				message = a + substitute + b;
			}
		}
		return message;
	}

}
