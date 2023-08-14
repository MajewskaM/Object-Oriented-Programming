import sys
import tkinter as tk
from tkinter import filedialog

from Functionality.BoardGrid import BoardGrid
from Functionality.BoardHex import BoardHex
from Functionality.BoardPoint import BoardPoint
from Functionality.FunctionalityStatics import *
from Game.OrganismStatics import *
from Game.World import World


class GameWindow(tk.Toplevel):

    def __init__(self, world_width, world_height, hex_grid, other_world=None):
        super().__init__()
        # setting the game window
        self.state('zoomed')
        self.title("2D-World-Simulator")
        self.protocol("WM_DELETE_WINDOW", self.exit)
        self.resizable(False, False)

        # constructor of game frame using other world instance (used in loading game from file)
        if other_world is not None:
            self.new_world = World(self, other_world.get_width(), other_world.get_height(),
                                   other_world.get_hex(), other_world)
            # setting new world instance to all organisms
            organism_list = self.new_world.get_list_of_organisms()
            iterator = iter(organism_list)
            for organism in iterator:
                organism.set_organism_world(self.new_world)
        else:
            self.new_world = World(self, world_width, world_height, hex_grid)

        self.button_grid = []

        # checking what kind of board we should create
        if not hex_grid:
            board = BoardGrid(self, self.new_world, self.button_grid)
        else:
            board = BoardHex(self, self.new_world, self.button_grid)

        # adding text box in the top-right corner
        self.text_box = tk.Label(self, font=("Arial", FONT_SIZE))
        self.text_box.place(relx=1.0, rely=0.0, anchor="ne")

        # adding functionality buttons in the down-right corner
        self.turn_button = tk.Button(self, text="Next Turn", bg=BUTTON_COLOR, height=BUTTON_HEIGHT,
                                     command=self.new_world.make_turn)
        self.turn_button.place(relx=0.98, rely=0.96, anchor="se")
        self.turn_button.configure(width=10, height=BUTTON_HEIGHT)

        self.save_button = tk.Button(self, text="Save Game", bg=BUTTON_COLOR, height=BUTTON_HEIGHT,
                                     command=self.save_game)
        self.save_button.place(relx=0.92, rely=0.96, anchor="se")
        self.save_button.configure(width=10, height=BUTTON_HEIGHT)

        self.load_button = tk.Button(self, text="Load Game", bg=BUTTON_COLOR, height=BUTTON_HEIGHT,
                                     command=self.load_game)
        self.load_button.place(relx=0.86, rely=0.96, anchor="se")
        self.load_button.configure(width=10, height=BUTTON_HEIGHT)

        self.bind("<KeyPress>", self.handle_key_move_press)

        # add the organisms to board visualization
        self.new_world.draw_world()

        # adding the grid panel
        self.mainloop()

    def exit(self):
        self.destroy()
        sys.exit()

    # handling pressing keys and performing appropriate operations
    def handle_key_move_press(self, event):
        key = event.keysym
        position = self.new_world.get_world_human().get_organism_position()
        new_pos_x = position.get_x()
        new_pos_y = position.get_y()

        if not self.new_world.get_world_human().get_killed():
            self.new_world.get_world_human().set_direction(SET)
            # changing human direction of move
            if key.upper() == "S":
                new_pos_y += 1
                if self.new_world.get_hex():
                    if self.new_world.get_world_human().get_organism_position().get_y() % 2 != 0:
                        new_pos_x += 1

            elif key.upper() == "W":
                new_pos_y -= 1
                if self.new_world.get_hex():
                    if self.new_world.get_world_human().get_organism_position().get_y() % 2 != 0:
                        new_pos_x += 1

            elif key.upper() == "A":
                new_pos_x -= 1
            elif key.upper() == "D":
                new_pos_x += 1
            elif key == "space":
                # activating human ability
                self.new_world.get_world_human().activate_ability()
                self.new_world.get_world_human().set_direction(NONE)
                return
            else:
                # in hex world we can move in 6 directions!
                if self.new_world.get_hex():
                    if key.upper() == "Q":
                        new_pos_y -= 1
                        if self.new_world.get_world_human().get_organism_position().get_y() % 2 == 0:
                            new_pos_x -= 1

                    elif key.upper() == "Z":
                        new_pos_y += 1
                        if self.new_world.get_world_human().get_organism_position().get_y() % 2 == 0:
                            new_pos_x -= 1
                    else:
                        self.new_world.get_world_human().set_direction(NONE)
                else:
                    self.new_world.get_world_human().set_direction(NONE)

            # check the correctness of the desired point
            if self.new_world.get_world_human().check_point_correctness(new_pos_x, new_pos_y):
                if self.new_world.get_world_human().get_direction() != NONE:
                    self.new_world.get_world_human().set_direction(SET)
                    self.new_world.get_world_human().set_direction_point(BoardPoint(new_pos_x, new_pos_y))

                    self.new_world.make_turn()
                    self.new_world.draw_world()
                    self.new_world.get_world_human().set_direction(NONE)
            else:
                self.new_world.get_world_window().add_comment("ERROR")

            self.new_world.get_world_human().set_direction(NONE)
        else:
            self.new_world.get_world_window().add_comment("HUMAN is not alive!!!")

    # displaying dialog window to chose save path
    def save_game(self):
        file_path = filedialog.asksaveasfilename(defaultextension=".pkl")
        if file_path:
            self.new_world.save_game_to_file(file_path)
            self.focus_set()

    # displaying dialog window to load game from file
    def load_game(self):
        file_path = filedialog.askopenfilename(filetypes=[("", "*.pkl")])
        if file_path:
            loaded_game = self.new_world.load_game_from_file(file_path)
            # hiding previous window
            self.withdraw()
            new_game_window = GameWindow(loaded_game.width, loaded_game.height, loaded_game.hex, loaded_game)
        # self.game_window.focus_set()

    def add_comment(self, comment):
        current_text = self.text_box.cget("text")
        updated_text = current_text + comment + "\n"
        self.text_box.config(text=updated_text)

    def clean_comments(self):
        self.text_box.config(text="")
