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

import org.bukkit.ChatColor;
import org.bukkit.Location;

import io.github.zachohara.bukkit.simpleplugin.command.CommandInstance;

/**
 * The {@code StringParser} class provides static constants and methods that are useful in
 * dealing with messages and other strings.
 *
 * @author Zach Ohara
 */
public final class StringParser {

	/**
	 * The {@code StringParser} class should not be instantiable.
	 */
	private StringParser() {

	}

	/**
	 * Gets nicely-formatted String with the coordinates and world name for a given
	 * location. This method assumes the default naming scheme for bukkit worlds, which is
	 * that the overworld will have an arbitrary user-defined name, and its corresponding
	 * nether world and end worlds will have that same arbitrary name suffixed by "_nether"
	 * and "_the_end" respectively.
	 *
	 * @param loc the location to be formatted into a String.
	 * @return a formatted String of the location.
	 */
	public static String getLocationString(Location loc) {
		return StringParser.getLocationString(loc, true);
	}

	/**
	 * Gets nicely-formatted String with the coordinates and world name for a given
	 * location. This method assumes the default naming scheme for bukkit worlds, which is
	 * that the overworld will have an arbitrary user-defined name, and its corresponding
	 * nether world and end worlds will have that same arbitrary name suffixed by "_nether"
	 * and "_the_end" respectively.
	 *
	 * @param loc the location to be formatted into a String.
	 * @param includeWorld {@code true} if the world name should be included in the
	 * resulting string, or {@code false} if only the coordinates should be returned.
	 * @return a formatted String of the location.
	 */
	public static String getLocationString(Location loc, boolean includeWorld) {
		//@formatter:off
		String locString = StringColor.getLocationColor() + "("
				+ loc.getBlockX() + ", "
				+ loc.getBlockY() + ", "
				+ loc.getBlockZ() + ")"
				+ StringColor.getTextColor();
		//@formatter:on
		if (includeWorld) {
			locString += " in " + StringColor.getLocationColor();
			String worldName = loc.getWorld().getName();
			boolean isNether = worldName.endsWith("_nether");
			boolean isTheEnd = worldName.endsWith("_the_end");
			if (isNether) {
				locString += "the nether";
			} else if (isTheEnd) {
				locString += "the end";
			} else {
				locString += "the overworld";
			}
			locString += StringColor.getTextColor();
		}
		return locString;
	}
	
	/**
	 * Parses and colors a message, and substitutes any of the supported shortcuts.
	 *
	 * @param message the message to be parsed.
	 * @return a colored and formatted version of the given message.
	 * @see #parseText(String, ChatColor, CommandInstance)
	 */
	public static String parseMessage(String message) {
		return StringParser.parseMessage(message, null);
	}

	/**
	 * Parses and colors a message, and substitutes any of the supported shortcuts.
	 *
	 * @param message the message to be parsed.
	 * @param source the {@code CommandInstance} object that this message is attached to.
	 * @return a colored and formatted version of the given message.
	 * @see #parseText(String, ChatColor, CommandInstance)
	 */
	public static String parseMessage(String message, CommandInstance source) {
		return StringParser.parseText(message, StringColor.getTextColor(), source);
	}

	/**
	 * Parses and colors an error message, and substitutes any of the supported shortcuts.
	 *
	 * @param message the error message to be parsed.
	 * @return a colored and formatted version of the given error message.
	 * @see #parseText(String, ChatColor, CommandInstance)
	 */
	public static String parseError(String message) {
		return StringParser.parseError(message, null);
	}
	
	/**
	 * Parses and colors a message, and substitues any of the supported shortcuts.
	 *
	 * @param message the message to parse.
	 * @param primayColor the color that the plain text of this message should appear in.
	 * @return a colored and formatted version of the given message.
	 * @see #parseText(String, ChatColor, CommandInstance)
	 */
	public static String parseText(String message, String primaryColor) {
		return StringParser.parseText(message, primaryColor, null);
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
		return StringParser.parseText(message, StringColor.getErrorColor(), source);
	}

	/**
	 * Parses a given message, substitutes any of the supported shortcuts, and colors the
	 * message to be the given color. If the message only needs to be parsed for color, but
	 * not for instance information, then the {@code CommandInstance} parameter can be
	 * {@code null}.
	 *
	 * @param message the message to parse.
	 * @param primayColor the color that the plain text of this message should appear in.
	 * @param source the {@code CommandInstance} object that this message is attached to.
	 * {@code null} if the message is not attached to any specific instance of a command.
	 * @return a colored and formatted version of the given message.
	 * @see #parseStringForCustomColor(String, ChatColor)
	 */
	private static String parseText(String message, String primaryColor, CommandInstance source) {
		message = primaryColor + message;
		if (source != null) {
			message = StringParser.parseStringForInstance(message, primaryColor, source);
		}
		message = StringParser.parseStringForCustomColor(message, primaryColor);
		message = StringParser.parseStringForDefaultColors(message, primaryColor);
		return message;
	}

	/**
	 * Parses a given message for supported shortcuts, and substitutes in relevant
	 * information from the source and context of a command.
	 *
	 * @param message the message to parse.
	 * @param primaryColor the color that the plain text of this message should appear in.
	 * @param source the {@code CommandInstance} that this message is attached to. Cannot
	 * be {@code null}.
	 * @return the message with expanded instance information.
	 * @see #parseText(String, ChatColor, CommandInstance)
	 */
	private static String parseStringForInstance(String message, String primaryColor,
			CommandInstance source) {
		//@formatter:off
		final String[][] parsingKeys = {
				{"%admin", "@admin" + PlayerUtil.getAdminName()},
				{"%sloc", StringParser.getSenderLocation(source)},
				{"%tloc", StringParser.getTargetLocation(source)},
				{"%s", source.getSenderName()},
				{"%t", source.getTargetName()},
				{"%gt", source.getGivenTarget()},
				{"/%c", "@name(/)%c"},
				{"%c", source.getName()}
		};
		//@formatter:on

		for (String[] parseKey : parsingKeys) {
			String substitute = StringColor.getNameColor() + parseKey[1] + primaryColor;
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
			return StringParser.getLocationString(source.getSenderPlayer().getLocation());
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
			return StringParser.getLocationString(source.getTargetPlayer().getLocation());
		} else {
			return "@location[no location]";
		}
	}

	/**
	 * Parse a given message for color keys, and color the message appropriately.
	 *
	 * @param message the message to be parsed.
	 * @param primarycolor the color that the "@default" tag should be expanded to.
	 * @return the recolored message.
	 */
	private static String parseStringForCustomColor(String message, String primaryColor) {
		//@formatter:off
		final String[][] colorKeys = {
				{"@default", primaryColor.toString()},
				{"@admin", StringColor.getAdminNameColor()},
				{"@name", StringColor.getNameColor()},
				{"@text", StringColor.getTextColor()},
				{"@error", StringColor.getErrorColor()},
				{"@location", StringColor.getLocationColor()}
		};
		//@formatter:on
		
		return StringParser.parseForColorKeys(message, colorKeys, primaryColor);
	}
	
	private static String parseStringForDefaultColors(String message, String primaryColor) {
		//@formatter:off
		final String[][] colorKeys = {
			{"&aqua", ChatColor.AQUA.toString()},
			{"&black", ChatColor.BLACK.toString()},
			{"&blue", ChatColor.BLUE.toString()},
			{"&darkaqua", ChatColor.DARK_AQUA.toString()},
			{"&darkblue", ChatColor.DARK_BLUE.toString()},
			{"&darkgray", ChatColor.DARK_GRAY.toString()},
			{"&darkgreen", ChatColor.DARK_GREEN.toString()},
			{"&darkpurple", ChatColor.DARK_PURPLE.toString()},
			{"&darkred", ChatColor.DARK_RED.toString()},
			{"&gold", ChatColor.GOLD.toString()},
			{"&gray", ChatColor.GRAY.toString()},
			{"&green", ChatColor.GREEN.toString()},
			{"&lightpurple", ChatColor.LIGHT_PURPLE.toString()},
			{"&red", ChatColor.RED.toString()},
			{"&white", ChatColor.WHITE.toString()},
			{"&yellow", ChatColor.YELLOW.toString()},
			{"&bold", ChatColor.BOLD.toString()},
			{"&italic", ChatColor.ITALIC.toString()},
			{"&strikethrough", ChatColor.STRIKETHROUGH.toString()},
			{"&underline", ChatColor.UNDERLINE.toString()},
			{"&magic", ChatColor.MAGIC.toString()},
			{"&reset", ChatColor.RESET.toString()}
		};
		//@formatter:on

		return StringParser.parseForColorKeys(message, colorKeys, primaryColor);
	}
	
	private static String parseForColorKeys(String message, String[][] colorKeys, String primaryColor) {		
		for (String[] colorKey : colorKeys) {
			if (colorKey[0].equals(primaryColor)) {
				primaryColor = colorKey[1];
			}
		}
		
		for (String[] colorKey : colorKeys) {
			String substitute = colorKey[1];
			while (message.indexOf(colorKey[0]) != -1) {
				int index = message.indexOf(colorKey[0]);
				String head = message.substring(0, index);
				if (message.indexOf(colorKey[0] + "(") == index) {
					int endIndex = message.indexOf(")", index);
					String body = message.substring(index + colorKey[0].length() + 1, endIndex);
					String tail = message.substring(endIndex + 1);
					message = head + substitute + body + primaryColor + tail;
				} else {
					String tail = message.substring(index + colorKey[0].length());
					message = head + substitute + tail;
				}
			}
		}
		return message;
	}

}
