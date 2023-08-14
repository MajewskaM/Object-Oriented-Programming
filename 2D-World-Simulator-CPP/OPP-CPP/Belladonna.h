#pragma once
#include "Plant.h"

class BellaDonna : public Plant {
public:
	BellaDonna(World* world, Point point);
private:
	bool defence(Organism* attackingOrganism) override;
	bool killAnimal(Organism* attackingOrganism);
};