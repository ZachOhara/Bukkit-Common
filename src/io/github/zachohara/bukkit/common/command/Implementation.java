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
 * The {@code Implementation} class acts as a superclass for implementations of specific
 * commands. Each command that a plugin supports has a corresponding
 * {@code Implementation} object that contains its main procedure.
 *
 * @author Zach Ohara
 */
public abstract class Implementation {

	/**
	 * Gets the name of the command.
	 * 
	 * @return the name of the command.
	 */
	public abstract String getName();

	/**
	 * Executes the main procedure of the command with a given context, and return the
	 * success of the operation.
	 * 
	 * @param instance the context of the command that is being executed.
	 * @return {@code true} if the operation was successful; {@code false} otherwise.
	 * @see {@link #doPlayerCommand(CommandInstance)}
	 * @see {@link #doConsoleCommand(CommandInstance)}
	 */
	public boolean doCommand(CommandInstance instance) {
		if (instance.isFromPlayer())
			return this.doPlayerCommand(instance);
		else 
			return this.doConsoleCommand(instance);
	}

	/**
	 * Executes the main procedure of a player-issued command with a given context, and
	 * return the success of the operation. Ideally, the code in this method should work
	 * when the command is sent from either a player or the console. That way, it only
	 * to be implemented once in this method. If the code here is not compatible with a
	 * command sent from the console, then the {@link #doConsoleCommand(CommandInstance)}
	 * method must be overridden.
	 * 
	 * @param instance the context of the command that is being executed.
	 * @return {@code true} if the operation was successful; {@code false} otherwise.
	 */
	protected abstract boolean doPlayerCommand(CommandInstance instance);

	/**
	 * Executes the main procedure of a console-issued command with a given context, and
	 * return the success of the operation. This method should only be overridden by a
	 * subclass if the {@link #doPlayerCommand(CommandInstance)} method does
	 * not work when the command is sent from a console. By default, this method will
	 * use the same procedure outlined in the {@link #doPlayerCommand(CommandInstance)}
	 * method.
	 * 
	 * @param instance the context of the command that is being executed
	 * @return {@code true} if the operation was successful; {@code false} otherwise.
	 */
	protected boolean doConsoleCommand(CommandInstance instance) {
		return this.doPlayerCommand(instance);
	}

}
