# Flappy Bird FX

A Flappy Bird clone written using Java and JavaFX! This is a project written for the final project in my Java Class.

## Requirements:
- [Java 11](https://adoptium.net/?variant=openjdk11) (Linux users can run: `sudo apt-get install openjdk-11-jdk && sudo apt install openjfx`)
## Running:
- Download the latest release zip
- Extract the zip to a folder
### Windows:
- Double Click `run.bat`
### Ubuntu (Linux):
- Make sure OpenJFX is installed (`sudo apt install openjfx`)
- Open Terminal to extacted zip location
- Run `sudo chmod +x ./run.sh`
- Run `./run.sh`

## Incorporated Elements:
### Classes:
- Managers: 
	- RenderManager - Used to draw objects onto a pane that gets displayed to a user 
	- SceneManager - Used to switch between scenes
- Scenes:
	- Scene - Base Scene Class
	- Menu - Sets up a scene for the main menu.
	- Game - Sets up a scene for the main game.
	- Leaderboard - Sets up a scene for the leaderboard information.
	- Options - Sets up a scene for the options menu
	- Credits - Sets up a scene for the credits menu
- Sprites
	- Sprite - Base sprite class
	- Player - Sets up a player object that can either be in demo mode or in a controlled mode
	- Pipe - Sets up a pipe object
	- Button - Generic Button that accepts an Action interface to do actions
	- SpriteData - Separate class from Sprite that holds a String and a Integer (Tag and Layer respectively)
	- Transform - Used by each sprite for Position, Rotation, and Scale
- Audio
	- SFXPlayer - Static class which is responsible for playing sound effects
	- MusicPlayer - Static class which is responsible for playing music
	- nus3audio - Class which reads nus3audio files
- Misc.
	- FPS - Calculates the DeltaTime (Used for Physics)
	- Utils - Used for Binary reading and writing
### Interfaces:
- Action
	- onClick - Runs an action when the button is clicked
	- onHover - Runs an action when the button is hovered over (unimplemented since it's unused)
### Files:
- Resources
	- spritesheet.png - Image that has all the necessary sprites on it
	- 04B_19__.TTF - Flappy Bird Font
- Leaderboard
	- leaderboard.flb - Custom Binary File Format that contains the score leaderboard (Extra Credit since we didn't go over reading and writing binary files)
- Options
	- option.fop - Custom Binary File Format that contains the options for the Music and SFX volume (Extra Credit since we didn't go over reading and writing binary files)
### GUI
- RenderManager
		- RenderPane - Main Pane that displays all sprites and nodes
### Exception Handling
- MusicPlayer
		- Tries to load music files. If it hits an exception, it'll check to see if the IS_DEBUG flag in App is enabled. If so, then print the stack trace, else, just print out a simple error message to the user.
- SFXPlayer
		- Same with MusicPlayer, but for SFX instead.
- Etc...
		- Exception Handling is everywhere in this project, but it'd take forever to list them all.
## Credits
[Ali Hussain](https://github.com/Coolsonickirby) [(Coolsonickirby / Random)](https://www.youtube.com/channel/UCUp-3P4BdmQWYCJ7rRyzrbQ/) - Programmer
[Superjustinbros](https://www.spriters-resource.com/submitter/Superjustinbros/) - [Spritesheet](https://www.spriters-resource.com/mobile/flappybird/sheet/59894/)
[StackOverFlow](https://stackoverflow.com/) - Answering Programming Questions
[Majora](https://www.youtube.com/watch?v=dQw4w9WgXcQ) & [Catalyst](https://www.youtube.com/channel/UCJKEJbn6HJ3c8hy8WO5zKsg) - Beta Testing
Kimberly Moscardelli - For letting me work on these fun projects and for being a cool professor!