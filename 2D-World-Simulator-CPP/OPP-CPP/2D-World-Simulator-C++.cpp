#include <iostream>
#include "World.h"
#include <Windows.h>
#include <conio.h>
#include <ctime>
#include <string>
#include<stdio.h>

#define SPACE ' '
#define QUIT 'Q'
#define NEW_GAME 'N'
#define CHANGE_SIZE 'C'
#define SAVE 'S'
#define LOAD 'L'

using namespace std;

enum state { START_GAME, PLAYING_GAME, GAME_OVER, END_OF_GAME, SAVE_GAME, LOAD_GAME };

void performPlayingGame(World* world, state* currState);
void prepareGame(World* world, state* currState);
void saveGameToFile(World* world, state* currentState);
void loadGameFromFile(World* world, state* currentState);

int main()
{   
    srand(time(NULL));
    //game displays in full screen windowed mode
    SetConsoleTitle("2D WORLD SIMULATOR");
    ShowWindow(GetConsoleWindow(), SW_MAXIMIZE);
    state currentState = START_GAME;
    World* currentWorld = new World();

    while (currentState != END_OF_GAME) {
        switch (currentState) {
        case START_GAME:
            prepareGame(currentWorld, &currentState);
            break;
        case PLAYING_GAME:
            performPlayingGame(currentWorld, &currentState);
            break;
        case END_OF_GAME:
            break;
        case SAVE_GAME:
            saveGameToFile(currentWorld, &currentState);
            break;
        case LOAD_GAME:
            loadGameFromFile(currentWorld, &currentState);
            break;
        default:
            currentState = END_OF_GAME;
            break;
        }
    }
    
}

//printing default start screen
void printStartScreen(World* world) {
    system("cls");
    cout << "WELCOME!" << endl;
    cout << "------------------------" << endl;
    cout << "Current World Size: " << world->getWidth() << "x" << world->getHeight() << endl;
    cout << "press N - Start New Game | press Q - Quit | press C - Change World Size | press S - Save Game | press L - Load Game" << endl;
}

//preparing game world
void prepareGame(World* world, state* currState) {
    
    char key;
    int newWidth = NULL, newHeight = NULL;
    while (*currState == START_GAME) {
        printStartScreen(world);
        key = _getch();

        if (toupper(key) == QUIT) {
            *currState = END_OF_GAME;
        }
        else if (toupper(key) == NEW_GAME) {

            //calculate maximum filling, maximum number of organisms for current world
            int maxFill = world->getHeight() * world->getWidth() * MAX_FILL;
            int maxNumOfOrganisms = floor((maxFill - ONE) / ORGANISM_TYPES_NUM), times = ONE;

            while (true) {
                cout << "Enter how many times you want to add EVERY type of organism to a game (there are " << ORGANISM_TYPES_NUM <<
                    " organisms in game + ONLY ONE HUMAN!)" << endl;
                cout << "Maximum number of each organism: " << maxNumOfOrganisms << endl;
 
                cin >> times;
                if (times <= maxNumOfOrganisms) {
                    break;
                }
                system("cls");
                cout << "Please try again." << endl;

            }
            cout << "There will be added every kind of organism "<< times <<"x times to a game." << endl;
            world->setStartFill(times);
            cout << "Press Any key to continue" << endl;
            _getch();
            world->startNewGame();
            *currState = PLAYING_GAME;
            
        }
        else if (toupper(key) == CHANGE_SIZE) {
            
            while(true) {
                cout << "Set new width: " << endl;
                cin >> newWidth;

                cout << "Set new height: " << endl;
                cin >> newHeight;

                if (newWidth <= MAX_WIDTH && newHeight <= MAX_HEIGHT) {
                    int area = floor(newWidth * newHeight * MAX_FILL);

                    //area must be at least 12 -> we have 11 organisms & one human
                    if (area >= MIN_SPECIES_NUMBER) break;
                    else {
                        cout << "Sorry, we cannot put minimum number of organisms in the world! Try bigger size of Wordl!" << endl;
                    }
                }

                cout << "ERROR -> Max Width is: " << MAX_WIDTH << ", Max Hieght is: " << MAX_HEIGHT << " Please try again." << endl;
            }
            
            world->setWidth(newWidth);
            world->setHeight(newHeight);
        }
        else if (toupper(key) == SAVE) {
            cout << "Start A Game First!" << endl;
            cout << "Press Any Button to Continue..." << endl;
            _getch();

        }
        else if (toupper(key) == LOAD) {
            *currState = LOAD_GAME;
        }
    }
    
}

//reading user's request and changing the state of game
void performPlayingGame(World* world, state* currState) {
    char key;
    while (*currState == PLAYING_GAME) {

        if (world->getWorldFull()) {
            cout << "GAME OVER" << endl;
            cout << "THE WORLD IS FULL" << endl;
            cout << "press N - New Game\t press Q - QUIT" << endl;
        }
        else {
            world->drawWorld();
        }
        
        key = _getch();
        if (key == SPACE && !world->getWorldFull()) {
            
            world->makeTurn();
            
        }
        else if (toupper(key) == QUIT) {
            *currState = END_OF_GAME;
        }
        else if (toupper(key) == NEW_GAME) {
            *currState = START_GAME;
        }
        else if (toupper(key) == SAVE) {
            *currState = SAVE_GAME;
        }
        else if (toupper(key) == LOAD) {
            *currState = LOAD_GAME;
        }

    }
}

//saving game to file
void saveGameToFile(World* world, state* currentState) {

    world->saveGame();
    *currentState = PLAYING_GAME;
}

//loading game from file
void loadGameFromFile(World* world, state* currentState) {

    world->loadGame();
    *currentState = PLAYING_GAME;
}
    

