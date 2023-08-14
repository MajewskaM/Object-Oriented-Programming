#include "Plant.h"
#include "Commentator.h"

Plant::Plant(World* world, Point point)
	:Organism(world, point)
{}

//plant sowing with some probability
void Plant::action() {

	//calculating probability of sowing
	int chanceToSow = rand() % 10;
	if (chanceToSow < SOW_PROBABILITY) {
		sowPlant();
	}

}

//performing basic sow action for every organism
void Plant::sowPlant() {
	
	//new position for plant
	Point newPoint = getNearPoint(getOrganismPos(), true);

	//if there is no free field to spawn new plant
	if (newPoint == getOrganismPos()) return;

	Organism* newOrganism = createNewOrganism(specie, organismWorld, newPoint);
	organismWorld->addOrganismToWorld(newOrganism);

	string organismSignString, newContent;
	organismSignString = organismSign;
	newContent = organismSignString + " -> Was sowed (action)";
	organismWorld->getCommentator()->addContent(newContent, newOrganism->getOrganismPos());
}

//performing basic collision for plants -> most of the plants do not perform collision
void Plant::collision(Organism* otherOrganism) {}

//default defence action
bool Plant::defence(Organism* attackingOrganism) {
	return false;
}

bool Plant::checkIfOrganismIsAnimal() {
	return false;
}
