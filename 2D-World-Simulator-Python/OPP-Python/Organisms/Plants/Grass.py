from Game.OrganismStatics import *
from Game.Plant import Plant


class Grass(Plant):
    def __init__(self, organism_world, point):
        super().__init__(organism_world, point, 0, 0, "#", OrganismSpecie.GRASS, "#1b5a00")
