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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A {@code PersistentList} is responsible for loading and storing any given {@code List}
 * as an external file, so that it remains persistent through server restarts.
 *
 * @param <E> the element type of the list
 * @see java.util.List
 * @author Zach Ohara
 */
public class PersistentList<E extends Serializable> extends PersistentObject {

	/**
	 * Constructs a new {@code PersistentList} with the given plugin as an owner, the data
	 * to store, and the filename to store that data to.
	 *
	 * @param <L> any type that is a {@code List<E>} and is {@code Serializable}.
	 * @param owner the plugin that created this object.
	 * @param data the serializable {@code List} that should be stored in an external file.
	 * @param filename the filename to store the object as.
	 */
	public <L extends List<E> & Serializable> PersistentList(CommonPlugin owner, L data, String filename) {
		super(owner, data, filename);
	}

	/**
	 * Constructs a new {@code PersistentList} with the given plugin as an owner, and the
	 * filename to store that data to. This constructor will create a new {@code ArrayList}
	 * object that will contain the data.
	 *
	 * @param owner the plugin that created this object.
	 * @param filename the filename to store the object as.
	 */
	public PersistentList(CommonPlugin owner, String filename) {
		super(owner, new ArrayList<E>(), filename);
	}

	/**
	 * Adds an element to the list.
	 *
	 * @param data the element to add.
	 * @see java.util.List#add(Object)
	 */
	public void add(E data) {
		this.listdata().add(data);
	}

	/**
	 * Adds an element to the list, if and only if it is not already in the list.
	 *
	 * @param data the element to add.
	 */
	public void addSafe(E data) {
		//@formatter:off
		if (!this.listdata().contains(data)) {
			this.add(data);
		}
		//@formatter:on
	}

	/**
	 * Adds all the given elements to the list.
	 *
	 * @param data the collection of elements to add.
	 * @see java.util.List#addAll(Collection)
	 */
	public void addAll(Collection<? extends E> data) {
		this.listdata().addAll(data);
	}

	/**
	 * Gets the element at the given index in the list.
	 *
	 * @param index the index to query for.
	 * @return the element at the given index.
	 * @see java.util.List#get(int)
	 */
	public E get(int index) {
		return null;
	}

	/**
	 * Determines if the list already contains the given data.
	 *
	 * @param data the data to query for.
	 * @return {@code true} if and only if the list currently contains the givne data.
	 * @see java.util.List#contains(Object)
	 */
	public boolean contains(E data) {
		return this.listdata().contains(data);
	}

	/**
	 * Returns the current size of the list.
	 *
	 * @return the size of the list.
	 * @see java.util.List#size()
	 */
	public int size() {
		return this.listdata().size();
	}

	/**
	 * Gets the {@code List} that is stored by this {@code PersistentList}.
	 *
	 * @return a reference to the underlying {@code List}.
	 */
	@SuppressWarnings("unchecked")
	public List<E> listdata() {
		if (this.getObject() instanceof List<?>) {
			return (List<E>) this.getObject();
		} else {
			return null;
		}
	}

}
