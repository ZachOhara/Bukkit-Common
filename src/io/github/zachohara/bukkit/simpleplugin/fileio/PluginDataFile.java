/* Copyright (C) 2017 Zach Ohara
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

package io.github.zachohara.bukkit.simpleplugin.fileio;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import io.github.zachohara.bukkit.simpleplugin.plugin.SimplePlugin;

public abstract class PluginDataFile {
	
	/**
	 * The {@code File} representing the file path.
	 */
	private File filepath;
	
	/**
	 * Constructs a new {@code PluginDataFile} with the given filename
	 * and that will belong to the given plugin.
	 *
	 * @param owner the plugin that owns the file.
	 * @param filename the name of the file.
	 */
	public PluginDataFile(SimplePlugin owner, String filename) {
		owner.registerPluginFile(this);
		this.filepath = new File(owner.getDataFolder(), filename);
		this.createFile(owner.getLogger());
		loadFile(owner.getLogger());
	}
	
	protected File getFile() {
		return this.filepath;
	}

	/**
	 * Creates a new file if and only if the required file does not exist.
	 *
	 * @param outputLog the log to output results to.
	 */
	private void createFile(Logger outputLog) {
		this.filepath.getParentFile().mkdirs();
		try {
			if (this.filepath.createNewFile()) {
				outputLog.info("New file sucessfully created: " + this.filepath);
			}
		} catch (IOException e) {
			outputLog.warning("Error creating new file: " + this.filepath);
			e.printStackTrace();
		}
	}
	
	/**
	 * Load the file, and log the results.
	 *
	 * @param outputLog the log to output results to.
	 */
	private final void loadFile(Logger outputLog) {
		try {
			this.attemptLoad();
			outputLog.info("File sucessfully loaded: " + this.filepath);
		} catch (IOException e) {
			outputLog.warning("File data could not be read: " + this.filepath);
			outputLog.warning("Was the plugin just updated? Or the file just created?");
		}
	}
	
	/**
	 * Safely close the file in order to shut down the server.
	 * 
	 * @param outputLog the log to output results to.
	 */
	public final void closeFile(Logger outputLog) {
		try {
			this.attemptClose();
			outputLog.info("File successfully saved and closed: " + this.filepath);
		} catch (IOException e) {
			outputLog.warning("Error saving file: " + this.filepath);
			outputLog.warning("Details below:");
			e.printStackTrace();
		}
	}
	
	/**
	 * Attempt to load the file. Subclass implementations of this method
	 * should always call {@code super.attemptLoad()} before their own
	 * procedures.
	 *
	 * @throws IOException
	 */
	protected void attemptLoad() throws IOException {
		// Take no action
		// Should be overridden by subclasses
	}
	
	/**
	 * Attempt to save and close the file. Subclass implementations of this method
	 * should always call {@code super.attemptClose()} after necessary
	 * procedures are finished.
	 *
	 * @throws IOException
	 */
	protected void attemptClose() throws IOException {
		// Take no action
		// Should be overridden by subclasses
	}

	/**
	 * Returns a {@code String} representation of this object. The string is generated
	 * using the abstract name of the output file.
	 *
	 * @return a {@code String} representation of this object.
	 */
	@Override
	public String toString() {
		return this.filepath.getName();
	}
	
}
