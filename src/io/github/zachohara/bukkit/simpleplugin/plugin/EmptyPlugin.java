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

package io.github.zachohara.bukkit.simpleplugin.plugin;

import io.github.zachohara.bukkit.simpleplugin.command.CommandSet;

/**
 * The {@code SimplePlugin} class acts as a supertype for the main class of any plugin that
 * uses the SimplePlugin library, but does not add any additional commands. All non-command
 * functionality must be added by overriding the {@code onEnable() method}, or a similar
 * method that is provided through the Bukkit API.
 *
 * @author Zach Ohara
 */
public class EmptyPlugin extends SimplePlugin {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<? extends CommandSet> getCommandSet() {
		return null;
	}

}
