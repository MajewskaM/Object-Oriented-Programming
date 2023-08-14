#pragma once
#include "Animal.h"
#define NEW_STRENGTH 5
#define POWER_RANGE 10
#define COOL_DOWN_TIME 5

//srtict to keep information about human's ability
struct MagicalPotion {
	bool activated = false;
	int coolDownTime = NULL;
	int newStrength = NEW_STRENGTH;
};

class Human : public Animal {
	MoveDirection direction = NONE;
	MagicalPotion ability;
public:
	Human(World* world, Point point);
	//functions used in world class
	void loadHumanAbility(FILE* file);
	void saveHumanAbility(FILE* file);
	void activateAbility();
	void setDirection(MoveDirection direction);

private:
	
	void action() override;
	void reduceActivation();
	void reduceCoolDownTime();
};