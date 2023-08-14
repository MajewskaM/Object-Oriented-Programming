import tkinter as tk

from Functionality import ListOfAnimals
from FunctionalityStatics import *


class BoardGrid(tk.Frame):

    def __init__(self, game_window, new_world, buttons):
        super().__init__(game_window)
        x = new_world.get_width()
        y = new_world.get_height()

        self.place(relx=0, rely=0, anchor='nw')

        # calculating proper board width and height
        screen_width = self.winfo_screenwidth()
        screen_height = self.winfo_screenheight()
        board_size = int(min(screen_width, screen_height) * BOARD_OCCUPATION)
        min_button_width = board_size // new_world.get_width() // WIDTH_PROPORTION
        min_button_height = board_size // new_world.get_height() // HEIGHT_PROPORTION

        # adding buttons to the grid
        for x in range(new_world.get_height()):
            row = []
            for y in range(new_world.get_width()):
                button = tk.Button(self, height=int(min_button_height), width=int(min_button_width))
                button.grid(row=x, column=y)
                button.config(command=lambda x=x, y=y: ListOfAnimals.button_clicked(game_window, x, y, new_world))
                row.append(button)
            buttons.append(row)
