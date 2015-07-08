package io.github.zachohara.bukkit.common.command;

public interface CommandRules {

	/**
	 * Gets the name of the command, as it would be typed in the game or from a console.
	 * @return the name of the command, as it would be typed in the game or from a
	 * console.
	 */
	public String getName();

	/**
	 * Gets the minimum amount of arguments that should be allowed for the command.
	 * @return the minimum amount of arguments that should be allowed for the command.
	 */
	public int getMinArgs();

	/**
	 * Gets the maximum amount of arguments that should be allowed for the command.
	 * @return the maximum amount of arguments that should be allowed for the command.
	 */
	public int getMaxArgs();

	/**
	 * Gets the type or range of sources that are allowed to use the command.
	 * @return the type or range of sources that are allowed to use the command.
	 * @see {@link #Commands.Source Commands.Source}
	 */
	public Source getAccessible();

	/**
	 * Gets the type or range of target players that should be allowed to use this command.
	 * @return the type or range of target players that should be allowed to use this
	 * command.
	 * @see {@link #Commands.Target Commands.Target}
	 */
	public Target getTargetable();

	/**
	 * Represents the set of possible sources, or ranges of sources, that may be allowed
	 * to use any single command.
	 */
	public static enum Source {
		ALL,
		OP_ONLY,
		ADMIN_ONLY
	}
	
	/**
	 * Represents the set of possible targets, or ranges of targets, that may be allowed
	 * to be targeted by any single command.
	 */
	public static enum Target {
		NONE,
		ALL,
		RESTRICT_ADMIN
	}

}
