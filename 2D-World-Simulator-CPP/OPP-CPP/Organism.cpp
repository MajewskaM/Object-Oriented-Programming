#include "Organism.h"
#include "Wolf.h"
#include "Sheep.h"
#include "Human.h"
#include "Fox.h"
#include "Turtle.h"
#include "Antelope.h"
#include "Grass.h"
#include "SowThistle.h"
#include "Guarana.h"
#include "BellaDonna.h"
#include "SosnowskysHogweed.h"

Organism::Organism(World* world, Point point) {
	this->organismWorld = world;
	this->organismPos = new Point(point.getX(), point.getY());
	this->whenBorn = world->getTurnNumber();
}

OrganismSpecie Organism::getSpecie() const {
	return specie;
}

int Organism::getWhenBorn() const {
	return whenBorn;
}

Point Organism::getOrganismPos() const {
	return *organismPos;
}

int Organism::getInitiative() const{ 
	return initiative; 
}

int Organism::getStrength() const{
	return strength;
}

char Organism::getOrganismSign() const {
	return organismSign;
}

bool Organism::getKilled() const {
	return killed;
}

void Organism::setWhenBorn(int whenBorn) {
	this->whenBorn = whenBorn;
}

void Organism::setInitiative(int initiative) {
	this->initiative = initiative;
}

void Organism::setStrength(int strength) {
	this->strength = strength;
}

void Organism::setOrganismSpecie(OrganismSpecie specie) {
	this->specie = specie;
}

void Organism::setOrganismSign(char organismSign) {
	this->organismSign = organismSign;
}

void Organism::setKilled(bool killed) {
	this->killed = killed;
}

//moving organism to given position
void Organism::moveToGivenField(Point newPoint) {
	organismWorld->getStatesOfFields()[organismPos->getY()][organismPos->getX()] = nullptr;
	organismWorld->getStatesOfFields()[newPoint.getY()][newPoint.getX()] = this;
	organismPos->setX(newPoint.getX());
	organismPos->setY(newPoint.getY());
}

//getting random field near organism, it may be free or not
//point may stay the same -> f.e. if there is no place for resping new organism
Point Organism::getNearPoint(Point point, bool free) {

	Point newPoint;
	int move, posX = point.getX(), posY = point.getY(), newPosX = posX, newPosY = posY;
	bool correctPos = false;
	bool leftCheck = false, rightCheck = false, upCheck = false, downCheck = false;
	int sides = SIDES, i = NULL;
	do {
		if (sides == NULL) break;
		move = rand() % sides;
		i = NULL;
		newPosX = posX;
		newPosY = posY;

		if (!rightCheck) {
			if (move == i) newPosX = posX + ONE, rightCheck = true;
			i++;
		}
		
		if (!leftCheck) {
			if (move == i) newPosX = posX - ONE, leftCheck = true;
			i++;
		}
		
		if (!upCheck) {
			if (move == i) newPosY = posY + ONE, upCheck = true;
			i++;
		}
		
		if (!downCheck) {
			if (move == i) newPosY = posY - ONE, downCheck = true;
			i++;
		}
		
		sides--;

		if (checkPointCorrenctness(newPosX, newPosY)) {
			if (free) {
				//that means we checked all sides and there is no possible free side
				if (leftCheck && rightCheck && upCheck && downCheck) {
					//no place around, point stays the same
					break;
				}
				else {
					//we also need to check if the field is free (the point)
					if (checkIfFieldIsFree(newPosX, newPosY)) {
						correctPos = true;
					}

				}
			}
			else {
				correctPos = true;
			}
		}

	} while (!correctPos);

	if (correctPos) {
		newPoint.setX(newPosX);
		newPoint.setY(newPosY);
		return newPoint;
	}
	else {
		//no place around, point stays the same
		return *organismPos;
	}


}

//checking if point is on the map
bool Organism::checkPointCorrenctness(int x, int y) {
	if (y < organismWorld->getHeight() && y >= NULL) {
		if (x < organismWorld->getWidth() && x >= NULL) {
			return true;
		}
	}
	return false;
}

//checking if given field is not occupied by other organism
bool Organism::checkIfFieldIsFree(int x, int y) {

	if (organismWorld->getStatesOfFields()[y][x] != nullptr) {
		return false;
	}
	return true;

}

//starting game function, creating every kind of organism once
void Organism::createEveryOrganism(World* world) {

	//creating animals
	Point newPoint = world->randomFreePoint();
	world->addOrganismToWorld(new Wolf(world, newPoint));
	newPoint = world->randomFreePoint();
	world->addOrganismToWorld(new Sheep(world, newPoint));
	newPoint = world->randomFreePoint();
	world->addOrganismToWorld(new Fox(world, newPoint));
	newPoint = world->randomFreePoint();
	world->addOrganismToWorld(new Turtle(world, newPoint));
	newPoint = world->randomFreePoint();
	world->addOrganismToWorld(new Antelope(world, newPoint));

	//creating plants
	newPoint = world->randomFreePoint();
	world->addOrganismToWorld(new Grass(world, newPoint));
	newPoint = world->randomFreePoint();
	world->addOrganismToWorld(new SowThistle(world, newPoint));
	newPoint = world->randomFreePoint();
	world->addOrganismToWorld(new Guarana(world, newPoint));
	newPoint = world->randomFreePoint();
	world->addOrganismToWorld(new BellaDonna(world, newPoint));
	newPoint = world->randomFreePoint();
	world->addOrganismToWorld(new SosnowskysHogweed(world, newPoint));
}

//creating new organism depending on given specie and position
Organism* Organism::createNewOrganism(OrganismSpecie specie, World* world, Point point) {
	switch (specie) {

	case WOLF: return new Wolf(world, point);
	case SHEEP: return new Sheep(world, point);
	case HUMAN: return new Human(world, point);
	case FOX: return new Fox(world, point);
	case TURTLE: return new Turtle(world, point);
	case ANTELOPE: return new Antelope(world, point);
	case GRASS: return new Grass(world, point);
	case SOW_THISTLE: return new SowThistle(world, point);
	case GUARANA: return new Guarana(world, point);
	case BELLADONNA: return new Guarana(world, point);
	case SOSNOWSKYS_HOGWEED: return new Guarana(world, point);

	default: return nullptr;
	}
}

//drawing organism sign
void Organism::draw() {
	cout << organismSign;
}

//saving info about organism to file
void Organism::saveOrganismToFile(FILE* file) const{

	int x = organismPos->getX(), y = organismPos->getY();
	fwrite(&specie, sizeof(OrganismSpecie), 1, file);
	fwrite(&x, sizeof(int), 1, file);
	fwrite(&y, sizeof(int), 1, file);
	fwrite(&organismSign, sizeof(char), 1, file);
	fwrite(&strength, sizeof(int), 1, file);
	fwrite(&initiative, sizeof(int), 1, file);
	fwrite(&whenBorn, sizeof(int), 1, file);
	fwrite(&killed, sizeof(bool), 1, file);

}

//loading info about organism from file
void Organism::loadOrganismFromFile(FILE* file, World* newWorld) {

	int bornRound = NULL;
	fread(&organismSign, sizeof(char), 1, file);
	fread(&strength, sizeof(int), 1, file);
	fread(&initiative, sizeof(int), 1, file);
	fread(&bornRound, sizeof(int), 1, file);
	setWhenBorn(bornRound);
	fread(&killed, sizeof(bool), 1, file);

}