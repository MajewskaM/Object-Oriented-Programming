#pragma once
#include <iostream>
#include <cstdlib>
#include "World.h"
#include "Organism.h"

#define SOW_PROBABILITY 1

//abstract class plant
class Plant : public Organism {

protected:

	Plant(World* world, Point point);
	void action() override;
	void collision(Organism* otherOrganism) override;
	bool defence(Organism* attackingOrganism) override;
	void sowPlant();
	bool checkIfOrganismIsAnimal() override;
};