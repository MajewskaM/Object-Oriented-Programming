#include <cstdlib>
#include "Animal.h"
#include "Point.h"
#include "Organism.h"
#include "Commentator.h"

Animal::Animal(World* world, Point point)
	:Organism(world, point)
{
}

//animal moves to a randomly selected neighbourhood field or fight with other organism
void Animal::action() {
	
	//looking for near field close to our organism
	Point newPoint = getNearPoint(getOrganismPos(), false); 

	//checking collisions
	Organism* newPosition = organismWorld->getOrganismAtPos(newPoint);
	if (newPosition != nullptr) collision(newPosition);
	else moveToGivenField(newPoint);
	
}

//perform collision for given organism specie
void Animal::collision(Organism* otherOrganism) {

	//multiplication of two same species
	if (specie == otherOrganism->getSpecie()){
		multiplyOrganisms(otherOrganism);
	}
	else {

		//other organism may defend itself or perform some other action when something tries to eat it
		if (otherOrganism->defence(this)) return;
		
		string newContent, organismSignString, opponentSignString;

		//looking for the result of the fight depending on organisms strength
		if (strength >= otherOrganism->getStrength()) {
			Point newPoint = otherOrganism->getOrganismPos();
			organismSignString = otherOrganism->getOrganismSign();
			opponentSignString = getOrganismSign();
			organismWorld->deleteOrganism(otherOrganism);
			moveToGivenField(newPoint);
		}
		else {
			organismSignString = getOrganismSign();
			opponentSignString = otherOrganism->getOrganismSign();
			organismWorld->deleteOrganism(this);
		}
		//adding new content to commentator report
		newContent = organismSignString + " -> Was killed (collision) by " + opponentSignString;
		organismWorld->getCommentator()->addContent(newContent, otherOrganism->getOrganismPos());
	}
}

//default defence "move" for an animal
bool Animal::defence(Organism* attackingOrganism) {
	return false;
}

//multiplying organisms (of the same specie), adding new organism to near field
void Animal::multiplyOrganisms(Organism* otherOrganism) {
	Organism* newOrganism;
	Point newPoint = getNearPoint(this->getOrganismPos(), true);

	//that means we cannot spawn organism, there is no free space around
	if (newPoint == getOrganismPos()) {
		newPoint = otherOrganism->getNearPoint(otherOrganism->getOrganismPos(), true);

		//looking for free space around second organism
		if (newPoint == otherOrganism->getOrganismPos()) {
			return;
		}
		else {
			//there is no space around first organism but there is free space around second organism
			newOrganism = createNewOrganism(specie, organismWorld, newPoint);
			organismWorld->addOrganismToWorld(newOrganism);

		}
	}
	else {
		//there is free space around first organism
		newOrganism = createNewOrganism(specie, organismWorld, newPoint);
		organismWorld->addOrganismToWorld(newOrganism);
	} 

	string organismSignString, newContent;
	organismSignString = organismSign;
	//adding commentator report
	newContent = organismSignString + " -> Was born (multiplication)";
	organismWorld->getCommentator()->addContent(newContent, newOrganism->getOrganismPos());
}


bool Animal::checkIfOrganismIsAnimal() {
	return true;
}