import tkinter as tk

from Functionality.BoardPoint import BoardPoint
from Functionality.FunctionalityStatics import *
from Game.OrganismStatics import OrganismSpecie


# new window to choose an animal to put
def button_clicked(window, x, y, new_world, state=None):
    if state is not None:
        if new_world.world_window.button_grid[x][y].state == DISABLED:
            return
    mouse_x = window.winfo_pointerx()
    mouse_y = window.winfo_pointery()
    list_of_animals = tk.Toplevel()
    list_of_animals.geometry(f"+{mouse_x}+{mouse_y}")
    list_of_animals.title("List of Animals")

    animal_buttons = [
        ("Wolf", OrganismSpecie.WOLF),
        ("Sheep", OrganismSpecie.SHEEP),
        ("Fox", OrganismSpecie.FOX),
        ("Turtle", OrganismSpecie.TURTLE),
        ("Antelope", OrganismSpecie.ANTELOPE),
        ("Grass", OrganismSpecie.GRASS),
        ("Sow Thistle", OrganismSpecie.SOW_THISTLE),
        ("Guarana", OrganismSpecie.GUARANA),
        ("Belladonna", OrganismSpecie.BELLADONNA),
        ("Sosnowsky's Hogweed", OrganismSpecie.SOSNOWSKYS_HOGWEED),
        ("Cyber-Sheep", OrganismSpecie.CYBER_SHEEP)
    ]

    for animal_name, specie in animal_buttons:
        button = tk.Button(list_of_animals, text=animal_name)
        button.pack()
        button.config(
            command=lambda specie=specie: animal_button_clicked(specie, x, y, list_of_animals, new_world))


# adding organism according to chosen specie
def animal_button_clicked(specie, x, y, list_of_animals, new_world):
    organism = new_world.create_new_organism(specie, BoardPoint(y, x))
    new_world.add_organism_to_world(organism)
    list_of_animals.destroy()
    new_world.draw_world()
