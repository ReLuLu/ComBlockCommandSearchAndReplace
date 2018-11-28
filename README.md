ComBlockCommandSearchAndReplace
==========
A plugin to mass-modify commands inside of command blocks in a defined area.

How it works
==================
### create selection
* **_/bp1_** while pointing at the desired block
* **_/bp1 <<x>x> <<y>y> <<z>z>_** or submit its coordinates
* **_/bp2_**
* **_/bp2 <<x>x> <<y>y> <<z>z>_**

### modify commands
This is where things turn complicated.
The basic syntax looks like this
* **_/blockreplace <<a>searchfortoken> <<a>replacewithtoken> <<a>also-look-for> <<a>and-also-replace> <<a>minecraft:give> <<a>essentials:give>_**
so you could use it as
* **_/blockreplace <<a>effect> <<a>minecraft:effect> <<a>tp> <<a>minecraft:tp> <<a>teleport> <<a>minecraft:teleport> <<a>clear> <<a>minecraft:clear> <<a>give> <<a>minecraft:give> <<a>summon> <<a>minecraft:summon>_**
but you might want to specify more than just a word because you realised replacing give and clear with minecraft:clear and minecraft:give screwed up the /effect clear and /effect give commands, use quotes ("") and run the process again
* **_/blockreplace <<a>"effect minecraft:clear"> <<a>"minecraft:effect clear"> <<a>"effect minecraft:give"> <<a>"minecraft:effect give">_**
and in case you realise command blocks that work with chestplates no longer work, you might want to fix what you broke before
* **_/blockreplace <<a>sminecraft:tpl> <<a>stpl>_**
but you may have noticed you've screwed up commands following an execute [...] run pattern, so you might want to change only these back
* **_/blockreplace <<a>"run minecraft:effect"> <<a>"run effect"> <<a>"run minecraft:tp"> <<a>"run tp"> <<a>"run minecraft:teleport"> <<a>"run teleport"> <<a>"run minecraft:clear"> <<a>"run clear"> <<a>"run minecraft:give"> <<a>"run give"> <<a>"run minecraft:summon"> <<a>"run summon">_**


### output
If your selection contains a huge number of command blocks, you will face a wall of spam with commands the way they were before changing and what they are afterwards.
Consider this type of spam as comfort so you can determine from your chatlog if things went ok or wrong, without having to look inside any command block.