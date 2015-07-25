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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import io.github.zachohara.bukkit.common.plugin.CommonPlugin;

/**
 * A {@code PersistentMap} is responsible for loading and storing any given
 * {@code HashMap} object as an external file, so that it remains persistent
 * through server restarts.
 * 
 * @param <K> the key type of the map
 * @param <V> the value type of the map
 * @see java.util.Map
 * 
 * @author Zach Ohara
 */
public class PersistentMap<K extends Serializable, V extends Serializable>
extends PersistentObject {
	
	/**
	 * Constructs a new {@code PersistentMap} with the given plugin as an owner, the
	 * data to store, and the filename to store that data to.
	 * 
	 * @param owner the plugin that created this object.
	 * @param data the serializable {@code Map} that should be stored in an external file.
	 * @param filename the filename to store the object as.
	 */
	public <T extends Map<K, V> & Serializable> PersistentMap(CommonPlugin owner, T data, String filename) {
		super(owner, data, filename);
	}
	
	/**
	 * Constructs a new {@code PersistentMap} with the given plugin as an owner, and the
	 * filename to store that data to. This constructor will create a new {@code HashMap}
	 * object that will contain the data.
	 * 
	 * @param owner the plugin that created this object.
	 * @param filename the filename to store the object as.
	 */
	public PersistentMap(CommonPlugin owner, String filename) {
		this(owner, new HashMap<K, V>(), filename);
	}
	
	/**
	 * Adds a new (Key, Value) entry pair to this map.
	 * 
	 * @param key the key of this entry.
	 * @param value the value of this entry.
	 * @see java.util.Map#put(Object, Object)
	 */
	public void put(K key, V value) {
		this.mapdata().put(key, value);
	}
	
	/**
	 * Gets the value corresponding to the given key of this map.
	 * 
	 * @param key the key to query for.
	 * @return the value associated with the given key.
	 * @see java.util.Map#get(Object)
	 */
	public V get(K key) {
		return this.mapdata().get(key);
	}
	
	/**
	 * Gets the {@code Map} that is stored by this {@code PersistentMap}.
	 * 
	 * @return a reference to the underlying {@code Map}.
	 */
	@SuppressWarnings("unchecked")
	public Map<K, V> mapdata() {
		if (this.getObject() instanceof Map<?, ?>) {
			return (Map<K, V>) this.getObject();
		} else {
			return null;
		}
	}
	
}
