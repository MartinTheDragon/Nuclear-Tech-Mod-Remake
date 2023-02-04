# Nuclear Tech Mod
**This is a full rewrite of the Nuclear Tech Mod [originally created by HbmMods](https://github.com/HbmMods/Hbm-s-Nuclear-Tech-GIT).**
It is currently being developed for the 1.18.2 Minecraft version.
Everything is still subject to change, even the speed at which blocks get mined. This mod is not ready for general use yet and everything is work in progress.

### Development builds

But you can still test the current state of the mod by either building the mod yourself, or downloading one of the [occasional releases published on GitHub](https://github.com/MartinTheDragon/Nuclear-Tech-Mod-Remake/releases). Finding and reporting bugs is encouraged.

### Discord

[Join the Discord server](https://discord.gg/XDrARD2FaJ) dedicated to the 1.18 port. It is not affiliated with the official NTM Discord server.

### Credits

For a (non-exhaustive) list of who worked on this project and the original, look at the [credits file](CREDITS.md).

### Contributing

Want to report a bug? Translate the mod? Make code contributions? We are interested! The [CONTRIBUTING.md](CONTRIBUTING.md) file contains the information for you.

### License

This project is licensed under the GNU Lesser General Public License v3. You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>

Make sure usages and modifications comply with the license, and with the [Minecraft EULA](https://account.mojang.com/documents/minecraft_eula).

### Privacy

The mod won't spy on you or collect any personal data. That would be unethical (yes I'm looking at you, Microsoft).
However, it uses the Forge version checker, which fetches a JSON file with update information from <https://nucleartech.martinthedragon.at/raw/update.json>.
Any requests made to this domain and its subdomains will increment a country-specific counter, depending on the IP-address.
The IP-address isn't logged for me to see (which doesn't legally count as personally-identifying information anyway), and only the counter is kept for (very) rough statistics on how many people play the mod each day.
It is just a side effect of providing an update-checker service.
If you wish to not use that service for the sake of your privacy, you are free to modify the mod as per license by removing the line beginning with `updateJSONURL` in the [mods.toml](src/main/resources/META-INF/mods.toml) file and compiling your own version (Forge also uses that version checker system by the way).
