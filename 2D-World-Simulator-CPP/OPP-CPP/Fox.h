#pragma once
#include "Animal.h"

class Fox : public Animal {
public:
	Fox(World* world, Point point);
private:
	void action() override;
};