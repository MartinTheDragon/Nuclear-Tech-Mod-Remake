# Migration

This project is migrating to [Codeberg.org](https://codeberg.org/MartinTheDragon/Nuclear-Tech-Mod-Remake) and will eventually be archived on GitHub.
[Releases](https://codeberg.org/MartinTheDragon/Nuclear-Tech-Mod-Remake/releases) will also be on Codeberg.
For the time being, you may still report issues here if you really prefer, but they will be tracked on Codeberg instead.

## Why?

GitHub always came with a bit of a bitter taste after being acquired by Microsoft, a company historically the worst opponent of open source software (see <https://en.wikipedia.org/wiki/Halloween_documents>).
While Microsoft has made great investments into open source and portrayed themselves as "lovers of open source" over the past two decades, they likely have ulterior motives, as they have recently shown with
their AI invention "GitHub Copilot", which fed off of the work of several thousands of people without any license or notice, generating millions of DMCA violations in the process and fighting them off
in court "for you" (<https://blogs.microsoft.com/on-the-issues/2023/09/07/copilot-copyright-commitment-ai-legal-concerns/>). This devestated the free software landscape, since you're now gonna have an army of
lawyers after you as soon as you even dare to protect your copyright. Lucky for them, with their license helper site <https://choosealicense.com/>, they managed to convince many open source projects to be
licensed under the MIT license, which they describe as "simple and permissive". "Simple and permissive" sounds kinda catchy when you don't really care about licensing, don't you think? Well, now you've given
everyone (including companies) the right to (ab)use your software in any way they want, even distribute it closed source, as long as they just mention you once somewhere, hidden away. There is also no protection
against [patent infringement](https://docs.codeberg.org/getting-started/licensing/#patent-usage). This really just misses [the point of free software](https://www.gnu.org/philosophy/).

If all this license stuff and free (libre) software is a bit hard to understand, Codeberg has actually written [great documentation on how licensing software works](https://docs.codeberg.org/getting-started/licensing).

But besides ideological reasons, GitHub Free comes with less features than Codeberg, and a paid plan doesn't really have anything I'd want. Codeberg itself is also entirely free software, meaning you could even
set up an instance yourself if you wanted or contribute to the site. Also comes without tracking.

# Nuclear Tech Mod
**This is a full rewrite of the Nuclear Tech Mod [originally created by HbmMods](https://github.com/HbmMods/Hbm-s-Nuclear-Tech-GIT).**
It is currently being developed for the 1.18.2 Minecraft version.
Everything is still subject to change, even the speed at which blocks get mined. This mod is not ready for general use yet and everything is work in progress.

### Development Builds

But you can still test the current state of the mod by either [building the mod yourself](#compilation-instructions), or downloading one of the [occasional releases published on GitHub](https://github.com/MartinTheDragon/Nuclear-Tech-Mod-Remake/releases). Finding and reporting bugs is encouraged.

### Discord

[Join the Discord server](https://discord.gg/XDrARD2FaJ) dedicated to the 1.18 port. It is not affiliated with the official NTM Discord server.

### Credits

For a (non-exhaustive) list of who worked on this project and the original, look at the [credits file](CREDITS.md).

### Contributing

Want to report a bug? Translate the mod? Make code contributions? We are interested! The [CONTRIBUTING.md](CONTRIBUTING.md) file contains the information for you.

### Compilation Instructions

If you want to be on the razor's edge of things instead of just cutting edge, you can also compile your own versions of the mod.

1. Fetch the source code

   For people who don't have a Git client:

   1. [Download the source code](https://github.com/MartinTheDragon/Nuclear-Tech-Mod-Remake/archive/refs/heads/1.18.zip)
   2. Extract the ZIP-archive

2. Open a terminal interface or [command line](https://www.freecodecamp.org/news/command-line-commands-cli-tutorial/) inside the root directory of the project
3. Make sure you have JDK 17 (not JRE) installed and the `JAVA_HOME` and `PATH` [environment variables set up](https://www3.ntu.edu.sg/home/ehchua/programming/howto/Environment_Variables.html)

   You can do so by entering this command:

   ```bash
   java -version
   ```

   The output should look something like this:

   ```
   openjdk version "17.0.6" 2023-01-17
   OpenJDK Runtime Environment (build 17.0.6+10)
   OpenJDK 64-Bit Server VM (build 17.0.6+10, mixed mode)
   ```

4. Enter the following command to compile the project:

   ```bash
   # On Windows, you may have to use a \ instead of a /
   ./gradlew build
   ```

5. Wait for compilation to finish (may take up to a few dozen minutes, heavily depending on internet speed)
6. You may now find a freshly-compiled snapshot jar under `build/libs` (only the non-slim and non-API version works directly as a mod).
7. Install the jar like any other mod

### License

This project is licensed under the GNU Lesser General Public License v3. You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>

Make sure usages and modifications comply with the license, and with the [Minecraft EULA](https://account.mojang.com/documents/minecraft_eula).

### Privacy

The mod won't spy on you or collect any personal data. That would be unethical (yes I'm looking at you, Microsoft).
However, it uses the Forge version checker, which fetches a JSON file with update information from <https://www.ntmr.dev/raw/update.json>.
Currently, this file is hosted on <https://codeberg.org>, which imposes no tracking other than storing your IP address for seven days in log files (see their [Privacy Policy](https://codeberg.org/Codeberg/org/src/branch/main/PrivacyPolicy.md)).
If you still wish to not use that service for the sake of your privacy, you are free to modify the mod as per license by removing the line beginning with `updateJSONURL` in the [mods.toml](src/main/resources/META-INF/mods.toml) file and compiling your own version (Forge also uses that version checker system by the way).
