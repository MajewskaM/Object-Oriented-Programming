#include "Point.h"
#include <cstdlib>

Point::Point() {
	x = NULL;
	y = NULL;
}

Point::Point(int x, int y) {
	this->x = x;
	this->y = y;
}

Point::Point(const Point& otherPoint) {
	x = otherPoint.x;
	y = otherPoint.y;
}

int Point::getX() const {
	return x;
}

int Point::getY() const {
	return y;
}

void Point::setX(int x) {
	this->x = x;
}

void Point::setY(int y) {
	this->y = y;
}

//generating random point within world area
void Point::randomPoint(World* world) {
	int randX = rand() % world->getWidth();
	int randY = rand() % world->getHeight();
	setX(randX);
	setY(randY);
}

//handling comparision operator
bool Point::operator==(const Point& otherPoint) { 
	return (x == otherPoint.x && y == otherPoint.y); 
}

