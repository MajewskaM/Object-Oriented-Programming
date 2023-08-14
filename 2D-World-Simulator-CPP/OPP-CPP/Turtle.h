#pragma once
#include "Animal.h"

#define CHANCE_TO_STAY 6
#define DEFENCE_STRENGTH 5

class Turtle : public Animal {
public:
	Turtle(World* world, Point point);
private:
	void action() override;
	bool defence(Organism* attackingOrganism) override;
	bool reflectAttack(Organism* attackingOrganism);
};