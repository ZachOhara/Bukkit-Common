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

package io.github.zachohara.bukkit.simpleplugin.fileio.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.github.zachohara.bukkit.simpleplugin.fileio.PluginDataFile;
import io.github.zachohara.bukkit.simpleplugin.plugin.SimplePlugin;

public class PluginLogFile extends PluginDataFile {
	
	private FileWriter writer;
	
	private static DateFormat timestampFormat = new SimpleDateFormat("[dd/MM/yy HH:mm:ss]");
	
	public PluginLogFile(SimplePlugin owner, String filename) {
		super(owner, filename);
	}
	
	public void logInfo(String message) {
		String line = PluginLogFile.timestampFormat.format(new Date()) + " " + message + "\r\n";
		try {
			this.writer.append(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void attemptLoad() throws IOException {
		super.attemptLoad();
		this.writer = new FileWriter(this.getFile(), true);
	}
	
	@Override
	protected void attemptClose() throws IOException {
		this.writer.close();
		super.attemptClose();
	}
	
}
