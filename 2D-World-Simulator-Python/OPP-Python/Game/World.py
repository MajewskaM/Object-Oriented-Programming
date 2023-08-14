import pickle

from Functionality.BoardPoint import BoardPoint
from Functionality.FunctionalityStatics import ENABLED
from Game.OrganismStatics import *
from Organisms.Animals.Antelope import Antelope
from Organisms.Animals.CyberSheep import CyberSheep
from Organisms.Animals.Fox import Fox
from Organisms.Animals.Human import Human
from Organisms.Animals.Sheep import Sheep
from Organisms.Animals.Turtle import Turtle
from Organisms.Animals.Wolf import Wolf
from Organisms.Plants.Belladonna import Belladonna
from Organisms.Plants.Grass import Grass
from Organisms.Plants.Guarana import Guarana
from Organisms.Plants.SosnowskysHogweed import SosnowskysHogweed
from Organisms.Plants.SowThistle import SowThistle


class World:

    def __init__(self, game_window, new_width, new_height, hex_grid, other_world=None):
        self.width, self.height = (new_width, new_height)
        self.hex = hex_grid
        self.world_window = game_window
        self.to_remove = []
        self.sosnowskys_hogweed_list = []
        if other_world is not None:
            self.turn_number = other_world.get_turn_number()
            self.list_of_organisms = other_world.get_list_of_organisms()
            self.states_of_fields = other_world.get_states_of_fields()
            self.sosnowskys_hogweed_list = other_world.get_sosnowskys_hogweed_list()
            self.world_human = other_world.get_world_human()
        else:
            self.turn_number = 1
            self.list_of_organisms = []
            self.states_of_fields = [[None for _ in range(self.width)] for _ in range(self.height)]

            # always at the beginning we need to add one human
            new_human = self.create_new_organism(OrganismSpecie.HUMAN, self.random_free_point())
            self.world_human = new_human
            self.add_organism_to_world(new_human)
            for i in range(START_FILL):
                self.create_every_organism()

    # in each turn all animals perform their actions in specified order
    def make_turn(self):
        self.turn_number += 1
        self.world_window.clean_comments()
        # we need to sort organism, to decide which one can move as a first one
        self.sort_organisms()

        # now using the list, we perform actions of each organism
        for organism in self.list_of_organisms:
            # to not perform action on newly born organisms or deceased organisms
            if organism.when_born != self.turn_number and not organism.killed:
                organism.action()

        self.remove_organisms()
        self.draw_world()

    # changing fields in the button_grid
    def draw_world(self):
        # make all fields empty
        for i in range(self.height):
            for j in range(self.width):
                if not self.hex:
                    self.world_window.button_grid[i][j] \
                        .config(bg=EMPTY_FIELD, text="", state="normal")
                else:
                    self.world_window.button_grid[i][j].change_color(EMPTY_FIELD)
                    self.world_window.button_grid[i][j].enabled_state()

        # add the organisms to board visualization
        for organism in self.list_of_organisms:
            organism.draw()

    # deleting organism, adding to remove list
    def delete_organism(self, organism):
        x = organism.get_organism_position().get_x()
        y = organism.get_organism_position().get_y()
        self.states_of_fields[y][x] = None
        organism.set_killed(True)
        self.to_remove.append(organism)

    # removing objects according to remove list
    def remove_organisms(self):
        for org_to_remove in self.to_remove:
            if org_to_remove in self.list_of_organisms:
                self.list_of_organisms.remove(org_to_remove)
                if org_to_remove.organism_specie == OrganismSpecie.SOSNOWSKYS_HOGWEED:
                    self.sosnowskys_hogweed_list.remove(org_to_remove)
        self.to_remove.clear()

    # sorting organisms using appropriate comparator (initiative and age)
    def sort_organisms(self):
        # sort organisms based on initiative in descending order
        # if the initiative is the same, sort based on round when it was born in ascending order
        self.list_of_organisms.sort(key=lambda x: (-x.initiative, x.when_born))

    # function places every kind of organism ONCE on the board (in different fields)
    def create_every_organism(self):
        # Creating animals
        organism_position = self.random_free_point()
        self.add_organism_to_world(Wolf(self, organism_position))
        organism_position = self.random_free_point()
        self.add_organism_to_world(Sheep(self, organism_position))
        organism_position = self.random_free_point()
        self.add_organism_to_world(Fox(self, organism_position))
        organism_position = self.random_free_point()
        self.add_organism_to_world(Turtle(self, organism_position))
        organism_position = self.random_free_point()
        self.add_organism_to_world(Antelope(self, organism_position))
        organism_position = self.random_free_point()
        self.add_organism_to_world(CyberSheep(self, organism_position))

        # Creating Plants
        organism_position = self.random_free_point()
        self.add_organism_to_world(Grass(self, organism_position))
        organism_position = self.random_free_point()
        self.add_organism_to_world(SowThistle(self, organism_position))
        organism_position = self.random_free_point()
        self.add_organism_to_world(Guarana(self, organism_position))
        organism_position = self.random_free_point()
        self.add_organism_to_world(Belladonna(self, organism_position))
        organism_position = self.random_free_point()
        self.add_organism_to_world(SosnowskysHogweed(self, organism_position))

    # returns new organism according to given specie and position on board
    def create_new_organism(self, organism_specie, organism_point):
        if organism_specie == OrganismSpecie.WOLF:
            return Wolf(self, organism_point)
        elif organism_specie == OrganismSpecie.SHEEP:
            return Sheep(self, organism_point)
        elif organism_specie == OrganismSpecie.FOX:
            return Fox(self, organism_point)
        elif organism_specie == OrganismSpecie.TURTLE:
            return Turtle(self, organism_point)
        elif organism_specie == OrganismSpecie.ANTELOPE:
            return Antelope(self, organism_point)
        elif organism_specie == OrganismSpecie.HUMAN:
            return Human(self, organism_point)
        elif organism_specie == OrganismSpecie.GRASS:
            return Grass(self, organism_point)
        elif organism_specie == OrganismSpecie.SOW_THISTLE:
            return SowThistle(self, organism_point)
        elif organism_specie == OrganismSpecie.GUARANA:
            return Guarana(self, organism_point)
        elif organism_specie == OrganismSpecie.BELLADONNA:
            return Belladonna(self, organism_point)
        elif organism_specie == OrganismSpecie.SOSNOWSKYS_HOGWEED:
            return SosnowskysHogweed(self, organism_point)
        elif organism_specie == OrganismSpecie.CYBER_SHEEP:
            return CyberSheep(self, organism_point)
        else:
            return None

    # generating random new point
    def random_free_point(self):
        new_point = BoardPoint(NO_POINT, NO_POINT)
        for i in range(self.width):
            for j in range(self.height):
                if self.states_of_fields[i][j] is None:
                    while True:
                        new_point.set_random_point(self)
                        x = new_point.get_x()
                        y = new_point.get_y()
                        if self.states_of_fields[y][x] is None:
                            return new_point
                else:
                    continue

        return BoardPoint(NO_POINT, NO_POINT)

    # adding organism to world
    def add_organism_to_world(self, new_organism):
        if new_organism is not None:
            x_board = new_organism.get_organism_position().get_x()
            y_board = new_organism.get_organism_position().get_y()
            self.states_of_fields[y_board][x_board] = new_organism
            self.list_of_organisms.append(new_organism)
            if new_organism.get_organism_specie() == OrganismSpecie.SOSNOWSKYS_HOGWEED:
                self.sosnowskys_hogweed_list.append(new_organism)

    # getting organism at given position
    def get_organism_at_pos(self, point):
        return self.states_of_fields[point.get_y()][point.get_x()]

    def __getstate__(self):
        state = self.__dict__.copy()
        del state['world_window']
        return state

    def __setstate__(self, state):
        self.__dict__.update(state)

    # saving game to file
    def save_game_to_file(self, file_path):
        with open(file_path, "wb") as file:
            pickle.dump(self, file)

    # loading game form given file_path
    def load_game_from_file(self, file_path):
        # Load the saved data from the file
        with open(file_path, "rb") as file:
            loaded_world = pickle.load(file)
        return loaded_world

    def get_width(self):
        return self.width

    def get_height(self):
        return self.height

    def get_hex(self):
        return self.hex

    def get_list_of_organisms(self):
        return self.list_of_organisms

    def get_sosnowskys_hogweed_list(self):
        return self.sosnowskys_hogweed_list

    def get_world_human(self):
        return self.world_human

    def get_world_window(self):
        return self.world_window

    def get_turn_number(self):
        return self.turn_number

    def get_states_of_fields(self):
        return self.states_of_fields
