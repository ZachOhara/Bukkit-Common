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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import io.github.zachohara.bukkit.simpleplugin.plugin.SimplePlugin;

/**
 * A {@code PersistentList} is responsible for loading and storing any given {@code List}
 * as an external file, so that it remains persistent through server restarts.
 *
 * @param <E> the element type of the list
 * @see java.util.List
 * @author Zach Ohara
 */
public class PersistentList<E extends Serializable> extends PersistentObject implements List<E> {

	/**
	 * Constructs a new {@code PersistentList} with the given plugin as an owner, the data
	 * to store, and the filename to store that data to.
	 *
	 * @param <L> any type that is a {@code List<E>} and is {@code Serializable}.
	 * @param owner the plugin that created this object.
	 * @param data the serializable {@code List} that should be stored in an external file.
	 * @param filename the filename to store the object as.
	 */
	public <L extends List<E> & Serializable> PersistentList(SimplePlugin owner, String filename, L data) {
		super(owner, filename, data);
	}

	/**
	 * Constructs a new {@code PersistentList} with the given plugin as an owner, and the
	 * filename to store that data to. This constructor will create a new {@code ArrayList}
	 * object that will contain the data.
	 *
	 * @param owner the plugin that created this object.
	 * @param filename the filename to store the object as.
	 */
	public PersistentList(SimplePlugin owner, String filename) {
		super(owner, filename, new ArrayList<E>());
	}

	/**
	 * Gets the {@code List} that is stored by this {@code PersistentList}.
	 *
	 * @return a reference to the underlying {@code List}.
	 */
	@SuppressWarnings("unchecked")
	private List<E> listdata() {
		if (this.getPayload() instanceof List<?>) {
			return (List<E>) this.getPayload();
		} else {
			return null;
		}
	}

	// @formatter:off

	/*
	 * +-------------------------------------------------------------------------------------+
	 * | All methods below this point are delegate methods extending this.listdata() to this |
	 * +-------------------------------------------------------------------------------------+
	 */

	// @formatter:on

	@Override
	public boolean add(E arg0) {
		return this.listdata().add(arg0);
	}

	@Override
	public void add(int arg0, E arg1) {
		this.listdata().add(arg0, arg1);

	}

	@Override
	public boolean addAll(Collection<? extends E> arg0) {
		return this.listdata().addAll(arg0);
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends E> arg1) {
		return this.listdata().addAll(arg0, arg1);
	}

	@Override
	public void clear() {
		this.listdata().clear();
	}

	@Override
	public boolean contains(Object arg0) {
		return this.listdata().contains(arg0);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		return this.listdata().containsAll(arg0);
	}

	@Override
	public E get(int arg0) {
		return this.listdata().get(arg0);
	}

	@Override
	public int indexOf(Object arg0) {
		return this.listdata().indexOf(arg0);
	}

	@Override
	public boolean isEmpty() {
		return this.listdata().isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return this.listdata().iterator();
	}

	@Override
	public int lastIndexOf(Object arg0) {
		return this.listdata().lastIndexOf(arg0);
	}

	@Override
	public ListIterator<E> listIterator() {
		return this.listdata().listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int arg0) {
		return this.listdata().listIterator(arg0);
	}

	@Override
	public boolean remove(Object arg0) {
		return this.listdata().remove(arg0);
	}

	@Override
	public E remove(int arg0) {
		return this.listdata().remove(arg0);
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		return this.listdata().removeAll(arg0);
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		return this.listdata().retainAll(arg0);
	}

	@Override
	public E set(int arg0, E arg1) {
		return this.listdata().set(arg0, arg1);
	}

	@Override
	public int size() {
		return this.listdata().size();
	}

	@Override
	public List<E> subList(int arg0, int arg1) {
		return this.listdata().subList(arg0, arg1);
	}

	@Override
	public Object[] toArray() {
		return this.listdata().toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		return this.listdata().toArray(arg0);
	}

}
