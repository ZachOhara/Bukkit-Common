package io.github.zachohara.bukkit.common.command;

import io.github.zachohara.bukkit.common.command.CommandRules.Source;
import io.github.zachohara.bukkit.common.command.CommandRules.Target;

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
	 * The maximum amount of arguments that should be allowed for the command.
	 */
	private int maxArgs;
	
	/**
	 * The type or range of sources that are allowed to use the command.
	 */
	private Source accessible;
	
	/**
	 * The type or range of target players that should be allowed to use this command.
	 */
	private Target targetable;
	
	/**
	 * Constructs a new {@code Rules} object based on the required information.
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
	 * @param name the name of this command.
	 * @param alias the {@code Command} object that this object should mimic the
	 * properties of.
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
	 * @return the name of the command, as it would be typed in the game or from a
	 * console.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets the minimum amount of arguments that should be allowed for the command.
	 * @return the minimum amount of arguments that should be allowed for the command.
	 */
	public int getMinArgs() {
		return minArgs;
	}
	
	/**
	 * Gets the maximum amount of arguments that should be allowed for the command.
	 * @return the maximum amount of arguments that should be allowed for the command.
	 */
	public int getMaxArgs() {
		return maxArgs;
	}
	
	/**
	 * Gets the type or range of sources that are allowed to use the command.
	 * @return the type or range of sources that are allowed to use the command.
	 * @see {@link #Commands.Source Commands.Source}
	 */
	public Source getAccessible() {
		return accessible;
	}
	
	/**
	 * Gets the type or range of target players that should be allowed to use this command.
	 * @return the type or range of target players that should be allowed to use this
	 * command.
	 * @see {@link #Commands.Target Commands.Target}
	 */
	public Target getTargetable() {
		return targetable;
	}

}
