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

package io.github.zachohara.bukkit.simpleplugin.persistence;

import java.io.Serializable;
import java.util.Collection;

import io.github.zachohara.bukkit.simpleplugin.plugin.SimplePlugin;

/**
 * A {@code PersistentKeyedMap} object is responsible for loading and storing any given
 * information that occurs in pairs as an external file, so that it remains persistent
 * through server restarts. At its core, a {@code PersistentKeyedMap} object is essentially
 * a {@code PersistentMap} with the map key being a String that is generated for any given
 * key.
 *
 * @param <K> the key type for this map.
 * @param <D> the data type for this map. A data object must be able to be generated for
 * any key object.
 * @author Zach Ohara
 */
public abstract class PersistentKeyedMap<K, D extends Serializable> extends PersistentMap<String, D> {

	/**
	 * Constructs a new {@code PersistentPlayerData} with the given plugin as an owner, and
	 * the filename to store that data to. This constructor will create a new
	 * {@code HashMap} object that will be contain the data.
	 *
	 * @param owner the plugin that created this object.
	 * @param filename the filename to store the object as.
	 */
	public PersistentKeyedMap(SimplePlugin owner, String filename) {
		super(owner, filename);
	}

	/**
	 * Returns the data corresponding to the map key that is generated for the given key
	 * object.
	 *
	 * @param key the key object to generate a map key for, and then to query the map for.
	 * @return the data associated with the given key, or {@code null} if no data exists
	 * for the given key.
	 */
	public D getKeyData(K key) {
		return this.getKeyData(this.generateMapKey(key));
	}

	/**
	 * Returns the data corresponding to the given map key.
	 *
	 * @param key the map key to query the map for.
	 * @return the data associated with the given key, or {@code null} if no data exists
	 * for the given key.
	 */
	public D getKeyData(String key) {
		return this.get(key);
	}

	/**
	 * Saves the relevant value for the given key to this map.
	 *
	 * @param key the key to save information for.
	 * @see #calculateDataValue(Object)
	 */
	public void saveKeyedData(K key) {
		String mapkey = this.generateMapKey(key);
		if (this.keyDataExists(key)) {
			this.remove(mapkey);
		}
		this.put(mapkey, this.calculateDataValue(key));
	}

	/**
	 * Saves the relevant values for all the keys given in the list.
	 *
	 * @param collection the list of keys to save data for.
	 */
	public void saveAllKeyedData(Collection<? extends K> collection) {
		for (K key : collection) {
			this.saveKeyedData(key);
		}
	}

	/**
	 * Saves the relevant values for all the keys given in the array.
	 *
	 * @param keylist the array of keys to save data for.
	 */
	public void saveAllKeyedData(K[] keylist) {
		for (K key : keylist) {
			this.saveKeyedData(key);
		}
	}

	/**
	 * Determines if a value for the given key exists in this map.
	 *
	 * @param key the data to query for.
	 * @return {@code true} if a value currently exists in the map for the given key;
	 * {@code false} otherwise.
	 */
	public boolean keyDataExists(K key) {
		String mapkey = this.generateMapKey(key);
		return this.keyDataExists(mapkey);
	}

	/**
	 * Determines if a value for the given map key exists in this map.
	 *
	 * @param key the map key to query for.
	 * @return {@code true} if a value currently exists in the map for the given key;
	 * {@code false} otherwise.
	 */
	public boolean keyDataExists(String key) {
		return this.get(key) != null;
	}

	/**
	 * Calculates and returns the relavant data value for the given key. Overriding this
	 * method allows sub-classes to have complete control over what data is stored for the
	 * specified key.
	 *
	 * @param key the key to calculate a data value for.
	 * @return the information for this map that is specific to the given key.
	 */
	public abstract D calculateDataValue(K key);

	/**
	 * Generate a key {@code String} that is specific to the given key object. This key
	 * will be used to store information in the map for the given object. Overriding this
	 * method allows sub-classes to have complete control over how the data is stored in
	 * the map, but the default implementation means that sub-classes are not required to
	 * generate their own map keys. By default, this method will simply use the
	 * {@code toString()} method of the key type.
	 *
	 * @param key the {@code K} key object to generate a map key for.
	 * @return the generated map key for the given data.
	 */
	public String generateMapKey(K key) {
		return key.toString();
	}

}
