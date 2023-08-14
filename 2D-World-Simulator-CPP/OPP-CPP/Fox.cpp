#include "Fox.h"
#include "Commentator.h"

Fox::Fox(World* world, Point point) : Animal(world, point) {

	this->setOrganismSign('F');
	this->setStrength(3);
	this->setInitiative(7);
	this->setOrganismSpecie(OrganismSpecie::FOX);

}

//performing action for fox
void Fox::action() {

	Point newPoint = getNearPoint(this->getOrganismPos(),false);
	Organism* newPos = organismWorld->getOrganismAtPos(newPoint);
	string newContent, organismSignString, otherSignString;

	//fox is not moving when on desired position there is organism with which he may lose the fight
	if (newPos != nullptr) {
		if (newPos->getStrength() < strength) {
			collision(newPos);
		}
		else if (newPos->getStrength() == strength) {
			if (newPos->getWhenBorn() > whenBorn) {
				collision(newPos);
			}
		}
		else {
			organismSignString = organismSign;
			otherSignString = newPos->getOrganismSign();
			newContent = organismSignString + " smells " + otherSignString + "'s stink...";
			organismWorld->getCommentator()->addContent(newContent, getOrganismPos());
		}
	}
	else {
		moveToGivenField(newPoint);
	}

}