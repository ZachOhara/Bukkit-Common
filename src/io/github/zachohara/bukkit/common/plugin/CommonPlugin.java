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

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import io.github.zachohara.bukkit.common.command.CommandExecutables;
import io.github.zachohara.bukkit.common.command.CommandInstance;
import io.github.zachohara.bukkit.common.command.CommandRules;
import io.github.zachohara.bukkit.common.persistence.PersistentObject;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The supertype for all plugins that use the common system.
 *
 * @author Zach Ohara
 */
public abstract class CommonPlugin extends JavaPlugin {
	
	/**
	 * The list of all {@code PersistentData} objects that have been registered to this
	 * plugin.
	 */
	private List<PersistentObject> persistentData;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onEnable() {
		persistentData = new LinkedList<PersistentObject>();
	}
	
	/**
	 * Safely closes the plugin. This method is called anytime before the plugin is
	 * disabled on the server, including during the server shutdown procedure. This
	 * method will close and save all of the registered {@code PersistentData} objects
	 * registered to this plugin.
	 */
	@Override
	public void onDisable() {
		super.onDisable();
		for (PersistentObject obj : this.persistentData) {
			try {
				obj.saveToFile();
			} catch (IOException e) {
				this.getLogger().warning("Unable to save offline persitent data: " + obj);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		CommandInstance instance = new CommandInstance(sender, command, args,
				this.getCommandRuleSet(), this.getCommandExecutableSet());
		if (instance.verifyCommand()) {
			instance.executeCommand();
		}
		return true;
	}
	
	/**
	 * Gets the enumeration of {@code CommandRules} that represents the set of commands
	 * that are specific to a plugin.
	 * 
	 * @return the {@code CommandRules} enumeration for this plugin.
	 */
	public abstract Class<? extends CommandRules> getCommandRuleSet();
	
	/**
	 * Gets the enumeration of {@code CommandExecutables} that represents the set of commands
	 * that are specific to a plugin.
	 * 
	 * @return the {@code CommandExecutables} enumeration for this plugin.
	 */
	public abstract Class<? extends CommandExecutables> getCommandExecutableSet();
	
	/**
	 * Register the given {@code PersistentObject} with this plugin.
	 * 
	 * @param data the {@code PersistentObject} to register.
	 */
	public void regiserPersistentObject(PersistentObject data) {
		this.persistentData.add(data);
	}
	
	/**
	 * Prints a console message that explains this file's nature as a Bukkit plugin, not a java app.
	 * 
	 * @param args the command line arguments passed to the program.
	 */
	public static void main(String[] args) {
		System.out.println("This is not a normal java app! This is a plugin for a Bukkit server!\n"
				+ "To use it, you have to install Bukkit, then install this plugin.");
	}
	
}
