#pragma once
#include <iostream>
#include <cstdlib>
#include "World.h"
#include "Organism.h"

#define ONE 1

//abstract class of organism
class Animal: public Organism {

protected:

	Animal(World* world, Point point);
	//basic functions for animal
	void action() override;
	void collision(Organism* otherOrganism) override;
	bool defence(Organism* attackingOrganism) override;
	bool checkIfOrganismIsAnimal() override;
	void multiplyOrganisms(Organism* otherOrganism);

};