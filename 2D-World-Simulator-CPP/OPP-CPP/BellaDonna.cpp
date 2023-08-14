#include "BellaDonna.h"
#include "Commentator.h"

BellaDonna::BellaDonna(World* world, Point point) : Plant(world, point) {
	this->setOrganismSign('O');
	this->setStrength(99);
	this->setInitiative(0);
	this->setOrganismSpecie(OrganismSpecie::BELLADONNA);
}

//checking defence successfulness
bool BellaDonna::defence(Organism* attackingOrganism) {

	if (killAnimal(attackingOrganism)) return true;
	return false;
}

//performing defence -> killing animal
bool BellaDonna::killAnimal(Organism* attackingOrganism) {
	string newContent, organismSignString;

	organismSignString = string(1, attackingOrganism->getOrganismSign());
	organismWorld->deleteOrganism(attackingOrganism);
	newContent = organismSignString + " died by eating BELLADONNA!";
	organismWorld->getCommentator()->addContent(newContent, getOrganismPos());
	return true;
}