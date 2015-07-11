# The Bukkit Common Library

### What is it?

I started this project because I was trying to maintain a few different Bukkit plugins, and noticed that they used some very similar code. I moved all of the reusable code into it's own project, and that project became the Bukkit Common Library. The Library handles almost everything needed for adding command-line functionality to a Bukkit plugin. The current version is confirmed to work with Bukkit version 1.7.9-R2, and will *maybe* work on later versions without modification. At the time of writing this, there is no current official version of Bukkit, because it got taken down by a DMCA request. Historically, the most recent version of Bukkit can be downloaded at [dl.bukkit.org](http://dl.bukkit.org).

Along with all of the sorce code, in the root folder of this repository, you'll also find [detailed documentation](javadoc) for all of the code, along with a compiled .jar version of the project.

I may or may not support this software in the future, but feel free to send a pull request if you think you have a way to improve it. There is no warranty on this software, and I am absolutely not going to do full-time tech support for it, but I will try to be as helpful as I can if you're having problems. Send me an email, or create a new issue.

This entire repository is made available under the GNU General Public License v3.0. A full copy of this license is available as the [LICENSE](LICENSE) file in this repository, or at [gnu.org/licenses](http://www.gnu.org/licenses/).

## Quick-Start Guide

### Installing the library on a server

This entire library works as a Bukkit plugin. Simply download the "Bukkit Common v___.jar" file from the root folder of this repository, and drop it into the 'plugins' folder in your server.

### Using the library for your plugin

Download the "Bukkit Common v___.jar" file from the root folder of this repository, and add it to the build path of your project. This should be obvious. In the 'plugin.yml' file for your plugin, tell Bukkit that your plugin relies on this one by including the line "depend: [BukkitCommon]" in the file. The main class for your plugin, as defined in the 'plugin.yml', must be a subclass of `CommonPlugin`, which is defined in this library. 

Your plugin must include two enumerations: one for command rules, and another for command executables. These enumerations should implement `CommandRules` and `CommandExecutables` respectively. Refer to the documentation for these two interfaces, as well as to the `CommandRulesEntry` and `Implementation` classes, to get started writing your commands.

Any plugin that adds functionality beyond basic command line interfaces needs to initialize that functionality by overriding the `onEnable` method of the main class. In the first line of the method, be sure to call `super.onEnable()` as well.
