import random
from abc import ABC

from Game.Organism import Organism
from Game.OrganismStatics import *


class Plant(Organism, ABC):
    def __init__(self, organism_world, point, strength, initiative, sign, specie, color):
        super().__init__(organism_world, point, strength, initiative, sign, specie, color)

    # plant sowing with some probability
    def action(self):
        # calculating probability of sowing
        chance_to_sow = random.randint(1, PROBABILITY_RANGE)
        if chance_to_sow <= SOW_PROBABILITY:
            self.sow_plant()

    # performing basic collision for plants -> some plants do not perform collision
    def collision(self, other_organism):
        pass

    # performing basic sow action for every organism
    def sow_plant(self):
        # new position for plant
        new_point = self.get_near_point(self.organism_position, True)

        # if there is no free field to spawn new plant
        if new_point == self.organism_position:
            return

        new_organism = self.organism_world.create_new_organism(self.organism_specie, new_point)
        self.organism_world.add_organism_to_world(new_organism)

        self.organism_world.get_world_window().add_comment(self.organism_sign + " -> Was sowed (action)")

    # default defence action
    def defence(self, attacking_organism):
        return False

    def check_if_organism_is_animal(self):
        return False
