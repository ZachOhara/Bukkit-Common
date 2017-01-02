/* Copyright (C) 2016 Zach Ohara
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

package io.github.zachohara.bukkit.simpleplugin.plugin.main;

import io.github.zachohara.bukkit.simpleplugin.command.CommandSet;
import io.github.zachohara.bukkit.simpleplugin.plugin.SimplePlugin;

/**
 * The {@code SimplePluginMain} class is a dummy class that acts as the main class of
 * SimplePlugin itself. A main class is required for all bukkit plugins, including this
 * one, even though SimplePlugin alone adds no new funcionality to a server.
 *
 * @author Zach Ohara
 */
public final class SimplePluginMain extends SimplePlugin {
	
	@Override
	public void onEnable() {
		super.onEnable();
		this.saveDefaultConfig();
	}

	@Override
	public Class<? extends CommandSet> getCommandSet() {
		return Commands.class;
	}

}
