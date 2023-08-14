#pragma once
#include "World.h"

class World;

class Point {
	int x;
	int y;

public:
	Point();
	Point(int x, int y);
	Point(const Point& otherPoint);
	int getX() const;
	int getY() const;
	void setX(int x);
	void setY(int y);
	void randomPoint(World* world);
	bool operator==(const Point& otherPoint);
};
