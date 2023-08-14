import random

from Functionality.BoardPoint import BoardPoint
from Game.Animal import Animal
from Game.OrganismStatics import *


class CyberSheep(Animal):
    def __init__(self, organism_world, point):
        super().__init__(organism_world, point, 11, 4, "𓃒", OrganismSpecie.CYBER_SHEEP, "#46fff5")

    # on each step it needs to get closer and closer to Sosnowsky's Hogweed
    def action(self):

        if len(self.organism_world.get_sosnowskys_hogweed_list()) > 0:
            move = random.randint(1, 2)
            # first we need to calculate which Sosnowsky's Hogweed is the closest one

            organism = self.calculate_distances()
            x = self.organism_position.x
            y = self.organism_position.y
            new_pos = self.organism_position
            if (move == HORIZONTAL or organism.position_point().get_y() == y) \
                    and organism.position_point().get_x() != x:
                # get closer in horizontal line
                if organism.position_point().get_x() > x:
                    # move organism to the right
                    new_pos = BoardPoint(x + 1, y)
                elif organism.position_point().get_x() < x:
                    new_pos = BoardPoint(x - 1, y)
            else:
                # get closer in vertical line
                if organism.position_point().get_y() > y:
                    # move organism upper
                    new_pos = BoardPoint(x, y + 1)
                elif organism.position_point().get_y() < y:
                    new_pos = BoardPoint(x, y - 1)

            # checking collisions
            new_position = self.organism_world.get_organism_at_pos(new_pos)
            if new_position is not None and new_position.get_organism_specie() != self.organism_specie:
                self.collision(new_position)
            else:
                self.move_to_given_field(new_pos.x, new_pos.y)
        else:
            # cyber-sheep behaves like a normal sheep
            super().action()

    def calculate_distances(self):
        # start with large value
        smallest_distance = float('inf')
        smallest_org = None
        list = self.organism_world.get_sosnowskys_hogweed_list()
        for org_to_remove in list:
            x = abs(self.organism_position.get_x() - org_to_remove.position_point().x)
            y = abs(self.organism_position.get_y() - org_to_remove.position_point().y)
            path = x + y
            if x == 0 or y == 0:
                path -= 1
            if path < smallest_distance:
                smallest_distance = path
                smallest_org = org_to_remove

        return smallest_org
