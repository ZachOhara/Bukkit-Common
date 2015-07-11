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

import io.github.zachohara.bukkit.common.command.CommandRules.Source;
import io.github.zachohara.bukkit.common.command.CommandRules.Target;

/**
 * A {@code CommandRulesEntry} object represents the rules and conditions for a command.
 * All of the conditions outlined in this object need to be verified before attempting to
 * execute the command.
 * <p>
 * This class contians rules for a command such as minimum and maximum expected amounts
 * of arguments, the required permissions and conditions of any entity trying to use the
 * command, and the type(s) of players that can be targeted by the command under certain
 * situations.
 * 
 * @author Zach Ohara
 */
public class CommandRulesEntry {

	/**
	 * The name of the command, as it would be typed in the game or from a console.
	 */
	private String name;

	/**
	 * The minimum amount of arguments that should be allowed for the command.
	 */
	private int minArgs;

	/**
	 * The maximum amount of arguments that should be allowed for the command, or
	 * {@code -1} if there is no maximum amount of arguments.
	 */
	private int maxArgs;

	/**
	 * The type or range of sources that are allowed to use the command.
	 */
	private Source accessible;

	/**
	 * The type or range of players that can be targeted by this command.
	 */
	private Target targetable;

	/**
	 * Constructs a new {@code Rules} object from the required information.
	 * 
	 * @param name see instance variable {@link #name}
	 * @param minArgs see instance variable {@link #minArgs}
	 * @param maxArgs see instance variable {@link #maxArgs}
	 * @param access see instanace variable {@link #accessible}
	 * @param target see instance variable {@link #targetable}
	 */
	public CommandRulesEntry(String name, int minArgs, int maxArgs, Source access, Target target) {
		this.name = name;
		this.minArgs = minArgs;
		this.maxArgs = maxArgs;
		this.accessible = access;
		this.targetable = target;
	}

	/**
	 * Constructs a new {@code CommandRulesEntry} object that should exactly mimic the
	 * properties of a different command.
	 * 
	 * @param name the (unique) name of this command.
	 * @param alias the command that this command should exactly mimic the properties of.
	 */
	public CommandRulesEntry(String name, CommandRulesEntry alias) {
		this.name = name;
		this.minArgs = alias.minArgs;
		this.maxArgs = alias.maxArgs;
		this.accessible = alias.accessible;
		this.targetable = alias.targetable;
	}

	/**
	 * Gets the name of the command, as it would be typed in the game or from a console.
	 * 
	 * @return the name of the command.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the minimum amount of arguments that should be allowed for the command.
	 * 
	 * @return the minimum amount of arguments allowed.
	 */
	public int getMinArgs() {
		return minArgs;
	}

	/**
	 * Gets the maximum amount of arguments that should be allowed for the command, or
	 * {@code -1} if this command has no maximum amount of arguments.
	 * 
	 * @return the maximum amount of arguments allowed.
	 */
	public int getMaxArgs() {
		return maxArgs;
	}

	/**
	 * Gets the type or range of sources that are allowed to use the command.
	 * 
	 * @return the type or range of sources that are allowed to use the command.
	 * @see CommandRules.Source
	 */
	public Source getAccessible() {
		return accessible;
	}

	/**
	 * Gets the type or range of target players that can be targeted by this command.
	 * 
	 * @return the type of players that are targetable by this command.
	 * @see CommandRules.Target
	 */
	public Target getTargetable() {
		return targetable;
	}

}
