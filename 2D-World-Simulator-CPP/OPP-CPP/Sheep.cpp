#include "Sheep.h"

Sheep::Sheep(World* world, Point point) : Animal(world, point) {

	this->setOrganismSign('S');
	this->setStrength(4);
	this->setInitiative(4);
	this->setOrganismSpecie(OrganismSpecie::SHEEP);

}