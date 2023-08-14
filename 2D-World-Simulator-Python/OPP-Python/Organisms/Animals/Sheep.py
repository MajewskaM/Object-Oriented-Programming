from Game.Animal import Animal
from Game.OrganismStatics import OrganismSpecie


class Sheep(Animal):
    def __init__(self, organism_world, point):
        super().__init__(organism_world, point, 4, 4, "S", OrganismSpecie.SHEEP, "#e3e3e3")
