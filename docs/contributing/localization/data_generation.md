## Translating: Utilizing data generators

Data generators can be used to generate language more efficiently than just by manual editing. Although they have a bunch of benefits, like...

 - Errors are less likely, because of static analysis and no direct use of translation keys
 - Checks for missing or obsolete translation strings, for example after an update, making it easier to catch up
 - Repetitive naming like ingots or ores can be automated, and those that don't work, can be overridden

..., they require some advanced knowledge to be used. This guide aims at making things easier to set up. Knowing [Kotlin](https://kotlinlang.org/) isn't that much of a necessity, but can still help you a lot. It's also a rather easy language to learn, so if you're interested, check out the [learning material provided on the Kotlin website](https://kotlinlang.org/docs/getting-started.html).

Nevertheless, it'll be easier for you to work with an IDE. See the guide on [setting up IntelliJ with the project](../ide_setup.md), if you haven't already.
The rest of this guide assumes you're using IntelliJ.

### Where are the data generators?

You can find them under [src/datagen/main/kotlin/at/martinthedragon/nucleartech/datagen](../../../src/datagen/main/kotlin/at/martinthedragon/nucleartech/datagen), either by clicking on that link, navigating to the directory in the project view, or searching for the file `NuclearLanguageProviders` (in IntelliJ, hit Shift two times in quick succession to open the search function).

The language-specific generators are in the `localisation` package.
If one for your language already exists, you can [edit that one](#adding-and-editing-translations). Otherwise, you'll have to add a new one.

### Adding a new data generator for your language

First of all, you need your language's country code, which you can [find on the Minecraft Wiki](https://minecraft.fandom.com/wiki/Language#Languages). It should look similar to this one: `en_us`.

You'll be adding that country code to the `NuclearLanguageProviders` file. Simply append a constant value near the end of the file, just below and similar to the other ones in there.

Next, you have to create a language-specific data generator under the `localisation` package. Right-click on that package, hover over 'New...', then click 'Kotlin Class/File' and just create a new file for now. The file name should be similar to this one: `EnUsLanguageProvider`.

Replace everything in that file with the following template and mind the placeholders:

```kotlin
package at.martinthedragon.nucleartech.datagen.localisation

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.Materials
import at.martinthedragon.nucleartech.SoundEvents
import at.martinthedragon.nucleartech.block.NTechBlocks
import at.martinthedragon.nucleartech.datagen.NuclearLanguageProviders
import at.martinthedragon.nucleartech.entity.EntityTypes
import at.martinthedragon.nucleartech.entity.attribute.Attributes
import at.martinthedragon.nucleartech.fluid.NTechFluids
import at.martinthedragon.nucleartech.item.NTechItems
import at.martinthedragon.nucleartech.menu.MenuTypes
import at.martinthedragon.nucleartech.world.DamageSources
import net.minecraft.data.DataGenerator

class `FILE NAME WITHOUT DIACRITICS`(dataGenerator: DataGenerator) : NuclearLanguageProvider(dataGenerator, NuclearLanguageProviders.`YOUR LANGUAGE CODE IN CAPITAL LETTERS`, false) {
  override fun translate() {
      addMaterials()
      // TRANSLATIONS GO HERE
  }

  private fun addMaterials() {
      // MATERIAL NAMES GO HERE
  }

  // REPLACE THESE FORMATTING STRINGS (INSIDE DOUBLE QUOTES) WITH YOUR ACTUAL TRANSLATIONS

  override val spawnEggSuffix = " Spawn Egg"

  override val oreFormat = "%s Ore"
  override val deepOreFormat = "Deepslate %s Ore"
  override val blockFormat = "Block of %s"
  override val rawFormat = "Raw %s"
  override val ingotFormat = "%s Ingot"
  override val nuggetFormat = "%s Nugget"
  override val crystalsFormat = "%s Crystals"
  override val powderFormat = "%s Powder"
  override val plateFormat = "%s Plate"
  override val wireFormat = "%s Wire"
}
```

Now you can add that new language provider to the list of language providers in `NuclearLanguageProviders` by appending your newly created file like the other existing providers.

Assuming you following everything correctly: Congratulations, you successfully added a new language provider for your language.

You can now move on to adding and editing translations for the mod.

### Adding and editing translations

Generally, you can source all lines of code from the English language provider, `EnUsLanguageProvider`. For that, I recommend [splitting the view](https://www.jetbrains.com/help/idea/using-code-editor.html#split_screen) between the English one and the one for your language.
Now just copy missing lines over to your language provider in the correct function scopes. I recommend starting with the materials, since they, together with the formatting strings near the end of the file, are used to automatically generate a big portion of the strings for things like ores, ingots, nuggets and plates.

#### Knowing which strings are missing

The data generators will automatically check and tell you which ones are still missing. All you need to do is execute them, which you can do by running `runData`, if you already [set up run configurations](../ide_setup.md#generating-run-configurations).
When execution finished, you can see the output of what's missing for every language provider at the end of the log in either the 'Run' or 'Debug' tool window, depending on how you executed the run configuration.

#### Overriding or adding new translation code

Your language may have different exceptions for auto-generated strings, since auto-generation doesn't always work correctly 100%. In the English language provider, if a string can be auto-generated, it is omitted from the code in the `translate` function.
However, you can override these auto-generated strings by adding lines of code to the `translate` function that add the correct translation explicitly.
You just add a line like the surrounding ones.

Example:

```kotlin
// Otherwise, it would automatically generate the string "Lanthanum Ingot"
addItem(NTechItems.lanthanumIngot, "Semi-Stable Lanthanum Ingot")
```

You can find the blocks or items you need to translate in `NTechBlocks` and `NTechItems` respectively (navigate to them by doing Ctrl+LMB on their name in the language provider code).

### Testing translations

With [run configurations set up](../ide_setup.md#generating-run-configurations), firstly run `runData` to generate the language file, then run `runClient` to launch a Minecraft instance with your changes.

When you're done with translating and made sure that everything is correct, you can move on to [committing your changes and creating a pull request](../pull_requests.md).
