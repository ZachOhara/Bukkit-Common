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

package io.github.zachohara.bukkit.simpleplugin.command;

import io.github.zachohara.bukkit.simpleplugin.util.PlayerUtil;
import io.github.zachohara.bukkit.simpleplugin.util.StringUtil;

/**
 * A {@code Properties} object is specific to a single command. It should contain information
 * about the expected context of a command, such as the allowed amount of arguments, and the
 * required permission level of the sender. A {@code Properties} object is also responsible for
 * verifying the conditions of any given command.
 *
 * @author Zach Ohara
 */
public class Properties {

	/**
	 * The minimum amount of arguments that should be allowed for the command.
	 */
	private final int minArgs;

	/**
	 * The maximum amount of arguments that should be allowed for the command, or
	 * {@code -1} if there is no maximum amount of arguments.
	 */
	private final int maxArgs;

	/**
	 * The type or range of sources that are allowed to use the command.
	 */
	private final Properties.Source accessible;

	/**
	 * The type or range of players that can be targeted by the command.
	 */
	private final Properties.Target targetable;
	
	/**
	 * The subclass of {@code Implementation} that contains an implementation for the
	 * command.
	 */
	private final Implementation implementation;
	
	/**
	 * Constructs a new {@code Properties} that exactly mimics the properties of the given command entry
	 *
	 * @param other the comand entry to mimic the properties of.
	 * @param implement see instance variable {@link #implementation}
	 */
	public Properties(CommandSet other, Implementation implement) {
		this(other.getProperties(), implement);
	}
	
	/**
	 * Constructs a new {@code Properties} that exactly mimics the given {@code Properties} object.
	 *
	 * @param other the {@code Properties} that this object should mimic.
	 * @param implement see instance variable {@link #implementation}
	 */
	public Properties(Properties other, Implementation implement) {
		this(other.minArgs, other.maxArgs, other.accessible, other.targetable, implement);
	}
	
	/**
	 * Constructs a new {@code Properties} with all the required information.
	 *
	 * @param minArgs see instance variable {@link #minArgs}
	 * @param maxArgs see instance variable {@link #maxArgs}
	 * @param access see instance variable {@link #accessible}
	 * @param target see instance variable {@link #targetable}
	 * @param implement see instance variable {@link #implementation}
	 */
	public Properties(int minArgs, int maxArgs, Source access,
			Target target, Implementation implement) {
		this.minArgs = minArgs;
		this.maxArgs = maxArgs;
		this.accessible = access;
		this.targetable = target;
		this.implementation = implement;
	}
	
	/**
	 * Determines if the command represented by this {@code Properties} should use a target.
	 * This is simply done by checking that the target setting is not {@code NONE};
	 *
	 * @return {@code true} if the command uses a target; {@code false} otherwise.
	 */
	public boolean useTarget() {
		return this.targetable != Target.NONE;
	}

	/**
	 * Gets the subclass of {@code Implementation} that contains an implementation for the
	 * command.
	 *
	 * @return the {@code Implementation} of the command.
	 * @see Implementation
	 */
	public Implementation getImplementation() {
		return this.implementation;
	}

	/**
	 * Checks the validity of the conditions that this command was sent with. The method
	 * will verify the following things about the conditions of the command:
	 * <ol>
	 * <li>An appropriate number of arguments were sent with the command.</li>
	 * <li>The target player that was specified with the command (if applicable) is a valid
	 * player given the conditions of the specific command.</li>
	 * <li>The entity that sent the command has permission to use the specific command.</li>
	 * </ol>
	 * If all the above conditions are met given the circumstances, the command has been
	 * successfully verified, and this method will return {@code true}.
	 *
	 * @return {@code true} if and only if all prerequisite conditions for the command are
	 * met; {@code false} otherwise.
	 * @see #verifyArguments()
	 * @see #verifyValidTarget()
	 * @see #verifyValidSource()
	 */
	public boolean verifyCommand(CommandInstance command) {
		return this.verifyValidArguments(command) && this.verifyValidTarget(command) && this.verifyValidSource(command);
	}

	/**
	 * Verifies that the given command was sent with an appropriate amount of arguments. If the
	 * amount of arguments is not valid, this method will return an appropriate response to
	 * the player or console that sent the command.
	 *
	 * @return {@code true} if and only if the amount of arguments that were sent with the
	 * command match the expected conditions for the command; {@code false} otherwise.
	 * @see #verifyCommand()
	 */
	private boolean verifyValidArguments(CommandInstance command) {
		if (command.getArguments().length < this.minArgs) {
			command.sendError(StringUtil.ERROR_TOO_FEW_ARGS_MESSAGE);
			return false;
		}
		if (this.maxArgs != -1 && command.getArguments().length > this.maxArgs) {
			command.sendError(StringUtil.ERROR_TOO_MANY_ARGS_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * Verifies that the specified target player is a valid target player that is not
	 * specially protected from this command. If the specified target player is not valid,
	 * this method will return an appropriate response to the player or console that sent
	 * the command.
	 *
	 * @return {@code true} if and only if the specified target player is a valid target
	 * for this command; {@code false} otherwise.
	 * @see #verifyCommand()
	 */
	private boolean verifyValidTarget(CommandInstance command) {
		switch (this.targetable) {
			case NONE:
				return true;
			case RESTRICT_ADMIN:
				if (command.getGivenTarget().equalsIgnoreCase(PlayerUtil.getAdminName())) {
					command.sendMessage(StringUtil.ERROR_ADMIN_PROTECTED_MESSAGE);
					command.reportToAdmins(StringUtil.ERROR_ADMIN_PROTECTED_ADMIN_NOTIFICATION);
					return false;
				} else {
					return true;
				}
			case IF_SENDER_OP:
				if (command.hasTarget() && command.isFromPlayer() && !command.getSenderPlayer().isOp()) {
					command.sendError(StringUtil.ERROR_TARGET_ONLY_IF_OP);
					return false;
				} else {
					return true;
				}
			case ALL_ONLINE:
				if (command.hasTarget() || command.getArguments().length == 0) {
					return true;
				} else {
					command.sendError(StringUtil.ERROR_TARGET_OFFLINE_MESSAGE);
					return false;
				}
			case ALLOW_OFFLINE:
				return true;
			default:
				command.logConsoleError("An unexpected error occured. Try updating the server's plugins!");
				throw new UnsupportedOperationException("An unexpected value of Properties.Target was found.");
		}
	}

	/**
	 * Verifies that the entity that sent this command has permission to do so. If the
	 * sender does not have the required permission to use this command, this method will
	 * return an appropriate response to the player or console that sent this command.
	 *
	 * @return {@code true} if and only if the entity that sent this command has the
	 * required permission and ability to do so; {@code false} otherwise.
	 * @see #verifyCommand()
	 */
	private boolean verifyValidSource(CommandInstance command) {
		switch (this.accessible) {
			case ALL:
				return true;
			case PLAYER_ONLY:
				if (command.isFromPlayer()) {
					return true;
				} else {
					command.sendError(StringUtil.ERROR_PLAYER_ONLY_MESSAGE);
					return false;
				}
			case OP_ONLY:
				if (command.isFromConsole() || command.getSenderPlayer().isOp()) {
					return true;
				} else {
					command.sendError(StringUtil.ERROR_NOT_OP_MESSAGE);
					return false;
				}
			case ADMIN_ONLY:
				if (command.isFromConsole() || PlayerUtil.playerIsAdmin(command.getSenderPlayer())) {
					return true;
				} else {
					command.sendMessage(StringUtil.ERROR_ADMIN_ONLY_MESSAGE);
					command.reportToAdmins(StringUtil.ERROR_ADMIN_ONLY_ADMIN_NOTIFICATION);
					return false;
				}
			case ADMIN_PLAYER_ONLY:
				if (command.isFromPlayer() && PlayerUtil.playerIsAdmin(command.getSenderPlayer())) {
					return true;
				} else if (!command.isFromPlayer()) {
					command.sendError(StringUtil.ERROR_PLAYER_ONLY_MESSAGE);
					return false;
				} else {
					command.sendError(StringUtil.ERROR_ADMIN_ONLY_MESSAGE);
					return false;
				}
			default:
				command.logConsoleError("An unexpected error occured. Try updating the server's plugins!");
				throw new UnsupportedOperationException(
						"An unexpected value of Properties.Source was found.");
		}
	}

	/**
	 * The set of possible sources, or ranges of sources, that may be allowed to
	 * use any single command.
	 */
	public static enum Source {
		ALL,
		PLAYER_ONLY,
		OP_ONLY,
		ADMIN_ONLY,
		ADMIN_PLAYER_ONLY,
		CONSOLE_ONLY
	}

	/**
	 * The set of possible targets, or ranges of targets, that may be allowed to
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
