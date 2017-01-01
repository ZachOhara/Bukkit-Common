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

import io.github.zachohara.bukkit.simpleplugin.command.CommandInstance;
import io.github.zachohara.bukkit.simpleplugin.command.CommandSet;
import io.github.zachohara.bukkit.simpleplugin.command.Implementation;
import io.github.zachohara.bukkit.simpleplugin.command.Properties;
import io.github.zachohara.bukkit.simpleplugin.command.Properties.Source;
import io.github.zachohara.bukkit.simpleplugin.command.Properties.Target;
import io.github.zachohara.bukkit.simpleplugin.plugin.SimplePlugin;

/**
 * The {@code Commands} interface represents the set of commands supported by this plugin,
 * and contains a {@code Properties} object for each command.
 *
 * @author Zach Ohara
 * @see Properties
 */
public enum Commands implements CommandSet {
	
	SIMPLEPLUGIN(new Properties(0, 0, Source.ALL, Target.NONE, new SimplePluginCommand()));
	
	/**
	 * The {@code Properties} object specific to a single command.
	 */
	private Properties properties;

	/**
	 * Constructs a new {@code Commands} with the given {@code Properties} for this
	 * command.
	 *
	 * @param p the {@code Properties} for this command.
	 */
	private Commands(Properties p) {
		this.properties = p;
	}

	@Override
	public Properties getProperties() {
		return this.properties;
	}

	/**
	 * The implementation for the 'simpleplugin' command.
	 */
	private static final class SimplePluginCommand extends Implementation {

		@Override
		protected boolean doPlayerCommand(CommandInstance instance) {
			instance.sendMessage("This server is currently running the @nameSimplePlugin@text library. Here are all the other plugins on this server that use @nameSimplePlugin@text:");
			String message = "";
			for (String pluginName : SimplePlugin.getActivePluginList()) {
				message += "@name" + pluginName + "@text, ";
			}
			message = message.substring(0, message.length() - 2);
			instance.sendMessage(message);
			return true;
		}
		
	}
	
}
