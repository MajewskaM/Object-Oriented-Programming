import random

from Game.Animal import Animal
from Game.OrganismStatics import *


class Antelope(Animal):
    def __init__(self, organism_world, point):
        super().__init__(organism_world, point, 4, 4, "A", OrganismSpecie.ANTELOPE, "#ffbf2d")

    # moving two fields instead of one
    def action(self):
        # getting new first point, and the second it should not be the initial position
        new_point1 = self.get_near_point(self.organism_position, False)
        new_point2 = self.get_near_point(new_point1, False)
        while True:
            if new_point2 != self.organism_position and new_point2 != new_point1:
                break
            new_point2 = self.get_near_point(new_point1, False)

        new_pos = self.organism_world.get_organism_at_pos(new_point2)

        # checking there is other organism
        if new_pos is not None:
            super().collision(new_pos)
        else:
            self.move_to_given_field(new_point2.get_x(), new_point2.get_y())

    # giving info about antelope defence success
    def defence(self, attacking_organism):
        if self.get_away(attacking_organism):
            return True
        return False

    # performing antelope defence function
    def get_away(self, attacking_organism):
        chance = random.randint(1, PROBABILITY_RANGE)
        if chance <= CHANCE_GETAWAY:
            # antelope runs away - moves to neighbouring free field
            new_point = self.get_near_point(self.organism_position, True)

            # no place to run, fight -> going back in collision function!
            if self.organism_position == new_point:
                return False
            # that means antelope defended itself and run away
            self.organism_world.get_world_window().add_comment(
                "ANTELOPE - > run away from " + attacking_organism.get_organism_sign())
            return True

        return False
