from Functionality.BoardPoint import BoardPoint
from Game.OrganismStatics import *
from Game.Plant import Plant


class SosnowskysHogweed(Plant):
    def __init__(self, organism_world, point):
        super().__init__(organism_world, point, 10, 0, "Y", OrganismSpecie.SOSNOWSKYS_HOGWEED, "#dbf6a0")

    # kill every animal in all neighborhood cells
    def action(self):
        x = self.organism_position.get_x()
        y = self.organism_position.get_y()
        sides = SIDES
        # hexagonal world means more fields around sosnowskyshogweed
        if self.organism_world.get_hex():
            sides = SIDES_HEX
        new_point = BoardPoint(0, 0)

        for i in range(0, sides):
            if i == UP:
                new_point = BoardPoint(x, y - 1)
            elif i == DOWN:
                new_point = BoardPoint(x, y + 1)
            elif i == RIGHT:
                new_point = BoardPoint(x + 1, y)
            elif i == LEFT:
                new_point = BoardPoint(x - 1, y)
            elif i == SECOND_DOWN:
                if y % 2 == 0:
                    new_point = BoardPoint(x - 1, y + 1)
                else:
                    new_point = BoardPoint(x + 1, y + 1)
            elif i == SECOND_UP:
                if y % 2 == 0:
                    new_point = BoardPoint(x - 1, y - 1)
                else:
                    new_point = BoardPoint(x + 1, y - 1)
            if super().check_point_correctness(new_point.x, new_point.y):
                tmp_organism = self.organism_world.get_organism_at_pos(new_point)
                # we cannot kill plants!
                if tmp_organism is not None and tmp_organism.check_if_organism_is_animal():
                    if tmp_organism.get_organism_specie() != OrganismSpecie.CYBER_SHEEP:
                        self.organism_world.delete_organism(tmp_organism)
                        self.organism_world.get_world_window().add_comment(
                            tmp_organism.get_organism_sign() + " died by SOSNOWSKYHOGWEED!")
        # afterwards just perform sowing
        super().action()

    # checking defence successfulness
    def defence(self, attacking_organism):
        if attacking_organism.get_organism_specie() != OrganismSpecie.CYBER_SHEEP:
            self.organism_world.delete_organism(attacking_organism)
            self.organism_world.get_world_window().add_comment(
                attacking_organism.get_organism_sign() + " died by SOSNOWSKYHOGWEED!")

        else:
            self.organism_world.delete_organism(self)
            self.organism_world.get_world_window().add_comment("SOSNOWSKYHOGWEED! was killed by " +
                                                               attacking_organism.get_organism_sign())
            attacking_organism.move_to_given_field(self.organism_position.get_x(), self.organism_position.get_y())
        return True

    def position_point(self):
        return self.organism_position
