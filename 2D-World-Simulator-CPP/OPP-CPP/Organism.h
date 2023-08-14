#pragma once
#include <iostream>
#include "World.h"
#include "Point.h"

#define ORGANISM_SIGN_SIZE 1

enum OrganismSpecie {
	HUMAN,
	WOLF,
	SHEEP,
	FOX,
	TURTLE,
	ANTELOPE,
	GRASS,
	SOW_THISTLE,
	GUARANA,
	BELLADONNA,
	SOSNOWSKYS_HOGWEED
};

class Point;
class World;

//abstract class of organism
class Organism {
public:
	virtual void action() = 0;
	virtual void collision(Organism* otherOrganism) = 0;
	virtual bool checkIfOrganismIsAnimal() = 0;
	virtual bool defence(Organism* attackingOrganism) = 0;

	static Organism* createNewOrganism(OrganismSpecie specie, World* world, Point point);
	static void createEveryOrganism(World* world);

	OrganismSpecie getSpecie() const;
	int getInitiative() const;
	int getStrength() const;
	bool getKilled() const;
	void setInitiative(int initiative);
	int getWhenBorn() const;
	char getOrganismSign() const;
	Point getOrganismPos() const;

	void setStrength(int strength);
	void setOrganismSpecie(OrganismSpecie specie);
	void setOrganismSign(char organismSign);
	void setKilled(bool killed);
	void setWhenBorn(int whenBorn);

	void saveOrganismToFile(FILE* file) const;
	void loadOrganismFromFile(FILE* file, World* newWorld);

	bool checkPointCorrenctness(int x, int y);
	Point getNearPoint(Point point, bool free);
	void draw();

protected:

	Organism(World* world, Point point);
	bool checkIfFieldIsFree(int x, int y);
	void moveToGivenField(Point newPoint);
	
	int strength = NULL;
	int initiative = NULL;
	int whenBorn = NULL;
	bool killed = false;

	World* organismWorld;
	Point* organismPos;
	OrganismSpecie specie;
	char organismSign;
	
};