ComBlockCommandSearchAndReplace
==========
A plugin to mass-modify commands inside of command blocks in a defined area.

How it works
==================
### create selection
* **_/bp1_** while pointing at the desired block or submit its coordinates
* **_/bp1 <<x>x> <<y>y> <<z>z>_**
* **_/bp2_**
* **_/bp2 <<x>x> <<y>y> <<z>z>_**

### modify commands
This is where things turn complicated.
The basic syntax looks like this
* **_/blockreplace <<a>searchfortoken> <<a>replacewithtoken> <<a>also-look-for> <<a>and-also-replace> <<a>minecraft:give> <<a>essentials:give>_**
so you could use it as
* **_/blockreplace <<a>effect> <<a>minecraft:effect> <<a>tp> <<a>minecraft:tp> <<a>clear> <<a>minecraft:clear> <<a>give> <<a>minecraft:give>_**
but you might want to specify more than just a word because you realised replacing clear with minecraft:clear screwed up the /effect clear command, use quotes ("") and run the process again
* **_/blockreplace <<a>"effect minecraft:clear"> <<a>"minecraft:effect clear">_**


### output
If your selection contains a huge number of command blocks, you will face a wall of spam with commands the way they were before changing and what they are afterwards.
Consider this type of spam as comfort so you can determine from your chatlog if things went ok or wrong, without having to look inside any command block.