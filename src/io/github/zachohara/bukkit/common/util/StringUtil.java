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
	 * The width, in characters, of the default player-side chat box.
	 */
	public static final int PLAYER_CHAT_WIDTH = 53;
	
	/**
	 * The width, in characters, of the default console window.
	 */
	public static final int CONSOLE_WIDTH = 63;
	
	/**
	 * The color that the server admin's name should appear in.
	 */
	public static final ChatColor ADMINCOLOR = ChatColor.LIGHT_PURPLE;
	
	/**
	 * The color that all standard text should appear in.
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
	 * The message that is sent to players when a command they have submitted did not have
	 * enough arguments sent with it.
	 */
	public static final String ERROR_TOO_FEW_ARGS_MESSAGE = "Not enough arguments! Try using @name/help %c";
	
	/**
	 * The message that is sent to players when a command they have submitted had too many
	 * arguments sent with it.
	 */
	public static final String ERROR_TOO_MANY_ARGS_MESSAGE = "Too many arguments! Try using @name/help %c";
	
	/**
	 * The message that is sent to players when the target player they have specified as a
	 * command argument is not a valid, currently online player.
	 */
	public static final String ERROR_TARGET_OFFLINE_MESSAGE = "%gt either is not online right now or doesn't exist.";
	
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
	public static final String ERROR_TARGET_ONLY_IF_OP = "You must be an OP to use this command on someone else";
	
	/**
	 * The message that is sent to players when they try to target the admin with a command
	 * that the admin is protected against.
	 */
	public static final String ERROR_ADMIN_PROTECTED_MESSAGE = "You cannot use this command on" + " the all-powerful %admin!\nOverlord %admin has been notified of your futile attempt!";
	
	/**
	 * The message that is sent to admins to inform them that a player has attempted to a
	 * command on them, but that the admin was protected from the command.
	 */
	public static final String ERROR_ADMIN_PROTECTED_ADMIN_NOTIFICATION = "%s has tried to use @name/%c on " + "overlord %admin!";
	
	/**
	 * The message that is sent to players when they try to use a command that requires
	 * operator / moderator priviages on the server, but the player is not an operator.
	 */
	public static final String ERROR_NOT_OP_MESSAGE = "You must be an OP to use this command";
	
	/**
	 * The message that is sent to the conosle when the console sends a command that can
	 * only be used by an in-game player.
	 */
	public static final String ERROR_PLAYER_ONLY_MESSAGE = "This command is only usable as a player";
	
	/**
	 * The message that is sent to players when they try to use a command that only the
	 * admin is allowed to use.
	 */
	public static final String ERROR_ADMIN_ONLY_MESSAGE = "Only the all-powerful " + "%admin may use this command!\nOverlord %admin has been notified of your futile attempt!";
	
	/**
	 * The message that is sent to admins to inform them that a player has tried to use a
	 * command that is reserved for the admins.
	 */
	public static final String ERROR_ADMIN_ONLY_ADMIN_NOTIFICATION = "%s has tried to use @name/%c on %gt";
	
	/**
	 * Gets nicely-formatted String with the coordinates and world name for a given
	 * location. This method assumes the default naming scheme for bukkit worlds, which is
	 * that the overworld will be named some arbitrary, user-defined name, that its
	 * corresponding nether world will be named that same arbitrary name suffixed by
	 * "_nether", and that its corresponding end world will be named the arbitrary name
	 * suffixed by "_the_end".
	 * 
	 * @param loc the location to be formatted into a String.
	 * @return a formatted String of the location.
	 */
	public static String getLocationString(Location loc) {
		//@formatter:off
		String locString = LOCATIONCOLOR + "("
				+ loc.getBlockX() + ", "
				+ loc.getBlockY() + ", "
				+ loc.getBlockZ() + ")"
				+ TEXTCOLOR + " in " + LOCATIONCOLOR;
		//@formatter:on
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
	 * Parses and colors a message, and substitutes any of the supported shortcuts.
	 * 
	 * @param message the message to be parsed.
	 * @param source the {@code CommandInstance} object that this message is attached to.
	 * @return a colored and formatted version of the given message.
	 * @see #parseText(String, ChatColor, CommandInstance)
	 */
	public static String parseString(String message, CommandInstance source) {
		return parseText(message, TEXTCOLOR, source);
	}
	
	/**
	 * Parses and colors an error message, and substitutes any of the supported shortcuts.
	 * 
	 * @param message the error message to be parsed.
	 * @param source the {@code CommandInstance} object that this message is attached to.
	 * @return a colored and formatted version of the given error message.
	 * @see #parseText(String, ChatColor, CommandInstance)
	 */
	public static String parseError(String message, CommandInstance source) {
		return parseText(message, ERRORCOLOR, source);
	}
	
	/**
	 * Parses a given message, substitutes any of the supported shortcuts, and colors the
	 * message to be the given color. If the message only needs to be parsed for color, but
	 * not for instance information, then the {@code CommandInstance} parameter can be
	 * {@code null}.
	 * 
	 * @param message the message to parse.
	 * @param color the color that the plain text of this message should appear in.
	 * @param source the {@code CommandInstance} object that this message is attached to.
	 * {@code null} if the message is not attached to any specific instance of a command.
	 * @return a colored and formatted version of the given message.
	 * @see #parseStringForColor(String, ChatColor)
	 */
	private static String parseText(String message, ChatColor color, CommandInstance source) {
		message = color + message;
		if (source != null) {
			message = parseStringForInstance(message, color, source);
		}
		message = parseStringForColor(message, color);
		return message;
	}
	
	/**
	 * Parses a given message for supported shortcuts, and substitutes in relevant
	 * information from the source and context of a command.
	 * 
	 * @param message the message to parse.
	 * @param color the color that the plain text of this message should appear in.
	 * @param source the {@code CommandInstance} that this message is attached to. Cannot
	 * be {@code null}.
	 * @return the message with expanded instance information.
	 * @see #parseText(String, ChatColor, CommandInstance)
	 */
	private static String parseStringForInstance(String message, ChatColor color, CommandInstance source) {
		//@formatter:off
		final String[][] parsingKeys = {
				{"%admin", "@admin" + PlayerUtil.getAdminName()},
				{"%sloc", getSenderLocation(source)},
				{"%tloc", getTargetLocation(source)},
				{"%s", source.getSenderName()},
				{"%t", source.getTargetName()},
				{"%gt", source.getGivenTarget()},
				{"/%c", "@name(/)%c"},
				{"%c", source.getName()}
		};
		//@formatter:on
		
		for (String[] parseKey : parsingKeys) {
			String substitute = NAMECOLOR + parseKey[1] + color;
			while (message.indexOf(parseKey[0]) != -1) {
				int index = message.indexOf(parseKey[0]);
				String head = message.substring(0, index);
				String tail = message.substring(index + parseKey[0].length());
				message = head + substitute + tail;
			}
		}
		return message;
	}
	
	/**
	 * Gets a colored, formatted string representing the location of the sender of a
	 * command. If the command was not sent by a player, the string {@code "[no location]"}
	 * will be returned. The returned string will be colored an appropriate color.
	 * 
	 * @param source the {@code CommandInstance} of the given command.
	 * @return a formatted string for the location of the command sender.
	 */
	private static String getSenderLocation(CommandInstance source) {
		if (source.isFromPlayer()) {
			return getLocationString(source.getSenderPlayer().getLocation());
		} else {
			return "@location[no location]";
		}
	}
	
	/**
	 * Gets a colored, formatted string representing the location of the player targeted by
	 * a command. If the command has no attached target player, the string
	 * {@code "[no location]"} will be returned. The returned string will be colored an
	 * appropriate color.
	 * 
	 * @param source the {@code CommandInstance} of the given command.
	 * @return a formatted string for the location of the command sender.
	 */
	private static String getTargetLocation(CommandInstance source) {
		if (source.hasTarget()) {
			return getLocationString(source.getTargetPlayer().getLocation());
		} else {
			return "@location[no location]";
		}
	}
	
	/**
	 * Parse a given message for color keys, and color the message appropriately.
	 * 
	 * @param message the message to be parsed.
	 * @param color the color that the "@default" tag should be expanded to.
	 * @return the recolored message.
	 */
	private static String parseStringForColor(String message, ChatColor color) {
		final String[][] colorKeys = { { "@default", color.toString()}, { "@admin", ADMINCOLOR.toString()}, { "@name", NAMECOLOR
			.toString()}, { "@text", TEXTCOLOR.toString()}, { "@error", ERRORCOLOR.toString()}, { "@location", LOCATIONCOLOR
				.toString()}};
		
		for (String[] colorKey : colorKeys) {
			String substitute = colorKey[1];
			while (message.indexOf(colorKey[0]) != -1) {
				int index = message.indexOf(colorKey[0]);
				String head = message.substring(0, index);
				if (message.indexOf(colorKey[0] + "(") == index) {
					int endIndex = message.indexOf(")", index);
					String body = message.substring(index + colorKey[0].length() + 1, endIndex);
					String tail = message.substring(endIndex + 1);
					message = head + substitute + body + color + tail;
				} else {
					String tail = message.substring(index + colorKey[0].length());
					message = head + substitute + tail;
				}
			}
		}
		return message;
	}
	
}
