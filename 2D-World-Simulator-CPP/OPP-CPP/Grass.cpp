#include "Grass.h"

Grass::Grass(World* world, Point point) : Plant(world, point) {

	this->setOrganismSign('#');
	this->setStrength(0);
	this->setInitiative(0);
	this->setOrganismSpecie(OrganismSpecie::GRASS);

}