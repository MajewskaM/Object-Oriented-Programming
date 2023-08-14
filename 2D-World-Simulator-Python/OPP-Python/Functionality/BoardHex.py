import math
import tkinter as tk

import win32api
import win32con
import win32gui

from Functionality import ListOfAnimals
from FunctionalityStatics import *
from Game.OrganismStatics import *


class BoardHex(tk.Frame):

    def __init__(self, game_window, new_world, hex_buttons):
        super().__init__(game_window)
        x = new_world.get_height()
        y = new_world.get_width()

        screen_width = self.winfo_screenwidth()
        screen_height = self.winfo_screenheight()
        board_size = min(screen_width, screen_height) * SCREEN_OCCUPATION

        hex_size = board_size // (x * 2)
        self.configure(width=int(math.sqrt(3) * hex_size) * (y + 1),
                       height=int(2 * hex_size) * x)
        self.place(relx=0, rely=0, anchor='nw')
        pos_x = 0
        pos_y = 0
        for i in range(x):
            row = []
            for j in range(y):
                hex_button = HexagonButton(hex_size, game_window)
                hex_button.grid(row=i, column=j)
                hex_button.place(x=pos_x, y=pos_y)
                hex_button.bind("<Button-1>",
                                lambda event, i=i, j=j: ListOfAnimals.button_clicked(game_window, i, j, new_world,
                                                                                     ENABLED))
                row.append(hex_button)
                pos_x += int(math.sqrt(3) * hex_size) + 1
            hex_buttons.append(row)
            if i % 2 == 0:
                pos_x = int(math.sqrt(3) * hex_size) / 2
            else:
                pos_x = 0

            pos_y += int(HEX_ACCURACY * (math.sqrt(3) * hex_size))


# instance of one hexagonal button
class HexagonButton(tk.Canvas):
    color = EMPTY_FIELD

    def __init__(self, hex_size, frame):
        super().__init__(frame)
        self.state = ENABLED
        self.hex_size = hex_size
        # make transparent area around polygon
        self.configure(relief=tk.SUNKEN, borderwidth=0, highlightthickness=0, bg='#000000')
        hwnd = self.winfo_id()
        colorkey = win32api.RGB(0, 0, 0)
        wnd_exstyle = win32gui.GetWindowLong(hwnd, win32con.GWL_EXSTYLE)
        new_exstyle = wnd_exstyle | win32con.WS_EX_LAYERED
        win32gui.SetWindowLong(hwnd, win32con.GWL_EXSTYLE, new_exstyle)
        win32gui.SetLayeredWindowAttributes(hwnd, colorkey, 255, win32con.LWA_COLORKEY)
        # draw polygon
        self.paint_hexagon()

    def paint_hexagon(self):
        self.delete("all")
        hexagon = []
        for i in range(SIDES_HEX):
            # calculating points
            angle_degrees = 60 * i - 30
            angle_radians = math.radians(angle_degrees)
            x = int((math.sqrt(3) * self.hex_size) / 2 + self.hex_size * math.cos(angle_radians))
            y = int(self.hex_size + self.hex_size * math.sin(angle_radians))
            hexagon.append((x, y))

        self.create_polygon(hexagon, outline="black", fill=self.color)

    # changing color of button
    def change_color(self, color):
        self.color = color
        self.paint_hexagon()

    def disabled_state(self):
        self.state = DISABLED

    def enabled_state(self):
        self.state = ENABLED

