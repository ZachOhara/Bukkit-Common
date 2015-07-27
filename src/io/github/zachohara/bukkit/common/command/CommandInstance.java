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

import io.github.zachohara.bukkit.common.util.PlayerUtil;
import io.github.zachohara.bukkit.common.util.StringUtil;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * A {@code CommandInstance} object represents a single invocation of any command that is
 * registered to a plugin. The object stores relevant information about the command, such
 * as the player or console that sent the command, the player that was targeted by the command
 * (if applicable), as well as any additional arguments that were sent with the command.
 * 
 * @author Zach Ohara
 */
public class CommandInstance {

	/**
	 * The name of the command that was called.
	 */
	private String name;

	/**
	 * The arguments (if any) that were sent along with the command.
	 */
	private String[] arguments;

	/**
	 * The general, non-instance-specific format and properties of the sent command.
	 */
	private CommandRulesEntry rules;

	/**
	 * The executable object that contains an implementation for the
	 * command.
	 */
	private Implementation commandImplementation;


	/**
	 * The entity that sent the command. This may be a player or the console.
	 */
	private CommandSender senderRaw;

	/**
	 * The player that sent the command. {@code null} if the command was sent by the console.
	 */
	private Player senderPlayer;

	/**
	 * The name of the entity that sent the command.
	 */
	private String senderName;


	/**
	 * The player that was targeted by the command (if applicable).
	 */
	private Player targetPlayer;

	/**
	 * The name of the player that was targeted by the command (if applicable).
	 */
	private String targetName;

	/**
	 * The name that was supplied as a target, regardless of whether or not that name is
	 * a valid target.
	 */
	private String givenTarget;


	/**
	 * Constructs a new {@code CommandInstance} based on availble information about the
	 * command.
	 * @param rawSender the entity that sent the command.
	 * @param rawCommand a {@code Command} object representing the command.
	 * @param args all additional arguments sent with the command.
	 * @param ruleSet the {@code Class} object from the applicable
	 * {@code {@link CommandRules}} enumeration.
	 * @param exeSet the {@code Class} object from the applicable
	 * {@code {@link CommandExecutables}} enumeration.
	 */
	public CommandInstance(CommandSender rawSender, Command rawCommand, String[] args,
			Class<? extends CommandRules> ruleSet, Class<? extends CommandExecutables> exeSet) {
		this.name = rawCommand.getName().toLowerCase();
		this.arguments = args;
		this.rules = rulesFromString(this.name, ruleSet);
		this.commandImplementation = implementFromString(this.name, exeSet);
		this.initializeSender(rawSender);		
		this.initializeSenderName();
		this.initializeTarget();
	}

	/**
	 * Returns {@code true} if an in-game player sent the command, or {@code false} if the
	 * command was sent by the server console.
	 * 
	 * @return {@code true} if the command was sent by a player; {@code false} otherwise.
	 * @see #isFromConsole()
	 */
	public boolean isFromPlayer() {
		return this.senderPlayer != null;
	}

	/**
	 * Returns {@code true} if the server console sent the command, or {@code false} if an
	 * in-game player sent the command.
	 * 
	 * @return {@code true} if the command was sent by the console; {@code false} otherwise.
	 * @see #isFromPlayer()
	 */
	public boolean isFromConsole() {
		return this.senderPlayer == null;
	}

	/**
	 * Returns {@code true} if and only if this command was send with a valid, online
	 * player as a target.
	 * 
	 * @return {@code true} if there is a valid target player attached to this command;
	 * {@code false} otherwise.
	 */
	public boolean hasTarget() {
		return this.targetPlayer != null;
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
	public boolean verifyCommand() {
		return this.verifyArguments()
				&& this.verifyValidTarget()
				&& this.verifyValidSource();
	}

	/**
	 * Verifies that the command was sent with an appropriate amount of arguments. If the
	 * amount of arguments is not valid, this method will return an appropriate response
	 * to the player or console that sent the command.
	 * 
	 * @return {@code true} if and only if the amount of arguments that were sent with
	 * the command match the expected conditions for the command; {@code false} otherwise.
	 * @see #verifyCommand()
	 */
	private boolean verifyArguments() {
		if (this.arguments.length < this.rules.getMinArgs()) {
			this.sendError(StringUtil.ERROR_TOO_FEW_ARGS_MESSAGE);
			return false;
		}
		if (this.rules.getMaxArgs() != -1
				&& this.arguments.length > this.rules.getMaxArgs()) {
			this.sendError(StringUtil.ERROR_TOO_MANY_ARGS_MESSAGE);
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
	 * for this command;  {@code false} otherwise.
	 * @see #verifyCommand()
	 */
	private boolean verifyValidTarget() {
		switch (this.rules.getTargetable()) {
		case NONE:
			return true;
		case RESTRICT_ADMIN:
			if (this.givenTarget.equalsIgnoreCase(PlayerUtil.getAdminName())) {
				this.sendMessage(StringUtil.ERROR_ADMIN_PROTECTED_MESSAGE);
				this.reportToAdmins(StringUtil.ERROR_ADMIN_PROTECTED_ADMIN_NOTIFICATION);
				return false;
			} else {
				return true;
			}
		case IF_SENDER_OP:
			if (this.hasTarget() && this.isFromPlayer() && !this.getSenderPlayer().isOp()) {
				this.sendError(StringUtil.ERROR_TARGET_ONLY_IF_OP);
				return false;
			} else {
				return true;
			}
		case ALL_ONLINE:
			if (this.hasTarget() || this.arguments.length == 0) {
				return true;
			} else {
				this.sendError(StringUtil.ERROR_TARGET_OFFLINE_MESSAGE);
				return false;
			}
		case ALLOW_OFFLINE:
			return true;
		default:
			this.logConsoleError("An unexpected error occured. Try updating the server's plugins!");
			throw new UnsupportedOperationException("An unexpected value of CommandRules.Target was found.");
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
	private boolean verifyValidSource() {
		switch(this.rules.getAccessible()) {
		case ALL:
			return true;
		case PLAYER_ONLY:
			if (this.isFromPlayer()) {
				return true;
			} else {
				this.sendError(StringUtil.ERROR_PLAYER_ONLY_MESSAGE);
				return false;
			}
		case OP_ONLY:
			if (this.isFromConsole() || this.senderPlayer.isOp()) {
				return true;
			} else {
				this.sendError(StringUtil.ERROR_NOT_OP_MESSAGE);
				return false;
			}
		case ADMIN_ONLY:
			if (this.isFromConsole() || PlayerUtil.playerIsAdmin(this.senderPlayer)) {
				return true;
			} else {
				this.sendMessage(StringUtil.ERROR_ADMIN_ONLY_MESSAGE);
				this.reportToAdmins(StringUtil.ERROR_ADMIN_ONLY_ADMIN_NOTIFICATION);
				return false;
			}
		case ADMIN_PLAYER_ONLY:
			if (this.isFromPlayer() && PlayerUtil.playerIsAdmin(this.senderPlayer)) {
				return true;
			} else if (!this.isFromPlayer()) {
				this.sendError(StringUtil.ERROR_PLAYER_ONLY_MESSAGE);
				return false;
			} else {
				this.sendError(StringUtil.ERROR_ADMIN_ONLY_MESSAGE);
				return false;
			}
		default:
			this.logConsoleError("An unexpected error occured. Try updating the server's plugins!");
			throw new UnsupportedOperationException("An unexpected value of CommandRules.Source was found.");
		}
	}

	/**
	 * Executes the 'main procedure' of the command after its conditions have been
	 * fully verified.
	 * 
	 * @see Implementation#doCommand(CommandInstance)
	 */
	public void executeCommand() {
		this.commandImplementation.doCommand(this);
	}

	/**
	 * Sends a given message to the target player specified by this command. The message
	 * will be formatted and colored before it is sent. If there is no valid target player
	 * attached to this command, a {@code NullPointerException} will be thrown.
	 * 
	 * @param message the message to be sent.
	 */
	public void sendTargetMessage(String message) {
		this.targetPlayer.sendMessage(StringUtil.parseString(message, this));
	}

	/**
	 * Sends a given message to all players and consoles on the server. The message will
	 * be formatted and colored before it is sent.
	 * 
	 * @param message the message to be sent.
	 */
	public void broadcastMessage(String message) {
		Bukkit.getServer().broadcastMessage(StringUtil.parseString(message, this));
	}

	/**
	 * Sends a given message to the player or console that sent this command. The message
	 * will be formatted before and colored it is sent.
	 * 
	 * @param message the message to be sent.
	 */
	public void sendMessage(String message) {
		this.senderRaw.sendMessage(StringUtil.parseString(message, this));
	}

	/**
	 * Sends a given error message to the player or console that sent this command. The
	 * error message will be formatted and colored before it is sent.
	 * 
	 * @param message the error message to be sent.
	 */
	public void sendError(String message) {
		this.senderRaw.sendMessage(StringUtil.parseError(message, this));
	}

	/**
	 * Sends a given message to the console and to the admin of the server. The message
	 * will be formatted and colored before it is sent.
	 * 
	 * @param message the message to be sent.
	 */
	public void reportToAdmins(String message) {
		String formattedMessage = StringUtil.parseString(message, this);
		PlayerUtil.sendAllAdmins(formattedMessage);
	}

	/**
	 * Sends a given error message to the console of the server. The error message will
	 * be formatted and colored before it is sent.
	 * 
	 * @param message the error message to be sent.
	 */
	public void logConsoleError(String message) {
		Bukkit.getConsoleSender().sendMessage(StringUtil.parseError(message, this));
	}

	/**
	 * Gets the name of the command that was sent. If a command alias was sent instead of
	 * an actual command, the name of the command represented by the alias will be
	 * returned instead.
	 * 
	 * @return the name of the command that was sent.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the arguments that were sent with this command.
	 * 
	 * @return the arguments that were sent with this command.
	 */
	public String[] getArguments() {
		return this.arguments;
	}

	/**
	 * Gets the {@code CommandSender} object that sent this command, regardless of
	 * whether the command was sent by a player or console.
	 * 
	 * @return the entity that sent this command.
	 */
	public CommandSender getSender() {
		return this.senderRaw;
	}

	/**
	 * Gets the {@code Player} object that sent this command, if the command was sent
	 * by a player. Returns {@code null} if the command was not sent by a player.
	 * 
	 * @return the {@code Player} that sent this command.
	 */
	public Player getSenderPlayer() {
		return this.senderPlayer;
	}

	/**
	 * Gets the name of the player that sent this command or {@code "The Console"} if the
	 * command was sent by the console.
	 * 
	 * @return the name of the entity that sent this command.
	 */
	public String getSenderName() {
		return this.senderName;
	}

	/**
	 * Gets the target player attached to this command, or {@code null} if no valid target
	 * player was specified.
	 * 
	 * @return the target player attached to this command.
	 */
	public Player getTargetPlayer() {
		return this.targetPlayer;
	}

	/**
	 * Gets the name of the target player attached to this command. If the specified
	 * target player is not a valid, online player, whatever was specified as the target
	 * player will be returned.
	 * 
	 * @return the name of the target player attached to this command.
	 */
	public String getTargetName() {
		if (this.hasTarget())
			return this.targetName;
		else
			return this.givenTarget;
	}

	/**
	 * Gets the name of the player that was specified as the target of this command,
	 * regardless of whether the specified player is valid.
	 * 
	 * @return the specified name of the target player.
	 */
	public String getGivenTarget() {
		return this.givenTarget;
	}

	/**
	 * Gets the {@code CommandRulesEntry} object corresponding to the command with the
	 * given name.
	 * 
	 * @param label the name of the requested command.
	 * @param ruleSet the {@code Class} object of the specific set of command rules.
	 * @return the rules for the given command.
	 */
	private static CommandRulesEntry rulesFromString(String label, Class<? extends CommandRules> ruleSet) {
		CommandRules[] allCommands = ruleSet.getEnumConstants();
		for (CommandRules c : allCommands) {
			if (c.getRulesEntry().getName().equals(label))
				return c.getRulesEntry();
		}
		return null;
	}

	/**
	 * Gets the {@code Implementation} object corresponding to the command with the given name.
	 * 
	 * @param name the name of the command that should be returned.
	 * @param exeSet the {@code Class} object of the specific set of command executables.
	 * @return an {@code Implementation} for the given command.
	 */
	private static Implementation implementFromString(String name, Class<? extends CommandExecutables> exeSet) {
		CommandExecutables[] all = exeSet.getEnumConstants();
		for (CommandExecutables exe : all) {
			if (exe.getImplementation().getName().equals(name))
				return exe.getImplementation();
		}
		return null;
	}

	/**
	 * Determines if the command was sent by a console or a player, and initializes this
	 * {@code CommandInstance} object accordingly.
	 * 
	 * @param sender the raw {@code CommandSender} object that represents the source of
	 * this command.
	 */
	private void initializeSender(CommandSender sender) {
		this.senderRaw = sender;
		if (this.senderRaw instanceof Player)
			this.senderPlayer = (Player) this.senderRaw;
	}

	/**
	 * Initializes instance variables according to the name of the player that sent this
	 * command, or {@code "The Console"} if the command was sent by a console. 
	 */
	private void initializeSenderName() {
		if (this.isFromPlayer()) {
			this.senderName = this.senderPlayer.getName();
		} else {
			this.senderName = "The Console";
		}
	}

	/**
	 * Determines if a target player was specified as an argument to this command, and
	 * initializes instance variables accordingly.
	 */
	@SuppressWarnings("deprecation")
	private void initializeTarget() {
		if (this.arguments.length > 0)
			this.givenTarget = this.arguments[0];
		else
			this.givenTarget = "";
		if (this.rules.getTargetable() != CommandRules.Target.NONE && this.givenTarget != "")
			this.targetPlayer = Bukkit.getPlayer(this.givenTarget);
		if (this.hasTarget())
			this.targetName = this.targetPlayer.getName();
		else
			this.targetName = "[No Target]";
	}

}
