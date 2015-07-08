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
 * 
 * @author Zach Ohara
 */
public class DummyPluginMain extends CommonPlugin {

	@Override
	public Class<? extends CommandRules> getCommandRuleSet() {
		return null;
	}

	@Override
	public Class<? extends CommandExecutables> getCommandExecutableSet() {
		return null;
	}

}
