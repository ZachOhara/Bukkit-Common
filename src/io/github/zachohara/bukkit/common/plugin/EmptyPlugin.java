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

 package io.github.zachohara.bukkit.common.plugin;

import io.github.zachohara.bukkit.common.command.CommandExecutables;
import io.github.zachohara.bukkit.common.command.CommandRules;

/**
 * This class only exists so that the Bukkit Common library can be loaded into Bukkit
 * on its own, and not through another plugin. It is essentially an empty implementation
 * of the {@code CommonPlugin} abstract class, which cannot be used as an entry point
 * because it is abstract.
 * <p>
 * This class could, in theory, also be used as the main class for a plugin that has no
 * commands. The main class for this hypothetical plugin would need to extend this class,
 * and add its own functionality through the {@code onEnable()} method, or similar methods
 * that are provided through the Bukkit API.
 *  
 * @author Zach Ohara
 */
public class EmptyPlugin extends CommonPlugin {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<? extends CommandRules> getCommandRuleSet() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<? extends CommandExecutables> getCommandExecutableSet() {
		return null;
	}

}
