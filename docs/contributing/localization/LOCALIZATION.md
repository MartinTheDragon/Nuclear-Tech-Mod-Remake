## Localiz(s)ation

Are you a native speaker of a foreign language, and a translation for it doesn't yet exist in NTM 1.18?
Feel free to contribute and make the mod more accessible to others!

In case you found any translation errors others made, please do make an issue or a pull request for it, or contact me on [Discord](../../../README.md#discord). Quality is also important.

### Currently supported languages

Languages are supported with data generators by default. Completion rates get updated each release.

- English (US): 100%
- German (author's translation): 100%
- Polish: 100%

#### Languages I'd like to support

Substantial parts of the player-base are from specific countries. Going through the effort of localization for these countries' languages would benefit many players at once.
Of course, I'm happy with any contributed translations, this list is just for orientation and sparking interest.
These are roughly ranked by the amount of players per country (if you are wondering where the statistics are from, check the [privacy section in the README](../../../README.md#privacy)).
Note that these statistics vary from time to time.

1. Chinese
2. Russian
3. French
4. Italian
5. Spanish
6. Portuguese
7. Czech
8. Dutch
9. Romanian
10. Turkish
11. Finnish
12. Slovak
13. Ukrainian
14. Indian
15. Indonesian

### Getting started with translating

There are two possible ways of translating the mod. The first is via data generators, the second is manual editing of the language JSON files.
While harder to get started with a steeper learning curve, data generators are the preferred method due to the following reasons.

 - Errors are less likely, because of static analysis and no direct use of translation keys
 - Checks for missing or obsolete translation strings, for example after an update, making it easier to catch up
 - Repetitive naming like ingots or ores can be automated, and those that don't work, can be overridden

Of course, doing it manually by creating a JSON file is simpler, as it neither involves working with special code editing software, nor requires you to know (at least some) Kotlin and how data generators work.
For that reason, I will also accept translations as manual JSON files, though I'll likely move them into data generators.

So, now you get to choose: [Manual](manual_editing.md) or [generated](data_generation.md)?

### General tips when translating

- Stay as consistent as possible with vanilla naming
- Use internet resources in your language to aid you
- Some words are names or fictional and therefore can only be transcribed (try checking out the German translation if you're unsure)
- `%s` and similar format codes get replaced with whatever information gets inserted into the string. Make sure it makes sense grammatically.
- Repeatedly check in-game if everything looks alright
