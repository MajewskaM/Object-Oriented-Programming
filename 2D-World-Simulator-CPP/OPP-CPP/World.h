#pragma once
#include <iostream>
#include <vector>
#include "Organism.h"

#define DEFAULT_WORLD_WIDTH 15
#define DEFAULT_WORLD_HEIGHT 15
#define MAX_HEIGHT 30
#define MAX_WIDTH 50
#define ONE 1
#define ROWS 2
#define ENTER 0x0d
#define SIDES 4
#define MAX_FILL 0.50
#define ORGANISM_TYPES_NUM 11
#define MIN_SPECIES_NUMBER 12
#define UP_KEY 0x48
#define DOWN_KEY 0x50
#define RIGHT_KEY 0x4d
#define LEFT_KEY 0x4b

using namespace std;
	
//declarations of classes
class Organism;
class Point;
class Commentator;
class Animal;
class Human;

enum MoveDirection { UP, DOWN, RIGHT, LEFT, NONE };

//class that manages gameplay and whole life cycle of organisms
class World {
	int height;
	int width;
	int turnNumber = NULL;
	int occFields = NULL;
	int startFill = NULL;
	bool humanAlive = true;
	bool worldFull = false;
	Commentator* commentator;
	vector <Organism*> listOfOrganisms;
	vector <Organism*> toRemove;
	Organism*** statesOfFields = nullptr;
	Human* human;

	void sortOrganismVector();
	void removeOrganismsFromVector();
	void addToRemove(Organism* organism);
	void moveHuman();
	void clearWorld();

public:

	World();
	World(int height, int width);
	void fillWorldWithOrganisms();
	void createStates();
	void makeTurn();
	void drawWorld();

	void saveGame();
	void loadGame();
	
	int getWidth() const;
	int getHeight() const;
	int getTurnNumber() const;
	bool getHumanAlive() const;
	Commentator* getCommentator() const;
	bool getWorldFull() const;

	void setStartFill(int startFill);
	void setWidth(int width);
	void setHeight(int height);
	void setHumanAlive(bool humanAlive);

	Organism *getOrganismAtPos(Point point);
	Organism*** getStatesOfFields();
	void addOrganismToWorld(Organism* newOrganism);
	void deleteOrganism(Organism* organism);
	Point randomFreePoint();
	void startNewGame();
	~World();

};


