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
 * The {@code CommandRules} interface is designed to be implemented by an enumeration class
 * that represents the set of commands that a plugin supports. The enumeration should
 * contain information for each command, such as the range of expected amounts of
 * arguments, the required permission level necessary to use a command, and if the command
 * should require a target player.
 * <p>
 * The resulting enumeration is one of two enumerations that represent the set of commands
 * supported by a plugin. Any single command will have two enumerable constants that
 * correspond to it. A command's constant listed in this enumeration will contain
 * information about the non-instance-specific details of the command, and its expected
 * context. A command's entry in the other enumeration, which should be an implementation
 * of {@code CommandExecutables}, will contain an executable object that holds the 'main
 * method' for the command.
 * 
 * @author Zach Ohara
 */
public interface CommandRules {
	
	/**
	 * Gets the {@code CommandRulesEntry} associated with this comand.
	 * 
	 * @return the {@code CommandRulesEntry} associated with this comand.
	 */
	public CommandRulesEntry getRulesEntry();
	
	/**
	 * Represents the set of possible sources, or ranges of sources, that may be allowed to
	 * use any single command.
	 */
	public static enum Source {
		ALL,
		PLAYER_ONLY,
		OP_ONLY,
		ADMIN_ONLY,
		ADMIN_PLAYER_ONLY
	}
	
	/**
	 * Represents the set of possible targets, or ranges of targets, that may be allowed to
	 * be targeted by any single command.
	 */
	public static enum Target {
		NONE,
		RESTRICT_ADMIN,
		IF_SENDER_OP,
		ALL_ONLINE,
		ALLOW_OFFLINE
	}
	
}
