#pragma once
#include "Plant.h"

#define MAX_ATTEMPS 3

class SowThistle : public Plant {
public:
	SowThistle(World* world, Point point);
private:
	void action() override;
};