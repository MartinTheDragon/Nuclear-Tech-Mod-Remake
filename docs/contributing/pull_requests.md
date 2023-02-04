## Creating a pull request

After you've [verified that everything is working](testing.md) the way you intended, you can finally present your changes and request them to be merged into the official releases of the mod by creating a pull request.

### Prerequisites

Besides having a GitHub account, this document expects you to use the [Git command line interface](https://git-scm.com/), which you'll have to install if it isn't already, and set up when running for the first time. While GUI clients do exist, the following instructions contain commands for the CLI.
But sometimes, you may have to use some other commands for specific cases. That's why learning and understanding Git is important, and it's not hard either. But for now, try following these steps as exactly as possible.
All the commands written here assume that you have a terminal or command line open in the root directory of the project. If you don't know how to do that, try looking it up online.

If you don't want to use a CLI, you can use [IntelliJ's builtin Git support](https://www.jetbrains.com/help/idea/commit-and-push-changes.html) instead. It works really well and is pretty straightforward.

### Committing and saving your changes

#### What are commits?

Committing is the process of permanently saving your changes as a sort of "difference" to the previous version. With Git, you then have the ability to move around in history and view changes that happened in each commit. This also means that any changes you committed aren't easy to get rid of, so make sure you don't accidentally commit any sensitive information like passwords!

#### What qualifies as a commit?

Commits should only contain changes to one specific feature or operation.

These are good commits:

- Add [MACHINE]
- Add [LANGUAGE] translation
- Update ...
- Fix ...

These are bad commits:

- ow
- my head hurts
- too many changes
- too many changes 2

Making sure your commits contain separate changes helps when trying to find the introduction of features or even bugs later on.

#### How to write commit messages?

It's equally important to write good commit messages. Those are the bits of text that show up as the title of your changes, i.e. it's the commit's name. Being able to easily and quickly understand what a commit does is very important.
Every project or repository has its own guidelines for what these messages should look like.

Commit messages to this project should start with a capitalized verb (or an adverb to a verb) in infinitive.
Good words are: Add, Fix, Change, Remove, Update, etc.
But they can also be more specific like "Use ...", "Set version to ...", etc.
Generally, you should be able to insert the commit message into this sentence: "This commit will: ..."
As an example: "This commit will: Add pineapples"

If you are still unsure, look at the existing commit history.

Commit titles **shouldn't be longer than 50 characters**. This ensures that not only commits need to be small, you also have to deeply think about what you did and formulate it quickly.
For any further information, you can write additional descriptions, which should wrap to a new line after 72 characters, if you know how to do that.

#### How to actually commit now?

Finally, to commit your changes to the version history, type the following commands (don't forget about the placeholders):

```bash
git add *
git commit -m "COMMIT MESSAGE"
```

If you need to revert anything or need some other help, try searching online, or worst case, contact me.

### Creating a fork

In order for your changes to be merged, you need to upload them to GitHub. But you don't have permission to push commits into the official repository, which is why you'll have to [create a fork](https://github.com/MartinTheDragon/Nuclear-Tech-Mod-Remake/fork).

### Setting remote origin to your fork

Git needs to know where it can upload your changes, which you can set through the following command (mind the placeholders):

```bash
git remote set-url origin https://github.com/YOUR-USERNAME/Nuclear-Tech-Mod-Remake.git
```

### Uploading to your fork

Now all that's left to do before creating a pull request is uploading (pushing) your commits to the fork.
For that, run this command:

```bash
git push
```

If everything worked this far, great! You successfully uploaded your changes to GitHub, where you can create a pull request.

### Opening a pull request

GitHub already has [pretty good documentation](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/creating-a-pull-request-from-a-fork) on how to do that, so follow that.
Now, just wait for your changes to be reviewed, and please be ready to accept proposed changes to your commits and actively following your conversation until your pull request gets accepted. If you made any mistakes, we'll handle them together from there.
To make that process easier, please enable the "Allow edits from maintainers" option when creating a pull request.

Thanks for contributing!
