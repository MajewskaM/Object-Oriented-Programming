#include "World.h"
#include "Human.h"
#include "Organism.h"
#include "Point.h"
#include "Commentator.h"
#include <algorithm>
#include <conio.h>

World::World() {
	this->height = DEFAULT_WORLD_HEIGHT;
	this->width = DEFAULT_WORLD_WIDTH;
	this->commentator = new Commentator();
	this->human = nullptr;
	//default world fill
	startFill = floor(((MAX_FILL * height * width) - ONE) / ORGANISM_TYPES_NUM);
}

World::World(int width, int height) {
	this->width = width;
	this->height = height;
	this->commentator = new Commentator();
	this->human = nullptr;
	startFill = floor((MAX_FILL * height * width) - ONE / ORGANISM_TYPES_NUM);
}

void World::startNewGame() {
	createStates();
	fillWorldWithOrganisms();
	commentator->clearComments();
	this->turnNumber = 1;
	this->humanAlive = true;
	this->occFields = NULL;
}

int World::getTurnNumber() const {
	return turnNumber;
}

bool World::getHumanAlive() const {
	return humanAlive;
}

int World::getWidth() const {
	return width;
}

int World::getHeight() const {
	return height;
}

bool World::getWorldFull() const {
	return worldFull;
}

Commentator* World::getCommentator() const {
	return commentator;
}

Organism*** World::getStatesOfFields() {
	return statesOfFields;
}

void World::setStartFill(int startFill) {
	this->startFill = startFill;
}

void World::setWidth(int width) {
	this->width = width;
}

void World::setHeight(int height) {
	this->height = height;
}

void World::setHumanAlive(bool humanAlive) {
	this->humanAlive = humanAlive;
}

//getting organism at given position
Organism* World::getOrganismAtPos(Point point) {

	return statesOfFields[point.getY()][point.getX()];
}

//release memory and clear vector
void World::clearWorld() {
	for (int i = 0; i < height; i++) {
		delete[] statesOfFields[i];
	}
	delete statesOfFields;

	listOfOrganisms.clear();
}

void World::createStates(){

	statesOfFields = new Organism * *[height];
	for (int i = NULL; i < height; i++) {
		statesOfFields[i] = new Organism * [width];
	}
	for (int i = NULL; i < height; i++) {
		for (int j = NULL; j < width; j++) {
			statesOfFields[i][j] = nullptr;
		}
	}
}

//filling world with organisms
void World::fillWorldWithOrganisms() {
	listOfOrganisms.clear();
	
	//always at the begining we need to add one human
	Organism* newHuman = Organism::createNewOrganism(HUMAN, this, randomFreePoint());
	human = (Human*)newHuman;
	addOrganismToWorld(newHuman);

	//startFill - how many time we want to add each organism
	for (int i = NULL; i < startFill; i++) {
		Organism::createEveryOrganism(this);
	}
}

//adding organism to world
void World::addOrganismToWorld(Organism* newOrganism) {
	int x = newOrganism->getOrganismPos().getX();
	int y = newOrganism->getOrganismPos().getY();
	listOfOrganisms.push_back(newOrganism);
	statesOfFields[y][x] = newOrganism;
}

//removing objects according to remove vector
void World::removeOrganismsFromVector() {

	for (size_t i = 0; i < toRemove.size(); i++) {
		vector<Organism*>::iterator orgPosition = find(listOfOrganisms.begin(), listOfOrganisms.end(), toRemove[i]);
		if (orgPosition != listOfOrganisms.end()) {
			listOfOrganisms.erase(orgPosition);
		}
	}
	toRemove.clear();

	
}

//adding organisms to remove to remove vector
void World::addToRemove(Organism* organism) {
	toRemove.push_back(organism);
}

//deleting organism
void World::deleteOrganism(Organism* organism) {

	int x = organism->getOrganismPos().getX();
	int y = organism->getOrganismPos().getY();
	statesOfFields[y][x] = nullptr;
	organism->setKilled(true);
	if (organism->getSpecie() == HUMAN) setHumanAlive(false);
	addToRemove(organism);

}

//returns Point(-1, -1) if there is no free space
Point World::randomFreePoint()
{	
	Point newPoint;
	int x, y;
	for (int i = NULL; i < height; i++) {
		for (int j = NULL; j < width; j++) {

			if (statesOfFields[i][j] == nullptr) {
				do{
					
					newPoint.randomPoint(this);
					x = newPoint.getX();
					y = newPoint.getY();
				} while (statesOfFields[y][x] != nullptr);
				return newPoint;
			}
		}
	}
	return Point(-1, -1);
}

//performing human move
void World::moveHuman() {
	cout << "Press ENTER - to activate Human's ability or just choose direction of human's move using arrow keys." << endl;
	int key, posX = human->getOrganismPos().getX(), posY = human->getOrganismPos().getY(), newPosX, newPosY;
	MoveDirection direction = NONE;

	key = _getch();
	if (key == ENTER) {
		human->activateAbility();
	}

	while (direction == NONE) {
		newPosX = posX;
		newPosY = posY;

		key = _getch();

		if (key == UP_KEY)
		{
			newPosY--;
			direction = UP;
		}
		else if (key == DOWN_KEY)
		{
			newPosY++;
			direction = DOWN;
		}
		else if (key == RIGHT_KEY)
		{
			newPosX++;
			direction = RIGHT;
		}
		else if (key == LEFT_KEY)
		{
			newPosX--;
			direction = LEFT;
		}

		if (human->checkPointCorrenctness(newPosX, newPosY) && direction != NONE) {
			human->setDirection(direction);
		}
		else {
			cout << "CANNOT MOVE IN THIS DIRECTION!" << endl;
			direction = NONE;
		}
	}
}


//letting all animals perform their action
void World::makeTurn() {

	//looking whether the world is full
	if (listOfOrganisms.size() == width * height) {
		worldFull = true;
		return;
	}

	commentator->clearComments();
	if(getHumanAlive()) moveHuman();
	turnNumber++;

	//sort organisms to decide which one can move as a first one
	sortOrganismVector();
	
	//perform action of each organism using sorted vector
	for (size_t i = 0; i < listOfOrganisms.size(); i++) {
		//to not perform action on newly born organisms or death organisms
		if (listOfOrganisms[i]->getWhenBorn() != turnNumber && !listOfOrganisms[i]->getKilled()) {
			listOfOrganisms[i]->action();
		}
	}

	removeOrganismsFromVector();
	
}

//sorting vector in descending order
void World::sortOrganismVector() {
	sort(listOfOrganisms.begin(), listOfOrganisms.end(),
		[](Organism* a, Organism* b) {
			int initA = a->getInitiative();
			int initB = b->getInitiative();
			if (initA != initB) {
				return initA > initB;
			}
			else {
				//initiatives are equal
				int ageA = a->getWhenBorn();
				int ageB = b->getWhenBorn();
				return ageA < ageB;
			}
			
		});
}

//drawing the world with current states
void World::drawWorld() {

	system("cls");
	cout << "PLAYING GAME..." << endl;
	cout << "------------------------" << endl;
	cout << "Turn Number: " << getTurnNumber() << endl;
	cout << "press 'N' - Start New Game\tpress 'Q' - Quit\t" << endl;
	cout << "press 'SPACE' - Make a Turn " << endl;
	cout << endl;
	
	//drawing world frame
	for (int i = NULL; i < ROWS; i++) {
		if (i == NULL) {
			cout << " X";
			for (int j = NULL; j < width; j++) cout << " X";
			cout << " X";
			cout << endl;
		}
		else {

			for (int j = NULL; j < width + ROWS; j++) {
				if (j == NULL) cout << "Y ";
				else cout << "  ";
			}
			cout << "Y";
		}
	}
	cout << endl;

	//drawing organisms
	for (int i = NULL; i < height; i++) {
		cout << "Y ";
		for (int j = NULL; j < width; j++) {
			cout << ' ';
			if (statesOfFields[i][j] != nullptr) statesOfFields[i][j]->draw();
			else cout << "_";
		}
		cout << "  Y";
		cout << endl;
	}
	
	//drawing world frame
	for (int i = NULL; i < ROWS; i++) {
		if (i == NULL) {
			for (int j = NULL; j < width + ROWS; j++) {
				if (j == NULL) cout << "Y ";
				else cout << "  ";
			}
			cout << "Y" << endl;;
		}
		else {
			cout << " X";
			for (int j = NULL; j < width; j++) cout << " X";
			cout << " X";
		}
	}

	cout << endl;
	cout << endl;

	//drawing reports from game
	cout << "\t --------------" << endl;
	cout << "\t|   TEXT BOX   | " << endl;
	cout << "\t --------------" << endl;
	commentator->reportMessage();

}

//loading game from file
void World::loadGame() {

	string fileName;
	cout << "Please enter name of file to load: " << endl;

	do {
		cin >> fileName;

		size_t size = fileName.length();
		char* charArray = new char[size + ONE];
		for (size_t i = 0; i < size; i++) {
			charArray[i] = fileName[i];
		}
		charArray[size] = '\0';

		//open file using fopen_s
		FILE* file = nullptr;
		errno_t err = fopen_s(&file, charArray, "r");
		delete[] charArray;

		if (err != NULL || file == nullptr) {
			cout << "We couldn't open the file. Please try again." << endl;
			cout << "Please enter file name : " << endl;
		}
		else {

			fread(&height, sizeof(int), 1, file);
			fread(&width, sizeof(int), 1, file);
			fread(&turnNumber, sizeof(int), 1, file);
			fread(&occFields, sizeof(int), 1, file);
			fread(&startFill, sizeof(int), 1, file);
			fread(&humanAlive, sizeof(bool), 1, file);
			//cleaning previous world instance
			listOfOrganisms.clear();

			int size, x, y;
			fread(&size, sizeof(int), 1, file);

			createStates();

			//loading each organism
			for (int i = NULL; i < size; i++) {

				OrganismSpecie newSpecie;
				fread(&newSpecie, sizeof(OrganismSpecie), 1, file);
				fread(&x, sizeof(int), 1, file);
				fread(&y, sizeof(int), 1, file);
				Point newPoint;
				newPoint.setX(x);
				newPoint.setY(y);
				Organism* newOrganism = Organism::createNewOrganism(newSpecie, this, newPoint);

				newOrganism->loadOrganismFromFile(file, this);

				//human needs to load additional information about his ability
				if (newSpecie == HUMAN) {
					human = (Human*)newOrganism;
					human->loadHumanAbility(file);

				}
				addOrganismToWorld(newOrganism);
			}
			fclose(file);
			break;

		}

	} while (true);
}

//saving game to file
void World::saveGame() {

	string fileName;
	cout << "Plese enter file name:" << endl;
	do {
		cin >> fileName;

		size_t size = fileName.length();
		char* charArray = new char[size + ONE];
		for (size_t i = 0; i < size; i++) {
			charArray[i] = fileName[i];
		}
		charArray[size] = '\0';

		//crating a file with saved current game status
		//open file using fopen_s
		FILE* file = nullptr;
		errno_t err = fopen_s(&file, charArray, "wb");
		delete[] charArray;

		if (err != 0 || file == nullptr) {
			cout << "We couldn't open the file. Please try again." << endl;
			cout << "Please enter file name : " << endl;
		}
		else {

			fwrite(&height, sizeof(int), 1, file);
			fwrite(&width, sizeof(int), 1, file);
			fwrite(&turnNumber, sizeof(int), 1, file);
			fwrite(&occFields, sizeof(int), 1, file);
			fwrite(&startFill, sizeof(int), 1, file);
			fwrite(&humanAlive, sizeof(bool), 1, file);
			int size = listOfOrganisms.size();
			fwrite(&size, sizeof(int), 1, file);

			for (const auto& organism : listOfOrganisms) {
				organism->saveOrganismToFile(file);
				//saving additional information about human
				if (organism->getSpecie() == HUMAN) {
					human->saveHumanAbility(file);
				}

			}
			fclose(file);
			break;

		}

	} while (true);

}

//world destructor
World::~World() {
	clearWorld();
}