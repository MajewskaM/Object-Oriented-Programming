import random


class BoardPoint:

    def __init__(self, x, y):
        self.x = x
        self.y = y

    # getting random point in range of world size
    def set_random_point(self, organism_world):
        self.x = random.randint(0, organism_world.width - 1)
        self.y = random.randint(0, organism_world.height - 1)

    def __eq__(self, other):
        if isinstance(other, BoardPoint):
            return self.x == other.x and self.y == other.y
        return False

    def __ne__(self, other):
        return not self.__eq__(other)

    def get_x(self):
        return self.x

    def get_y(self):
        return self.y

    def set_x(self, new_x):
        self.x = new_x

    def set_y(self, new_y):
        self.y = new_y
