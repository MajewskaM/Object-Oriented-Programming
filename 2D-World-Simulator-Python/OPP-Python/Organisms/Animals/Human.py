from Game.Animal import Animal
from Game.OrganismStatics import *


class MagicalPotion:
    def __init__(self):
        self.coolDownTime = 0
        self.activated = False
        self.new_strength = NEW_STRENGTH


class Human(Animal):

    def __init__(self, organism_world, point):
        super().__init__(organism_world, point, 5, 4, "H", OrganismSpecie.HUMAN, "#fec7cb")
        self.direction = NONE
        self.direction_point = None
        self.ability = MagicalPotion()

    # performing human move according to player given direction
    def action(self):
        self.reduce_cooldown_time()
        if self.ability.activated:
            self.reduce_activation()

        if self.direction == NONE:
            return

        new_pos = self.organism_world.get_organism_at_pos(self.direction_point)

        # checking there is other organism
        if new_pos is not None:
            super().collision(new_pos)
        else:
            self.move_to_given_field(self.direction_point.x, self.direction_point.y)

        self.direction = NONE
        self.direction_point = None

    # activate human's ability
    def activate_ability(self):
        if self.ability.activated:
            self.organism_world.get_world_window().add_comment(
                "Human ability is already ACTIVATED!")
            return

        if self.ability.coolDownTime > 0:
            self.organism_world.get_world_window().add_comment("Sorry, we cannot activate Human's ability." + "\n" +
                                                               " You need to wait " +
                                                               str(self.ability.coolDownTime) + " round/s.")
            return

        if self.strength >= POWER_RANGE:
            self.organism_world.get_world_window().add_comment("ERROR! Human already has power at least 10! ")
            return

        self.ability.activated = True
        self.ability.new_strength = POWER_RANGE - self.strength

        # strength rises to 10
        self.strength = POWER_RANGE

        self.organism_world.get_world_window().add_comment("HUMAN ability ACTIVATED!")

    # counting time left for activation
    def reduce_activation(self):
        # changing human's strength
        if self.ability.new_strength > 0:
            self.ability.new_strength -= 1
            self.strength -= 1
        else:
            # we need to switch off ability power
            # strength at its initial state -> so we do not have to change it
            self.organism_world.get_world_window().add_comment("HUMAN ability DISACTIVATED!")
            self.ability.activated = False
            self.ability.coolDownTime = COOL_DOWN_TIME

    # reducing cooldown time after activation of ability
    def reduce_cooldown_time(self):
        if self.ability.coolDownTime > 0:
            self.ability.coolDownTime -= 1

    def set_direction(self, new_direction):
        self.direction = new_direction

    def get_direction(self):
        return self.direction

    def set_direction_point(self, point):
        self.direction_point = point
