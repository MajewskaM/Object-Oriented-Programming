import tkinter as tk

from Functionality.FunctionalityStatics import *
from Functionality.GameWindow import GameWindow
from Game.OrganismStatics import START_FILL, TOTAL_NUMBER_OF_ORGANISMS


class Menu:

    def __init__(self):

        self.menu_window = tk.Tk()
        self.menu_window.title("2D-World-Simulator")
        self.menu_window.geometry(f"{MENU_WIDTH}x{MENU_HEIGHT}")
        self.menu_window.resizable(False, False)

        # width and height of a screen
        screen_width = self.menu_window.winfo_screenwidth()
        screen_height = self.menu_window.winfo_screenheight()

        # center position on the screen
        x = (screen_width / 2) - (MENU_WIDTH / 2)
        y = (screen_height / 2) - (MENU_HEIGHT / 2)

        self.menu_window.geometry('%dx%d+%d+%d' % (MENU_WIDTH, MENU_HEIGHT, x, y))

        label_width = tk.Label(self.menu_window, text="Set World Width:")
        label_width.place(relx=0.5, rely=0.1, anchor="center")

        self.new_width = tk.Entry(self.menu_window, width=TEXT_ENTRY_WIDTH)
        self.new_width.place(relx=0.5, rely=0.17, anchor="center")

        label_height = tk.Label(self.menu_window, text="Set World Height:")
        label_height.place(relx=0.5, rely=0.24, anchor="center")

        self.new_height = tk.Entry(self.menu_window, width=TEXT_ENTRY_WIDTH)
        self.new_height.place(relx=0.5, rely=0.31, anchor="center")

        start_button = tk.Button(self.menu_window, text="Start Game Grid", command=lambda: self.check_input(False),
                                 bg="lightgrey")
        start_button.place(relx=0.5, rely=0.5, anchor="center")
        start_button.configure(width=BUTTON_WIDTH, height=BUTTON_HEIGHT)

        start_button_hex = tk.Button(self.menu_window, text="Start Game Hexagonal",
                                     command=lambda: self.check_input(True), bg="lightgrey")
        start_button_hex.place(relx=0.5, rely=0.7, anchor="center")
        start_button_hex.configure(width=BUTTON_WIDTH, height=BUTTON_HEIGHT)

        self.menu_window.mainloop()

    def check_input(self, hex_grid):
        width_str = self.new_width.get()
        height_str = self.new_height.get()

        try:
            width = int(width_str)
            height = int(height_str)
            if width > 0 and height > 0 and (width * height) > ((START_FILL * TOTAL_NUMBER_OF_ORGANISMS) + 1):
                # close the previous window
                self.menu_window.withdraw()
                GameWindow(width, height, hex_grid)
            else:
                self.new_width.delete(0, tk.END)
                self.new_height.delete(0, tk.END)
                self.new_width.insert(0, "Too small values!")
                self.new_height.insert(0, "Too small values!")

        except ValueError:
            self.new_width.delete(0, tk.END)
            self.new_height.delete(0, tk.END)
            self.new_width.insert(0, "Invalid input!")
            self.new_height.insert(0, "Invalid input!")

    def start_game_grid(self):
        new_height_value = self.new_height.get()
        print(new_height_value)

    def start_game_hex(self):
        new_width_value = self.new_width.get()
        print(new_width_value)


# start of an application

Menu()
