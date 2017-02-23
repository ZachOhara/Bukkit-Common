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

package io.github.zachohara.bukkit.simpleplugin.fileio.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import io.github.zachohara.bukkit.simpleplugin.fileio.PluginDataFile;
import io.github.zachohara.bukkit.simpleplugin.plugin.SimplePlugin;

/**
 * A {@code PersistentObject} is responsible for loading and storing any given
 * {@code Serializable} object as an external file, so that it remains persistent through
 * server restarts.
 *
 * @author Zach Ohara
 */
public class PersistentObject extends PluginDataFile {

	/**
	 * The {@code Serializable} object that will be stored. After registering the object
	 * here, the object can still be modified by other classes.
	 */
	private Serializable payload;

	/**
	 * Constructs a new {@code PersistentObject} with the given plugin as an owner, the
	 * payload to store, and the filename to store that data to.
	 *
	 * @param owner the plugin that created this object.
	 * @param payload the object that should be stored in an external file.
	 * @param filename the filename to store the payload as.
	 */
	public PersistentObject(SimplePlugin owner, String filename, Serializable payload) {
		super(owner, filename);
		if (this.payload == null) {
			this.payload = payload;
		}
	}
	
	/**
	 * Constructs a new {@code PersistentObject} with the given plugin as an owner, and
	 * the filename to load existing data from.
	 *
	 * @param owner the plugin that created this object.
	 * @param filename the filename to load / store the payload as.
	 */
	public PersistentObject(SimplePlugin owner, String filename) {
		this(owner, filename, null);
	}

	/**
	 * Returns a reference to the {@code Serializable} object that is stored by this
	 * {@code PersistentObject}.
	 *
	 * @return a reference to the stored data.
	 */
	public Serializable getPayload() {
		return this.payload;
	}

	@Override
	protected void attemptLoad() throws IOException {
		super.attemptLoad();
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(this.getFile()));
		Object loadedData;
		try {
			loadedData = in.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException(e);
		} finally {
			in.close();
		}
		if (loadedData instanceof Serializable) {
			this.payload = (Serializable) loadedData;
		} else {
			throw new IOException("Object found was not of the correct type");
		}
	}

	@Override
	protected void attemptClose() throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.getFile()));
		out.writeObject(this.payload);
		out.close();
		super.attemptClose();
	}

}
