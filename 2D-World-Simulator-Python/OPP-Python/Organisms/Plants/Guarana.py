from Game.OrganismStatics import *
from Game.Plant import Plant


class Guarana(Plant):
    def __init__(self, organism_world, point):
        super().__init__(organism_world, point, 0, 0, "+", OrganismSpecie.GUARANA, "#ff2b1c")

    # checking defence successfulness
    def defence(self, attacking_organism):
        self.increase_strength(attacking_organism)
        # plant must be eaten anyway -> means we come back to collision()
        return False

    # performing defence of guarana -> adding strength to attacking organism
    def increase_strength(self, attacking_organism):
        new_strength = attacking_organism.get_strength() + ADDITIONAL_STRENGTH
        attacking_organism.set_strength(new_strength)
        self.organism_world.get_world_window().add_comment(attacking_organism.get_organism_sign() +
                                                           " gained +3 STRENGTH by eating GUARANA!")
