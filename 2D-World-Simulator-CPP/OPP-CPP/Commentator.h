#pragma once
#include <iostream>
#include <vector>
#include "Point.h"
#define ONE 1

using namespace std;
class Point;

class Commentator {

	struct Report
	{
		string comment = "";
		Point* position = new Point();
	};
	vector<Report> textBox;

public:
	Commentator();
	void clearComments();
	void addContent(string newContent, Point organismPosition);
	void reportMessage();
};
