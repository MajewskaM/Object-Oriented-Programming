#include "SosnowskysHogweed.h"
#include "Commentator.h"

SosnowskysHogweed::SosnowskysHogweed(World* world, Point point) : Plant(world, point) {

	this->setOrganismSign('Y');
	this->setStrength(10);
	this->setInitiative(0);
	this->setOrganismSpecie(OrganismSpecie::SOSNOWSKYS_HOGWEED);
}

//kill every animal in neighborhood cells
void SosnowskysHogweed::action() {
	
	int x = organismPos->getX(), y = organismPos->getY();
	Point newPoint;
	Organism* tmpOrganism;
	string newContent, organismSignString;

	for (int i = NULL; i < SIDES; i++) {

		switch (i) {
		case UP: newPoint = Point(x, y - 1); break;
		case DOWN: newPoint = Point(x, y + 1);break;
		case RIGHT: newPoint = Point(x + 1, y); break;
		case LEFT: newPoint = Point(x - 1, y); break;
		default: break;
		}
		
		if (checkPointCorrenctness(newPoint.getX(), newPoint.getY())) {
			tmpOrganism = organismWorld->getOrganismAtPos(newPoint);
			//we cannot kill plants!
			if (tmpOrganism != nullptr && tmpOrganism->checkIfOrganismIsAnimal()) {
				organismSignString = tmpOrganism->getOrganismSign();
				newContent = organismSignString + " died by SOSNOWSKYHOGWEED!";
				organismWorld->getCommentator()->addContent(newContent, tmpOrganism->getOrganismPos());
				organismWorld->deleteOrganism(tmpOrganism);
			}

		}
	}

	//afterwards just perform sowing
	Plant::action();

}

//checking defence successfulness
bool SosnowskysHogweed::defence(Organism* attackingOrganism) {
	if (killAnimal(attackingOrganism)) return true;
	return false;
}

//kill every animal that eats it
bool SosnowskysHogweed::killAnimal(Organism* attackingOrganism) {
	return true;
}