#pragma once
#include "Plant.h"

class SosnowskysHogweed : public Plant {
public:
	SosnowskysHogweed(World* world, Point point);
private:
	void action() override;
	bool defence(Organism* attackingOrganism) override;
	bool killAnimal(Organism* attackingOrganism);
};
