# Cache Noisettes Game



## Project Overview

This assignment is to create a **Java Swing** based interactive puzzle game called **Cache Noisettes** using **Java** and **Swing**.

Cache Noisettes is a **single player** French game that roughly translates to *Hide the Nuts*. The game involves placing a number of squirrel pieces on a **4x4 grid**, and sliding them (following specific rules) such that they each drop the nut they are carrying into a hole in the gameboard. The squirrel pieces have a tendency to cover the holes.

## How to Compile and Run Cache Noisette

Compiling and running this program is fairly straight forward. Simply follow the following steps:
1. Download the program files by either cloning or installing the ZIP.
    - If you installed using .ZIP, you will need to extract all files.
2. Open a terminal and navigate to **cacheNoisetteGame folder** with the Java scripts.
    - Navigation can be done via the command ```cd <folder path>```
3. Once you navigate to **cacheNoisetteGame** folder run the following command ```javac *.java && java Driver```
    - This command will compile and run the program at the same time.
    
### Image Representation
![Image taken from Part-I Summer Project](assets/readme/readme-realGameRepresentation.png)

## Game Rules
The rules of the game are simple:
- The game board consists of a 4x4 grid of empty spaces.
- The holes are in four of those spaces. They are always in the same space.
    - ![An empty cache noisette board](assets/readme/readme-codedGameHoleLocations.png)<br />*An empty cache noisette board*
- There are game pieces that look like squirrels. Up to four squirrel pieces may be placed on the board, each has its own colour and shape. At the start of the game each squirrel carries a nut. The nut resides on the same square as the squirrel’s head.
- There are many levels to the game (sixty levels in the full game!). Each level defines which of the squirrel pieces are in use for that level, their starting position on the board, and the rotation of the pieces (a squirrel may face up, down, left or right on the board). Once placed at the start of the game, the rotation of the pieces cannot be changed.
part of a squirrel may be moved into a space that is occupied by another piece (such as another squirrel or a flower). Any part of a squirrel may however move onto a hole.
- If the part of a squirrel holding a nut moves over a hole, the nut drops into that hole. A hole may only hold at most one nut and cannot be removed.
- The game is won when every nut is placed into a hole.

![codedGameSquirrelsFace](assets/readme/readme-codedGameSquirrelsFaces.png)
*The squirrels in the game, facing up, left, right and down respectively. Note the Grey and Red squirrels are straight and occupy two board spaces, but the Brown and Black squirrel pieces are ‘L’ shaped and occupy three spaces.*
## Resources
The resources used were cloned from a different repository. They were provided to us in addition to a class **Picture** which loads an image and can rotate the graphics for us. The repository was:
```
git clone https://scc-source.lancs.ac.uk/scc.Y1/scc.111/cachenoisettes-dist.git
```
These graphics may be changed later on.

