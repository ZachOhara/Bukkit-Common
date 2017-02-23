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

import io.github.zachohara.bukkit.simpleplugin.plugin.SimplePlugin;
import io.github.zachohara.bukkit.simpleplugin.plugin.main.SimplePluginMain;

public class StringColor {

	/**
	 * The {@code StringColor} class should not be instantiable.
	 */
	private StringColor() {

	}
	
	/**
	 * The color that all standard text should appear in.
	 */
	public static String getTextColor() {
		return StringColor.getColor("text");
	}

	/**
	 * The color that error messages should appear in.
	 */
	public static String getErrorColor() {
		return StringColor.getColor("error");
	}

	/**
	 * The color that player's and command's names should appear in.
	 */
	public static String getNameColor() {
		return StringColor.getColor("names");
	}

	/**
	 * The color that the server admin's name should appear in.
	 */
	public static String getAdminNameColor() {
		return StringColor.getColor("admin-name");
	}

	/**
	 * The color that location coordinates should appear in.
	 */
	public static String getLocationColor() {
		return StringColor.getColor("locations");
	}
	
	private static String getColor(String option) {
		return "&" + SimplePlugin.getPluginInstance(SimplePluginMain.class).getConfig().getString("colors." + option);
	}
	
}
