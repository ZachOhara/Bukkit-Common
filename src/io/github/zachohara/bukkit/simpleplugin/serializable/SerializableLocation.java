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

package io.github.zachohara.bukkit.simpleplugin.serializable;

import java.io.Serializable;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * A {@code SerializableLocation} is a Bukkit {@code Location} that can be serialized using
 * Java's standard object IO system.
 *
 * @author Zach Ohara
 */
public class SerializableLocation extends Location implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code SerializableLocation} with no yaw or pitch.
	 *
	 * @param world the world for this location
	 * @param x the x-coordinate for this location
	 * @param y the y-coordinate for this location
	 * @param z the z-coordinate for this location
	 * @see Location#Location(World, double, double, double)
	 */
	public SerializableLocation(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	/**
	 * Constructs a new {@code SerializableLocation} with all the given information.
	 *
	 * @param world the world for this location.
	 * @param x the x-coordinate for this location.
	 * @param y the y-coordinate for this location.
	 * @param z the z-coordinate for this location.
	 * @param yaw the yaw for this location.
	 * @param pitch the pitch for this location.
	 * @see Location#Location(World, double, double, double, float, float)
	 */
	public SerializableLocation(World world, double x, double y, double z, float yaw, float pitch) {
		super(world, x, y, z, yaw, pitch);
	}

	/**
	 * Constructs a new {@code SerializableLocation} from another location.
	 *
	 * @param location the {@code Location} to initialize this {@code SerializableLocation} from. 
	 */
	public SerializableLocation(Location location) {
		this(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(),
				location.getPitch());
	}

}
