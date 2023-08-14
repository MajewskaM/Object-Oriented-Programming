#include "Human.h"
#include "Commentator.h"

Human::Human(World* world, Point point)
	:Animal(world, point)
{
	this->setOrganismSign('H');
	this->setStrength(5);
	this->setInitiative(4);
	this->setOrganismSpecie(OrganismSpecie::HUMAN);

}

void Human::setDirection(MoveDirection direction) {
	this->direction = direction;
}

//performing human move according to direction given by player
void Human::action()
{	
	int x = organismPos->getX(), y = organismPos->getY();
	Point newPoint;

	switch (direction) {
	case UP: newPoint = Point(x, y - ONE); break;
	case DOWN: newPoint = Point(x, y + ONE);break;
	case RIGHT: newPoint = Point(x + ONE, y); break;
	case LEFT: newPoint = Point(x - ONE, y); break;
	default: break;
	}

	Organism* newPos = organismWorld->getOrganismAtPos(newPoint);

	if (newPos != nullptr) {
		collision(newPos);
	}
	else {
		moveToGivenField(newPoint);
	}

	reduceCoolDownTime();
	if (ability.activated) {
		reduceActivation();
	}
}

//activating human ability
void Human::activateAbility() {
	
	if (ability.activated) {
		cout << "Human ability is already ACIVATED!" << endl;
		return;
	}

	if (ability.coolDownTime) {
		cout << "Sorry, we cannot activate Human's ability. You need to wait " << ability.coolDownTime << " round/s." << endl;
		return;
	}

	if (getStrength() >= POWER_RANGE) {
		cout << "ERROR! Human already has power at leat 10! " << endl;
		return;
	}
	ability.activated = true;
	ability.newStrength = POWER_RANGE - getStrength();

	//strength rises to 10
	setStrength(getStrength() + ability.newStrength);
	cout << "Ability ACTIVATED" << endl;
	organismWorld->getCommentator()->addContent("HUMAN ability ACTIVATED!", getOrganismPos());

}

//counting time left for activation
void Human::reduceActivation() {
	
	//changing human's strength
	if (ability.newStrength) {
		ability.newStrength--;
		setStrength(getStrength() - ONE);
	}
	else {
		//we need to switch off ability power
		//strength is == 5, the initial state -> so we do not have to change it
		organismWorld->getCommentator()->addContent("HUMAN ability DISACTIVATED!", getOrganismPos());
		ability.activated = false;
		ability.coolDownTime = COOL_DOWN_TIME;
	}
}

//reducing cool down time after activation of ability
void Human::reduceCoolDownTime() {
	if (ability.coolDownTime) ability.coolDownTime--;
}

//loading info about human abilities from file
void Human::loadHumanAbility(FILE * file) {

	fread(&ability.activated, sizeof(bool), 1, file);
	fread(&ability.coolDownTime, sizeof(int), 1, file);
	fread(&ability.newStrength, sizeof(int), 1, file);
}

//saving info about human abilities to file
void Human::saveHumanAbility(FILE* file) {

	fwrite(&ability.activated, sizeof(bool), 1, file);
	fwrite(&ability.coolDownTime, sizeof(int), 1, file);
	fwrite(&ability.newStrength, sizeof(int), 1, file);
}
