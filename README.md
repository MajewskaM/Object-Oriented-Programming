# Object-Oriented-Programming
🌱 University project written in C++, Java &amp; Python for the Object Oriented Programming course.
</br>
The main goal of the project is the implementation of a 2D virtual world simulator as console application (C++) and then by using GUI (Java & Python).

## Project Name: 2D-World-Simulator

The world have the structure of a two-dimensional NxM grid. Simple life forms will exist, they may collide, multiply and perform special actions depending on its species. Every organism occupy exactly one cell of world's 2D grid array.
</br> --------------------------------------------------------------------------------------------------------------------- </br>
All actions are performed in turns (animals move, while plants remain static). In case of a collision (one of the
organisms enters a cell occupied by another organism) stronger of them wins, either by killing its
opponent (i.e. wolf) or by reflecting the opponent's attack (i.e. turtle). The order of all actions depends on the initiative and age of organisms. Game include a human player and unlike regular
animals, his movement is not random - it is controlled by player by pressing the appropriate arrow key (C++) or WASD (Java & Python) before the start of every
round. Human also possesses a special ability - *Magical Potion* which can be activated with a separate key (ENTER). It works 5 rounds then it is automatically deactivated for next 5 rounds. It rises human strength to 10 in first turn and decreases by "1" per
round until it returns to original value. If his strength is already 10 magical potion will not have.
</br> --------------------------------------------------------------------------------------------------------------------- </br>
🌱 Implementation notes:
</br>
- Class Organism is abstract, and it is the base class for two other abstract
classes: Plant and Animal,
- Common behaviors for all animals are implemented in the class Animal,
- Base movement in method action() - every typical animal moves to a randomly
selected neighboring field,
- Multiplication in method collision() - when two animals of the same species collide,
instead of fighting with each other, both animals remain in their original positions, and
next to them a new animal of their species is created,
- Class Human extend the class Animal,
- Using Encapsulation and Polymorphism,
- Possibility of saving and loading state of the world to file and from file,
</br> --------------------------------------------------------------------------------------------------------------------- </br>
- Additionally in GUI applications:
  - Adding a new organism of any kind to the word by clicking on free map
cell,
  - Creating two different versions of world - one
implementation use grid and the other one use hexagonal
fields (with all consequences that impact neighborhood relations between
cells).

## Explenation Of Symbols and Organism's Behaviour
</br>

| Symbol | Strength | Initiative | action() | collision() |
| ------ | -------- | ---------- | -------- | ----------- |
| **_Animals_** |  |  | *every typical animal moves to a randomly selected neighboring field* | *when two animals of the same species collide, instead of fighting with each other, both animals remain in their original positions, and next to them a new animal of their species is created* |
| ![#fec7cb](https://placehold.co/10x10/fec7cb/fec7cb.png) **'H'** - Human | 5 | 4 | movement controlled by player | human special ability |
| ![#939393](https://placehold.co/10x10/939393/939393.png) **'W'** - Wolf | 9 | 5 | default for Animal | default for Animal |
| ![#e3e3e3](https://placehold.co/10x10/e3e3e3/e3e3e3.png) **'S'** - Sheep | 4 | 4 | default for Animal | default for Animal |
| ![#ff8b12](https://placehold.co/10x10/ff8b12/ff8b12.png) **'F'** - Fox | 3 | 7 | fox will never move to a cell occupied by a stronger organism | default for Animal |
| ![#0a9000](https://placehold.co/10x10/0a9000/0a9000.png) **'T'** - Turtle | 2 | 1 | has 75% chance to stay in the same place | reflects attacks of animal with strength less than 5, attacker will return to the previous cell |
| ![#ffbf2d](https://placehold.co/10x10/ffbf2d/ffbf2d.png) **'A'** - Antelope | 4 | 4 | has wider range of movement - 2 fields instead of 1 | has 50% chance to escape from fight, in such case it moves to a free neighboring cell |
| *In Python one more organism* |  |  |
| ![#46fff5](https://placehold.co/10x10/46fff5/46fff5.png) **'𓃒'** - Cyber Sheep | 11 | 4 | its main goal is the extermination of Sosnowsky's hogweed. It always moves towards the closes hogweed and tries to eat it, if there are no Sosnowsky's hogweeds, it behaves like a normal sheep | eats Sosnowsky's hogweed |
| **_Plants_** |  |  |*with a certain probability the plant can "sow" a new plant on a random free neighboring field* | *it is eaten by the attacking animal* |
| ![#1b5a00](https://placehold.co/10x10/1b5a00/1b5a00.png) **'#'** - Grass | 0 | - | default for Plant | default for Plant |
| ![#feff00](https://placehold.co/10x10/feff00/feff00.png) **'*'** - Sow Thistle | 0 | - | performs 3 attempts at spreading in each turn | default for Plant |
| ![#ff2b1c](https://placehold.co/10x10/ff2b1c/ff2b1c.png) **'+'** - Guarana | 0 | - | default for Plant | strength of the animal which ate guarana is permanently increased by 3 |
| ![#9c7fe6](https://placehold.co/10x10/9c7fe6/9c7fe6.png) **'O'** - Belladonna | 99 | - | default for Plant | kills any animal which eats it |
| ![#dbf6a0](https://placehold.co/10x10/dbf6a0/dbf6a0.png) **'Y'** - Sosnowsky's Hogweed | 10 | - | kills every animal in its immediate neighborhood except cyber-sheep | kills any animal which eats it, apart from cyber-sheep |
| ![#cfd9cf](https://placehold.co/10x10/cfd9cf/cfd9cf.png) **' '** - Free Cell |  |  |  |  |
