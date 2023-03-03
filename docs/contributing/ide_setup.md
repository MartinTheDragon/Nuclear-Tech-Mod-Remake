## How to set up the project with an IDE

For advanced modifications, you may need to use an IDE (integrated development environment) instead of just your trusty text editor and the command line.

### Which IDE?

Personally, I have been using [IntelliJ](https://www.jetbrains.com/idea/) for years now, and it has always lived up to my expectations. Therefore, the rest of this guide will assume that you're going to be working with it as well.
If you really want to use something else like [Eclipse](https://www.eclipse.org/downloads/), I can't stop you. Just note though that things may not work out of the box, and that I have no idea what it's like anymore to use Eclipse, so I can't really help you if you have any trouble.

### Installing

Simply download the [IntelliJ](https://www.jetbrains.com/idea/download) installer (Community Edition is more than enough) and go through the installation process to set everything up the way you prefer.

### Downloading and building the project

Assuming you are on IntelliJ's welcome screen, click on 'Get from Version Control' or 'Get from VCS', then with 'Repository URL' mode (default), put <https://github.com/MartinTheDragon/Nuclear-Tech-Mod-Remake> into the URL input field. Make sure 'Git' is selected in the dropdown-menu above and that you selected the directory you want, then hit 'Clone'.

IntelliJ should now work on fetching all the needed dependencies to get you up and running (can take up to a few dozen minutes depending on internet speed).
When that's done, check if the correct JDK is being used by clicking the cog icon in the top right, selecting 'Project Structure...', and seeing if a JDK 17 shows up for 'SDK'. If not, click on the dropdown-menu, hover over 'Add SDK', then select 'Download JDK...', and download a JDK 17.

If anything fails during this or any later steps, please contact me via [Discord](../../README.md#discord) or by starting a discussion on GitHub. Maybe there'll be a troubleshooting page later on for common issues.

### Generating run configurations

Run configurations tell IntelliJ and Gradle what to do when you hit the play button in the top right. But first, you need to generate them.

For IntelliJ, ForgeGradle (the Gradle plugin for developing mods with Forge) has a builtin Gradle task called `genIntellijRuns`. You can run it by opening the Gradle tool window from the right sidebar (the elephant icon), then finding it under `Nuclear Tech Mod` > `Tasks` > `forgegradle runs`, and finally double-clicking `genIntellijRuns`.

When the task execution finished, you should see the options `runClient`, `runData` and `runServer` in the dropdown-menu to the top-right next to the play button. You'll need those for [testing your changes](testing.md).

### Troubleshooting

If problems should arise concerning Gradle while you are working on the project, try looking at the [troubleshooting document](../TROUBLESHOOTING.md) before opening an issue.
