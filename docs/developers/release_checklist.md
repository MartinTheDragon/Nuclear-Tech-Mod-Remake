## Release Checklist

### What is this?

This little document is to make sure Martin doesn't keep on forgetting some things whenever he releases a new version of the mod...

### Before releasing

- Update the bug report issue template to contain the new version (should be in the version changing commit)
- Update the completion rates in [the list of supported languages](../contributing/localization/LOCALIZATION.md#currently-supported-languages)

### When releasing

- Write a changelog divided into categories in the following order:

  1. Gameplay
  2. Performance
  3. Graphics
  4. UI/UX
  5. Audio
  6. Localization
  7. Customization
  8. Cosmetics
  9. Code Quality
  10. Compatibility
  11. API
  12. Binaries
  13. Misc

- Add MD5 and SHA1 checksums

### After releasing

- Update the update.json file on the gh-pages branch
- Make an announcement on the Discord server
