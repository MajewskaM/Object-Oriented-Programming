#include "Antelope.h"
#include "Commentator.h"

Antelope::Antelope(World* world, Point point) : Animal(world, point) {

	this->setOrganismSign('A');
	this->setStrength(4);
	this->setInitiative(4);
	this->setOrganismSpecie(OrganismSpecie::ANTELOPE);

}

//moving two fields instead of one
void Antelope::action() {
	
	Point initialPos = *organismPos;
	//getting new first point
	Point newPoint1 = getNearPoint(this->getOrganismPos(), false);
	
	//new second point should not be the initial position
	Point newPoint2;
	do {
		newPoint2 = getNearPoint(newPoint1, false);
	} while (newPoint2 == newPoint1 || newPoint2 == initialPos);

	Organism* newPos = organismWorld->getOrganismAtPos(newPoint2);

	//checking if there is other organism
	if (newPos != nullptr) {
		collision(newPos);
	}
	else {
		moveToGivenField(newPoint2);
	}

}

//giving info about antelope defence success
bool Antelope::defence(Organism* attackingOrganism) {
	if (getAway(attackingOrganism)) return true;
	return false;
}

//performing antelope defence function
bool Antelope::getAway(Organism* attackingOrganism) {
	string newContent, organismSignString;

	Point newPoint;
	int chance = rand() % 10;
	if (chance <= CHANCE_TO_GETAWAY) {
		//antelope runs away - moves to neighborhood free field
		newPoint = getNearPoint(getOrganismPos(), true);

		//no place to run, fight -> going back in collision function of parent class!
		if (getOrganismPos() == newPoint) return false;

		organismSignString = attackingOrganism->getOrganismSign();
		//that means antelope defended itself and runned away
		newContent = "ANTELOPE - > runned away from " + organismSignString;
		organismWorld->getCommentator()->addContent(newContent, getOrganismPos());
		return true;
	}
	return false;
}
