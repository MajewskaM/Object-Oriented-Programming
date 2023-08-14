#pragma once
#include "Animal.h"

#define CHANCE_TO_GETAWAY 5

class Antelope : public Animal {
public:
	Antelope(World* world, Point point);
private:
	void action() override;
	bool defence(Organism* attackingOrganism) override;
	bool getAway(Organism* attackingOrganism);
};