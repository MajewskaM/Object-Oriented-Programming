#include "Turtle.h"
#include "Commentator.h"

Turtle::Turtle(World* world, Point point) : Animal(world, point) {

	this->setOrganismSign('T');
	this->setStrength(2);
	this->setInitiative(1);
	this->setOrganismSpecie(OrganismSpecie::TURTLE);
}

//turtle has 75% chance to stay in the same place, so it may not move
void Turtle::action() {

	int chance = rand() % 10;

	if (chance > CHANCE_TO_STAY) {
		//just performing casual animal action
		Animal::action();
	}
	//else he just stays in the same place
	
}

//checking defence successfulness
bool Turtle::defence(Organism* attackingOrganism) {
	if (reflectAttack(attackingOrganism)) return true;
	return false;
}

//reflecting attack from an opponent
bool Turtle::reflectAttack(Organism* attackingOrganism) {
	
	//that means he defended himself from attacking organism
	if (attackingOrganism->getStrength() < DEFENCE_STRENGTH) {
		string newContent, organismSignString, otherSignString;
		organismSignString = getOrganismSign();
		otherSignString = attackingOrganism->getOrganismSign();
		newContent = organismSignString + " -> reflected attack from " + otherSignString;
		organismWorld->getCommentator()->addContent(newContent, getOrganismPos());
		return true;
	}
	return false;
}
