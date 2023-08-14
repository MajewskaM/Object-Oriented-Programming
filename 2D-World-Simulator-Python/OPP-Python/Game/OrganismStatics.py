from enum import Enum

MIN_BOARD_SIZE = 7
NO_POINT = -1

SIDES = 4
SIDES_HEX = 6
UP = 0
DOWN = 1
RIGHT = 2
LEFT = 3
SECOND_DOWN = 4
SECOND_UP = 5
NONE = 6
SET = 7
HORIZONTAL = 1

# number of organisms on the board at the beginning of a game
START_FILL = 1
TOTAL_NUMBER_OF_ORGANISMS = 11
PROBABILITY_RANGE = 100
CHANCE_TO_STAY = 75
DEFENCE_STRENGTH = 5
NEW_STRENGTH = 5
CHANCE_GETAWAY = 50
POWER_RANGE = 10
COOL_DOWN_TIME = 5

# plants statics
SOW_PROBABILITY = 17
MAX_ATTEMPT = 3
ADDITIONAL_STRENGTH = 3

EMPTY_FIELD = "#cfd9cf"
FONT_COLOR = 'black'


class OrganismSpecie(Enum):
    # animals
    WOLF = 1
    SHEEP = 2
    FOX = 3
    TURTLE = 4
    ANTELOPE = 5
    CYBER_SHEEP = 12

    # human
    HUMAN = 6

    # plants
    GRASS = 7
    SOW_THISTLE = 8
    GUARANA = 9
    BELLADONNA = 10
    SOSNOWSKYS_HOGWEED = 11
