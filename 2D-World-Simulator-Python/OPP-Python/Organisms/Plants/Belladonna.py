from Game.OrganismStatics import *
from Game.Plant import Plant


class Belladonna(Plant):
    def __init__(self, organism_world, point):
        super().__init__(organism_world, point, 99, 0, "0", OrganismSpecie.BELLADONNA, "#9c7fe6")

    # checking defence successfulness
    def defence(self, attacking_organism):
        # always performing defence -> killing animal
        self.organism_world.delete_organism(attacking_organism)
        self.organism_world.get_world_window().add_comment(
            attacking_organism.get_organism_sign() + " died by eating BELLADONNA!")
        return True
