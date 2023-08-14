#include "Wolf.h"

Wolf::Wolf(World* world, Point point) : Animal(world, point) {

	 this->setOrganismSign('W');
	 this->setStrength(9);
	 this->setInitiative(5);
	 this->setOrganismSpecie(OrganismSpecie::WOLF);

}