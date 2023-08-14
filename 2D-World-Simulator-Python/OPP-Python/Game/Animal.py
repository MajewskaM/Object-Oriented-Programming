from abc import ABC

from Game.Organism import Organism


class Animal(Organism, ABC):
    def __init__(self, organism_world, point, strength, initiative, sign, specie, color):
        super().__init__(organism_world, point, strength, initiative, sign, specie, color)

    # animal moves to a randomly selected neighborhood field or fight with other organism
    def action(self):
        # looking for near field close to our organism
        new_point = self.get_near_point(self.get_organism_position(), False)

        # checking collisions
        new_position = self.get_organism_world().get_organism_at_pos(new_point)
        if new_position is not None and new_position.get_organism_specie() != self.organism_specie:
            self.collision(new_position)
        else:
            self.move_to_given_field(new_point.x, new_point.y)

    # performing collisions
    def collision(self, other_organism):

        # multiplication of two same organisms
        if self.organism_specie == other_organism.get_organism_specie():
            self.multiply_organisms(other_organism)
        else:
            # other organism may defend itself or perform some other action when something tries to eat it
            if other_organism.defence(self):
                return

            # looking for the result of the fight depending on organisms strength
            if self.strength >= other_organism.get_strength():
                new_point = other_organism.get_organism_position()
                organism_sign_string = other_organism.get_organism_sign()
                opponent_sign_string = self.organism_sign
                self.organism_world.delete_organism(other_organism)
                self.move_to_given_field(new_point.get_x(), new_point.get_y())
            else:
                organism_sign_string = self.organism_sign
                opponent_sign_string = other_organism.get_organism_sign()
                self.organism_world.delete_organism(self)

            # adding new content to commentator report
            self.organism_world.get_world_window().add_comment(organism_sign_string + " -> Was killed (collision) by "
                                                               + opponent_sign_string)

    def defence(self, attacking_organism):
        return False

    # multiplying organisms (of the same specie), adding new organism to near field
    def multiply_organisms(self, other_organism):
        # getting child organism position
        new_point = self.get_near_point(self.organism_position, True)

        # that means we cannot spawn the organism, there is no free space around
        if new_point == self.organism_position:
            new_point = other_organism.get_near_point(other_organism.get_organism_position(), True)

            # look for free space around second organism
            if new_point == other_organism.get_organism_position():
                return
            else:
                # there is no space around first organism but there is free space around second organism
                new_organism = self.organism_world.create_new_organism(self.organism_specie, new_point)

        else:
            # there is free space around first organism
            new_organism = self.organism_world.create_new_organism(self.organism_specie, new_point)

        self.organism_world.add_organism_to_world(new_organism)
        # adding commentator report
        self.organism_world.get_world_window().add_comment(self.organism_sign + " -> Was born (multiplication)")

    # look whether given organism is an animal
    def check_if_organism_is_animal(self):
        return True
