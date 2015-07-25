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

package io.github.zachohara.bukkit.common.persistence;

import io.github.zachohara.bukkit.common.plugin.CommonPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * A {@code PersistentObject} is responsible for loading and storing any given
 * {@code Serializable} object as an external file, so that it remains persistent
 * through server restarts.
 * 
 * @author Zach Ohara
 */
public class PersistentObject {
	
	/**
	 * The {@code File} location that the file should be stored in.
	 */
	private File dataFile;
	
	/**
	 * The {@code Serializable} object that will be stored. After registering the object
	 * here, the object can still be modified by other classes.
	 */
	private Serializable data;
	
	/**
	 * Constructs a new {@code PersistentObject} with the given plugin as an owner, the
	 * data to store, and the filename to store that data to.
	 * 
	 * @param owner the plugin that created this object.
	 * @param data the object that should be stored in an external file.
	 * @param filename the filename to store the object as.
	 */
	public PersistentObject(CommonPlugin owner, Serializable data, String filename) {
		this.dataFile = new File(owner.getDataFolder(), filename);
		this.data = data;
		this.createDataFile(owner);
		this.attemptLoadFile(owner);
	}
	
	/**
	 * Returns a reference to the {@code Serializable} object that is stored by this
	 * {@code PersistentObject}.
	 * 
	 * @return a reference to the stored data.
	 */
	public Serializable getObject() {
		return this.data;
	}
	
	/**
	 * Loads previously-stored persistent data from an external file, so that it can be
	 * reconstructed into a usable object form.
	 * 
	 * @throws IOException if the file read operation fails.
	 * @throws ClassNotFoundException if the read is succesful, but the content is not
	 * what was expected (this should be impossible).
	 */
	public void loadFromFile() throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(this.dataFile));
		Object loadedData = in.readObject();
		in.close();
		if (loadedData instanceof Serializable)
			this.data = (Serializable) loadedData;
		else
			throw new IOException("Object found was not of the correct type");
	}
	
	/**
	 * Saves all the stored persistent data into an external file, so that it can be
	 * reconstructed later even if the server is shut down.
	 * 
	 * @throws IOException if the write operation fails.
	 */
	public void saveToFile() throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.dataFile));
		out.writeObject(this.data);
		out.close();
	}
	
	/**
	 * Creates a new file for persistent data, if and only if there is not already stored
	 * persistent data under the same filename.
	 * 
	 * @param owner the {@code JavaPlugin} that should be informed of errors in this
	 * operation.
	 */
	private void createDataFile(JavaPlugin owner) {
		try {
			this.dataFile.getParentFile().mkdirs();
			this.dataFile.createNewFile();
		} catch (IOException e) {
			owner.getLogger().warning("Error creating new offline persistent data file:");
			e.printStackTrace();
		}
	}
	
	/**
	 * Attempts to load currently-existant persistent data from a file.
	 * 
	 * @param owner the {@code JavaPlugin} that should be informed of errors in this
	 * operation.
	 */
	private void attemptLoadFile(JavaPlugin owner) {
		try {
			this.loadFromFile();
		} catch (ClassNotFoundException | IOException e) {
			owner.getLogger().warning("Offline persistent data could not be read!");
			owner.getLogger().warning("Is this the first time the plugin is being used?");
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns a {@code String} representation of this object. The string is generated
	 * using the abstract name of the output file.
	 * 
	 * @return a {@code String} representation of this object.
	 */
	public String toString() {
		return this.dataFile.getName();
	}
	
}
