from Game.Animal import *
from Game.OrganismStatics import OrganismSpecie


class Wolf(Animal):
    def __init__(self, organism_world, point):
        super().__init__(organism_world, point, 9, 5, "W", OrganismSpecie.WOLF, "#939393")
