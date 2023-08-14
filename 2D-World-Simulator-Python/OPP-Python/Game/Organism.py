import random
from abc import ABC, abstractmethod

from Functionality.BoardPoint import BoardPoint
from Functionality.FunctionalityStatics import *
from Game.OrganismStatics import *


class Organism(ABC):

    def __init__(self, organism_world, point, strength, initiative, sign, specie, color):
        self.organism_world = organism_world
        self.organism_position = point
        self.when_born = organism_world.get_turn_number()
        self.strength = strength
        self.initiative = initiative
        self.organism_sign = sign
        self.organism_specie = specie
        self.organism_color = color
        self.killed = False

    @abstractmethod
    def action(self):
        pass

    @abstractmethod
    def collision(self, other_organism):
        pass

    @abstractmethod
    def defence(self, attacking_organism):
        pass

    # getting random field near organism, it may be free or not
    # point may stay the same -> f.e. if there is no place for spawning new organism
    def get_near_point(self, point, free):
        move = 0
        new_pos_x = 0
        new_pos_y = 0
        pos_x = point.get_x()
        pos_y = point.get_y()

        correct_pos = False
        left_check = False
        right_check = False
        up_check = False
        down_check = False
        up_second = False
        down_second = False

        sides = SIDES
        i = 0
        checked = 0
        hexagonal = self.organism_world.get_hex()
        if hexagonal:
            sides = SIDES_HEX

        while True:
            if (checked > (SIDES - 1) and not hexagonal) or (checked > (SIDES_HEX - 1) and hexagonal):
                break

            if hexagonal:
                move = random.randint(0, SIDES_HEX - 1)
            else:
                move = random.randint(0, SIDES - 1)

            i = 0
            new_pos_x = pos_x
            new_pos_y = pos_y

            if not up_check:
                if move == UP:
                    new_pos_y = pos_y + 1
                    up_check = True
                i += 1

            if not down_check:
                if move == DOWN:
                    new_pos_y = pos_y - 1
                    down_check = True
                i += 1

            if not right_check:
                if move == RIGHT:
                    new_pos_x = pos_x + 1
                    right_check = True
                i += 1

            if not left_check:
                if move == LEFT:
                    new_pos_x = pos_x - 1
                    left_check = True
                i += 1

            # if it is hexagonal world we also need to check additional fields
            if hexagonal:
                if not down_second:
                    if move == SECOND_DOWN:
                        if new_pos_y % 2 == 0:
                            new_pos_x = pos_x - 1
                            new_pos_y = pos_y + 1
                        else:
                            new_pos_x = pos_x + 1
                            new_pos_y = pos_y + 1
                        down_second = True
                    i += 1

                if not up_second:
                    if move == SECOND_UP:
                        if new_pos_y % 2 == 0:
                            new_pos_x = pos_x - 1
                            new_pos_y = pos_y - 1
                        else:
                            new_pos_x = pos_x + 1
                            new_pos_y = pos_y - 1
                        up_second = True
                    i += 1

            sides -= 1

            if self.check_point_correctness(new_pos_x, new_pos_y):
                # if the field must be free
                if free:
                    # that means we checked all sides and there is no possible free side
                    if left_check and right_check and up_check and down_check:
                        if hexagonal and down_second and up_second:
                            break
                        # point stays the same
                        if not hexagonal:
                            break
                    else:
                        # we also need to check if the field is free (the point)
                        if self.check_if_field_is_free(new_pos_x, new_pos_y):
                            correct_pos = True
                else:
                    correct_pos = True

            if correct_pos:
                break

        if correct_pos:
            return BoardPoint(new_pos_x, new_pos_y)
        else:
            # no place around, point stays the same
            return self.organism_position

    # checking if point is on the map
    def check_point_correctness(self, x, y):
        if self.organism_world.get_height() > y >= 0:
            if self.organism_world.get_width() > x >= 0:
                return True
        return False

    def check_if_field_is_free(self, x, y):
        if self.organism_world.get_states_of_fields()[y][x] is not None:
            return False
        return True

    # moving organism to given field
    def move_to_given_field(self, x, y):
        self.organism_world.get_states_of_fields()[self.organism_position.get_y()][
            self.organism_position.get_x()] = None
        self.organism_world.get_states_of_fields()[y][x] = self
        self.organism_position.set_x(x)
        self.organism_position.set_y(y)

    def draw(self):
        if self.organism_world.get_hex():
            self.organism_world.get_world_window().button_grid[self.organism_position.get_y()][
                self.organism_position.get_x()] \
                .change_color(self.organism_color)
            self.organism_world.get_world_window().button_grid[self.organism_position.get_y()][
                self.organism_position.get_x()] \
                .disabled_state()
            self.organism_world.get_world_window().button_grid[self.organism_position.get_y()][
                self.organism_position.get_x()] \
                .paint_hexagon()
        else:
            self.organism_world.get_world_window().button_grid[self.organism_position.get_y()][
                self.organism_position.get_x()] \
                .config(bg=self.organism_color, fg=FONT_COLOR, text=self.organism_sign, state="disabled")

    def set_organism_world(self, new_world):
        self.organism_world = new_world

    def get_organism_world(self):
        return self.organism_world

    def get_killed(self):
        return self.killed

    def set_killed(self, new_killed):
        self.killed = new_killed

    def get_organism_position(self):
        return self.organism_position

    def get_organism_sign(self):
        return self.organism_sign

    def get_organism_specie(self):
        return self.organism_specie

    def get_strength(self):
        return self.strength

    def get_initiative(self):
        return self.initiative

    def get_when_born(self):
        return self.when_born

    def set_strength(self, new_strength):
        self.strength = new_strength
