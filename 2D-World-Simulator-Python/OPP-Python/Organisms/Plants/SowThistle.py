from Game.OrganismStatics import *
from Game.Plant import Plant


class SowThistle(Plant):
    def __init__(self, organism_world, point):
        super().__init__(organism_world, point, 0, 0, "*", OrganismSpecie.SOW_THISTLE, "#feff00")

    # sowThistle has 3 attempts to sow in one round
    def action(self):
        attempts = MAX_ATTEMPT
        while attempts > 0:
            super().action()
            attempts -= 1
