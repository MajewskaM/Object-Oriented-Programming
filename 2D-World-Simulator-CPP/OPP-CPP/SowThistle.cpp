#include "SowThistle.h"

SowThistle::SowThistle(World* world, Point point) : Plant(world, point) {

	this->setOrganismSign('*');
	this->setStrength(0);
	this->setInitiative(0);
	this->setOrganismSpecie(OrganismSpecie::SOW_THISTLE);
}

//sowThistle has 3 attemps to sow in one round
void SowThistle::action() {
	int attemps = MAX_ATTEMPS;
	while (attemps--) {
		Plant::action();
	}
	
}