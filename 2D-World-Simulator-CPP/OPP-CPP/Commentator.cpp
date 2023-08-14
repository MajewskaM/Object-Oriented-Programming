#include "Commentator.h"
#include "Point.h"

Commentator::Commentator() {
	clearComments();
}

//cleaning vector
void Commentator::clearComments() {
	textBox.clear();
}

//adding new content to report vector
void Commentator::addContent(string newContent, Point organismPosition) {

	Report currentReport;
	currentReport.comment = newContent + ", Organism Position:";
	currentReport.position = new Point(organismPosition);
	textBox.push_back(currentReport);
}

//showing the content of a vector
void Commentator::reportMessage() {

	for (size_t i = NULL; i < textBox.size(); i++) {
		cout << textBox[i].comment << " [" << textBox[i].position->getX() + ONE << ", " << textBox[i].position->getY() + ONE << "]" << endl;
	}
}


