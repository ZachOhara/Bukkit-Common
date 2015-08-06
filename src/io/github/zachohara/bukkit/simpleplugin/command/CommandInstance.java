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

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * A {@code CommandInstance} object represents a single invocation of any command that is
 * registered to a plugin. The object stores relevant information about the command, such
 * as the player or console that sent the command, the player that was targeted by the
 * command (if applicable), as well as any additional arguments that were sent with the
 * command.
 *
 * @author Zach Ohara
 */
public class CommandInstance {

	/**
	 * The name of the command that was called.
	 */
	private final String name;

	/**
	 * The arguments (if any) that were sent along with the command.
	 */
	private final String[] arguments;

	/**
	 * The {@code Properties} of the command.
	 */
	private final Properties properties;

	/**
	 * The entity that sent the command. This may be a player or the console.
	 */
	private CommandSender senderRaw;

	/**
	 * The player that sent the command. {@code null} if the command was sent by the
	 * console.
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
	 * The name that was supplied as a target, regardless of whether or not that name is a
	 * valid target.
	 */
	private String givenTarget;

	/**
	 * Constructs a new {@code CommandInstance} based on availble information about the
	 * command.
	 *
	 * @param rawSender the entity that sent the command.
	 * @param rawCommand a {@code Command} object representing the command.
	 * @param args all additional arguments sent with the command.
	 * @param commandSet the {@code Class} object from the applicable {@link CommandRules}
	 * enumeration.
	 * @param exeSet the {@code Class} object from the applicable
	 * {@link CommandExecutables} enumeration.
	 */
	public <T extends CommandSet> CommandInstance(CommandSender rawSender, Command rawCommand, String[] args,
			Class<T> commandSet) {
		this.name = rawCommand.getName().toLowerCase();
		this.arguments = args;
		this.properties = CommandInstance.propertiesFromString(this.name, commandSet);
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
	 * @return {@code true} if the command was sent by the console; {@code false}
	 * otherwise.
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
	 * Gets the name of the command that was sent. If a command alias was sent instead of
	 * an actual command, the name of the command represented by the alias will be returned
	 * instead.
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
	 * Gets the {@code CommandSender} object that sent this command, regardless of whether
	 * the command was sent by a player or console.
	 *
	 * @return the entity that sent this command.
	 */
	public CommandSender getSender() {
		return this.senderRaw;
	}

	/**
	 * Gets the {@code Player} object that sent this command, if the command was sent by a
	 * player. Returns {@code null} if the command was not sent by a player.
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
	 * Gets the name of the target player attached to this command. If the specified target
	 * player is not a valid, online player, whatever was specified as the target player
	 * will be returned.
	 *
	 * @return the name of the target player attached to this command.
	 */
	public String getTargetName() {
		if (this.hasTarget()) {
			return this.targetName;
		} else {
			return this.givenTarget;
		}
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
	 * Checks the validity of the conditions that this command was sent with. The
	 * {@code Properties} class is ultimately responsible for this verification, so this
	 * method calls and returns the value of
	 * {@link Properties#verifyCommand(CommandInstance)}
	 *
	 * @return {@code true} if and only if all prerequisite conditions for the command are
	 * met; {@code false} otherwise.
	 */
	public boolean verifyCommand() {
		return this.properties.verifyCommand(this);
	}

	/**
	 * Executes the 'main procedure' of the command after its conditions have been fully
	 * verified.
	 *
	 * @see Implementation#doCommand(CommandInstance)
	 */
	public void executeCommand() {
		this.properties.getImplementation().doCommand(this);
	}

	/**
	 * Sends a given message to all players and consoles on the server. The message will be
	 * formatted and colored before it is sent.
	 *
	 * @param message the message to be sent.
	 */
	public void broadcastMessage(String message) {
		Bukkit.getServer().broadcastMessage(StringUtil.parseString(message, this));
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
	 * Sends a given error message to the console of the server. The error message will be
	 * formatted and colored before it is sent.
	 *
	 * @param message the error message to be sent.
	 */
	public void logConsoleError(String message) {
		Bukkit.getConsoleSender().sendMessage(StringUtil.parseError(message, this));
	}

	/**
	 * Gets the {@code Properties} object corresponding to the command with the given name.
	 *
	 * @param name the name of the requested command.
	 * @param ruleSet the {@code Class} object of the specific set of command rules.
	 * @return the rules for the given command.
	 */
	@SuppressWarnings("unchecked")
	private static Properties propertiesFromString(String name, Class<? extends CommandSet> ruleSet) {
		CommandSet[] allCommands = ruleSet.getEnumConstants();
		for (CommandSet command : allCommands) {
			Enum<? extends CommandSet> enumConst = (Enum<? extends CommandSet>) command;
			if (enumConst.name().equalsIgnoreCase(name)) {
				return command.getProperties();
			}
		}
		throw new IllegalArgumentException("Nothing was found with a name matching " + name);
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
		if (this.senderRaw instanceof Player) {
			this.senderPlayer = (Player) this.senderRaw;
		}
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
		if (this.arguments.length > 0) {
			this.givenTarget = this.arguments[0];
		} else {
			this.givenTarget = "";
		}
		if (this.properties.useTarget() && this.givenTarget != "") {
			this.targetPlayer = Bukkit.getPlayer(this.givenTarget);
		}
		if (this.hasTarget()) {
			this.targetName = this.targetPlayer.getName();
		} else {
			this.targetName = "[No Target]";
		}
	}

}
