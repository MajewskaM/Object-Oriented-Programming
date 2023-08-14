#include "Guarana.h"
#include "Commentator.h"

Guarana::Guarana(World* world, Point point) : Plant(world, point) {

	this->setOrganismSign('+');
	this->setStrength(0);
	this->setInitiative(0);
	this->setOrganismSpecie(OrganismSpecie::GUARANA);

}

//checking defence successfulness
bool Guarana::defence(Organism* attackingOrganism) {

	if (increaseStrength(attackingOrganism)) return true;
	return false;
}

//performing defence of guarana -> adding strength to attacking organism
bool Guarana::increaseStrength(Organism* attackingOrganism) {
	string newContent, organismSignString;
	organismSignString = attackingOrganism->getOrganismSign();
	int currentStrength = attackingOrganism->getStrength();
	attackingOrganism->setStrength(currentStrength + ADDITIONAL_STRENGTH);
	newContent = organismSignString + " gained +3 STRENGTH by eating GUARANA!";
	organismWorld->getCommentator()->addContent(newContent, getOrganismPos());

	//plant must be eaten anyway -> means we come back to collision()
	return false;
}