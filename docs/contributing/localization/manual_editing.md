## Translating: Manually writing JSON files

Contributions with manually-written JSON files are accepted for the sake of simplicity and ease-of-use. If you feel comfortable enough with working with code editors and have some knowledge in programming, maybe you could [try out data generation](data_generation.md) instead. Otherwise, you're at the right place.

First of all, [check if your language is already supported](LOCALIZATION.md#currently-supported-languages). If it is, you may want to [edit mistakes or add new strings](#editing-an-existing-language-file). Otherwise, [add a new language file](#adding-a-new-language-file).
Before proceeding though, you'll have [download the repository as a ZIP archive from GitHub](https://github.com/MartinTheDragon/Nuclear-Tech-Mod-Remake/archive/refs/heads/1.18.zip) and extract it (or get the source code in any other way you're familiar with).

### Adding a new language file

In case your language isn't supported yet, you'll have to create a new language file under [src/main/resources/assets/nucleartech/lang/](../../../src/main/resources/assets/nucleartech/lang) with your country code.
The Minecraft Wiki has [a list of languages and such codes](https://minecraft.fandom.com/wiki/Language#Languages) (use values from the column with in-game locale codes).
For example: `en_us.json`

### Editing an existing language file

If you found an existing language file in the directory from the step above, you may just edit it and continue to the next step.
If not, you will probably find a language file under [src/datagen/generated/resources/assets/nucleartech/lang/](../../../src/datagen/generated/resources/assets/nucleartech/lang), which you may edit for now, but further edits will later be required to move your changes into the data generator code when you create a pull request.
Also note that data generator outputs are escaped with unicode sequences for special characters, which make them hard to read.

### Writing translations

This works just like with resource packs, [as described in the Minecraft Wiki](https://minecraft.fandom.com/wiki/Resource_pack#Language).
When you're done (you don't necessarily need to translate everything at once), check if you did everything correctly by [running Minecraft with your changes](../testing.md), then [move on to creating a pull request](../pull_requests.md).
