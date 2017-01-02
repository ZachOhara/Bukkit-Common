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

package io.github.zachohara.bukkit.simpleplugin.command;

/**
 * The {@code CommandSet} interface is designed to be implemented by an enumeration that
 * represents the set of commands supported by a plugin. The enumeration should contain a
 * {@code Properties} object for each command, with appropriate information.
 *
 * @author Zach Ohara
 * @see Properties
 */
public interface CommandSet {

	/**
	 * Gets the {@code Properties} object specific to a single command.
	 *
	 * @return the {@code Properties} for a command.
	 */
	public Properties getProperties();

}
