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

import io.github.zachohara.bukkit.simpleplugin.command.CommandInstance;

import org.bukkit.ChatColor;
import org.bukkit.Location;

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
		String locString = Strings.LOCATIONCOLOR + "("
				+ loc.getBlockX() + ", "
				+ loc.getBlockY() + ", "
				+ loc.getBlockZ() + ")"
				+ Strings.TEXTCOLOR + " in " + Strings.LOCATIONCOLOR;
		//@formatter:on
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
		return StringParser.parseText(message, Strings.TEXTCOLOR, source);
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
		return StringParser.parseText(message, Strings.ERRORCOLOR, source);
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
			message = StringParser.parseStringForInstance(message, color, source);
		}
		message = StringParser.parseStringForColor(message, color);
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
			String substitute = Strings.NAMECOLOR + parseKey[1] + color;
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
	 * @param color the color that the "@default" tag should be expanded to.
	 * @return the recolored message.
	 */
	private static String parseStringForColor(String message, ChatColor color) {
		//@formatter:off
		final String[][] colorKeys = {
				{"@default", color.toString()},
				{"@admin", Strings.ADMINCOLOR.toString()},
				{"@name", Strings.NAMECOLOR.toString()},
				{"@text", Strings.TEXTCOLOR.toString()},
				{"@error", Strings.ERRORCOLOR.toString()},
				{"@location", Strings.LOCATIONCOLOR.toString()}
		};
		//@formatter:on

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
