# 2D-World-Simulator-Java
## Key Configuration
**SPACE** - *activate human ability* </br>
**W A S D** - *chosing direction of human move*</br>
</br>
**🌱 Additionally in hexagonal world:**
  - **W** - *moving human to right upper cell*
  - **S** - *moving human to right lower cell*
  - **Q** - *moving human to left upper cell*
  - **Z** - *moving human to left lower cell*
Buttons to **_make a turn/save game/load game_** are placed in the upper right corner. </br>
Placing any organism by clicking on any free cell is avaliable in this application.
## Gameplay
🌱 At the start of a game, there appears a window where you can chose board size and its visualization - to be a grid or hexagonal version of a world.
## Examples of Game Saves:
*Java-Saves/save_1* </br>
**Hexagonal World:** Human ability is already activated. </br>
</br>
*Java-Saves/save_2* </br>
**Hexagonal World:** Human ability is deactivated for next 5 rounds.</br>
</br>
*Java-Saves/save_3* </br>
**Grid World:** Situation when human is in the left upper corner of a board, 
his ability is activated (strength is risen to 10) and he is surrounded by two wolves (with strength = 9).
Knowing this information there is no possibility for human to die in this round, he won't get killed by weaker organism.
Next round his strength will be 9 so now he might get killed by one of the wolves as his iniciative is lower (wolves will move before human).
