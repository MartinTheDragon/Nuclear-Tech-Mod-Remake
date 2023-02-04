## Testing your changes

Whatever change you did, it is almost always a good idea to test your changes before [committing and creating a pull request](pull_requests.md).

Depending on what your development environment looks like, you'll have to test in different ways.

### Testing in an IDE

If you haven't set everything up for working with an IDE, see [the guide on it](ide_setup.md).

Then, assuming you're using IntelliJ, you can either generate data (if you performed any changes under [src/datagen/main](../../src/datagen/main)) by launching the `runData` run configuration, or run a test instance of Minecraft together with JEI, Mekanism and maybe other mods installed by launching `runClient`.
Simply select the proper run configuration from the dropdown-menu and hit either the play button or the bug button.

### Testing via command line or terminal

If you don't have an IDE or don't want to use one, you can also run the project from the command line.

The only prerequisite for that is having a working installation of JDK 17, which, on Windows, can sometimes be tedious to set up. You can get an installer from <https://adoptium.net/>. If you have any trouble, look for solutions online.

Then, with a command line open in the project directory, run one of the following commands, depending on what you need as described in the [section above](#testing-in-an-ide).

```bash
# You may need to use \ instead of / on Windows
./gradlew runData
```

```bash
# You may need to use \ instead of / on Windows
./gradlew runClient
```
