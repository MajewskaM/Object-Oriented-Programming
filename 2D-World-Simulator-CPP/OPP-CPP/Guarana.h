#pragma once
#include "Plant.h"
#define ADDITIONAL_STRENGTH 3

class Guarana : public Plant {
public:
	Guarana(World* world, Point point);
private:
	bool defence(Organism* attackingOrganism) override;
	bool increaseStrength(Organism* attackingOrganism);
};