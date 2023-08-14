from Game.Animal import Animal
from Game.OrganismStatics import OrganismSpecie


class Fox(Animal):
    def __init__(self, organism_world, point):
        super().__init__(organism_world, point, 3, 7, "F", OrganismSpecie.FOX, "#ff8b12")

    # performing action for fox
    def action(self):
        new_point = self.get_near_point(self.organism_position, False)
        new_pos = self.organism_world.get_organism_at_pos(new_point)

        # fox is not moving when on desired position there is organism with which he may lose the fight
        if new_pos is not None:
            if new_pos.get_strength() < self.strength:
                super().collision(new_pos)
            elif new_pos.get_strength() == self.strength:
                if new_pos.get_when_born() > self.when_born:
                    super().collision(new_pos)
            else:
                self.organism_world.get_world_window().add_comment(self.organism_sign + " smells " +
                                                                   new_pos.get_organism_sign() + "'s stink...")
        else:
            self.move_to_given_field(new_point.get_x(), new_point.get_y())
