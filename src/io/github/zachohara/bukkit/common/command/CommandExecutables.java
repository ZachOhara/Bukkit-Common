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

package io.github.zachohara.bukkit.common.command;

/**
 * The {@code CommandExecutables} interface is designed to be implemented by an
 * enumeration class that represents the set of commands that a plugin supports. The
 * enumeration should contain executable objects for each command that act as the main
 * procedure for that command.
 * <p>
 * The resulting enumeration is one of two enumerations that represent the set of commands
 * supported by a plugin. Any single command will have two enumerable constants that
 * correspond to it. A command's constant listed in this enumeration will contain an
 * executable object that holds the 'main method' for the command. A command's entry in
 * the other enumeration, which should be an implementation of {@code CommandRules}, will
 * contain information about the non-instance-specific details of the command, and its
 * expected context.
 * 
 * @author Zach Ohara
 */
public interface CommandExecutables {

	/**
	 * Gets the subclass of {@code Implementation} that contains an implementation for the
	 * command.
	 * 
	 * @return the {@code Implementation} of the command.
	 * @see {@link Implementation}
	 */
	public Implementation getImplementation();

}
