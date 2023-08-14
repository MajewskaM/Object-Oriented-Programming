import random

from Game.Animal import Animal
from Game.OrganismStatics import *


class Turtle(Animal):
    def __init__(self, organism_world, point):
        super().__init__(organism_world, point, 2, 1, "T", OrganismSpecie.TURTLE, "#0a9000")

    # turtle has 75% chance to stay in the same place, so it may not move
    def action(self):
        chance = random.randint(1, PROBABILITY_RANGE)
        if chance > CHANCE_TO_STAY:
            # just performing casual animal action, parent function
            super().action()
        # else he just stays in the same place

    # checking its defence successfulness
    def defence(self, attacking_organism):
        if self.reflect_attack(attacking_organism):
            return True
        return False

    # reflecting attack from an opponent
    def reflect_attack(self, attacking_organism):
        # that means he defended himself from attacking organism
        if attacking_organism.get_strength() < DEFENCE_STRENGTH:
            self.organism_world.get_world_window().add_comment(self.organism_sign + " -> reflected attack from "
                                                               + attacking_organism.get_organism_sign())
            return True
        return False
