package op.JavaProject.Game;

import op.JavaProject.Functionality.BoardPoint;
import op.JavaProject.Functionality.Species;
import op.JavaProject.Organisms.Animals.*;
import op.JavaProject.Organisms.Plants.*;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

import static op.JavaProject.Game.OrganismsStatics.*;

public abstract class Organism implements Serializable {

    protected int strength;

    protected int initiative;
    protected int whenBorn;
    protected boolean killed;

    protected World organismWorld;
    protected BoardPoint organismPos;

    protected Species organismSpecie;

    protected String organismSign;

    protected Color organismColor;

    protected Organism(World organismWorld, BoardPoint point) {
        this.organismWorld = organismWorld;
        this.organismPos = new BoardPoint(point.GetX(), point.GetY());
        this.whenBorn = organismWorld.GetTurnNumber();
    }

    //moving organism to given position
    protected void MoveToGivenField(BoardPoint newPoint) {
        organismWorld.GetStatesOfFields()[organismPos.GetY()][organismPos.GetX()] = null;
        organismWorld.GetStatesOfFields()[newPoint.GetY()][newPoint.GetX()] = this;
        organismPos.SetX(newPoint.GetX());
        organismPos.SetY(newPoint.GetY());
    }

    //getting random field near organism, it may be free or not
    //point may stay the same -> f.e. if there is no place for spawning new organism
    public BoardPoint GetNearPoint(BoardPoint point, boolean free) {

        BoardPoint newPoint = new BoardPoint();
        int move, posX = point.GetX(), posY = point.GetY(), newPosX = posX, newPosY = posY;
        boolean correctPos = false;
        boolean leftCheck = false, rightCheck = false, upCheck = false, downCheck = false, upSecond = false, downSecond = false;
        int sides = SIDES, i = 0, checked = 0;
        boolean hex = organismWorld.GetIfHex();
        ;
        if (hex) sides = SIDES_HEX;
        do {
            if (((checked > (SIDES - 1)) && !hex) || ((checked > (SIDES_HEX - 1)) && hex)) break;
            Random randomValue = new Random();

            if (hex) {
                move = randomValue.nextInt(SIDES_HEX);
            } else {
                move = randomValue.nextInt(SIDES);
            }

            i = 0;
            newPosX = posX;
            newPosY = posY;

            if (!upCheck) {
                if (move == UP) {
                    newPosY = posY + 1;
                    upCheck = true;
                }
                i++;
            }

            if (!downCheck) {
                if (move == DOWN) {
                    newPosY = posY - 1;
                    downCheck = true;
                }
                i++;
            }

            if (!rightCheck) {
                if (move == RIGHT) {
                    newPosX = posX + 1;
                    rightCheck = true;
                }
                i++;
            }

            if (!leftCheck) {
                if (move == LEFT) {
                    newPosX = posX - 1;
                    leftCheck = true;
                }
                i++;
            }

            //if it is hexagonal world we also need to check additional fields
            if (hex) {
                if (!downSecond) {

                    if (move == SECOND_DOWN) {
                        if (newPosY % 2 == 0) {
                            newPosX = posX - 1;
                            newPosY = posY + 1;
                        } else {
                            newPosX = posX + 1;
                            newPosY = posY + 1;
                        }

                        downSecond = true;
                    }
                    i++;
                }

                if (!upSecond) {
                    if (move == SECOND_UP) {
                        if (newPosY % 2 == 0) {
                            newPosX = posX - 1;
                            newPosY = posY - 1;
                        } else {
                            newPosX = posX + 1;
                            newPosY = posY - 1;
                        }

                        upSecond = true;
                    }
                    i++;
                }
            }

            sides--;

            if (CheckPointCorrectness(newPosX, newPosY)) {
                if (free) {
                    //that means we checked all sides and there is no possible free side
                    if (leftCheck && rightCheck && upCheck && downCheck) {
                        if (hex && downSecond && upSecond) {
                            //no place around, point stays the same
                            break;
                        }
                        //point stays the same
                        if (!hex) break;
                    } else {
                        //we also need to check if the field is free (the point)
                        if (CheckIfFieldIsFree(newPosX, newPosY)) {
                            correctPos = true;
                        }

                    }
                } else {
                    correctPos = true;
                }
            }

        } while (!correctPos);

        if (correctPos) {
            newPoint.SetX(newPosX);
            newPoint.SetY(newPosY);
            return newPoint;
        } else {
            //no place around, point stays the same
            return organismPos;
        }

    }

    //checking if point is on the map
    public boolean CheckPointCorrectness(int x, int y) {
        if (y < organismWorld.GetHeight() && y >= 0) {
            if (x < organismWorld.GetWidth() && x >= 0) {
                return true;
            }
        }
        return false;
    }

    abstract public void Action();

    abstract public void Collision(Organism otherOrganism);

    abstract public boolean Defence(Organism attackingOrganism);

    abstract public boolean CheckIfOrganismIsAnimal();

    //checking if given field is not occupied by other organism
    protected boolean CheckIfFieldIsFree(int x, int y) {

        if (organismWorld.GetStatesOfFields()[y][x] != null) {
            return false;
        }
        return true;

    }

    public void SetOrganismWorld(World organismWorld) {
        this.organismWorld = organismWorld;
    }

    //printing organism on the grid
    public void Draw() {
        if (!organismWorld.GetIfHex()) {
            organismWorld.GetWorldFrame().boardPanel.board[organismPos.GetY()][organismPos.GetX()].setText(organismSign);
            organismWorld.GetWorldFrame().boardPanel.board[organismPos.GetY()][organismPos.GetX()].setEnabled(false);
            organismWorld.GetWorldFrame().boardPanel.board[organismPos.GetY()][organismPos.GetX()].setForeground(Color.BLACK);
            organismWorld.GetWorldFrame().boardPanel.board[organismPos.GetY()][organismPos.GetX()].setBackground(organismColor);
        } else {
            organismWorld.GetWorldFrame().boardPanelHex.hexButtons[organismPos.GetY()][organismPos.GetX()].changeColor(organismColor);
            organismWorld.GetWorldFrame().boardPanelHex.hexButtons[organismPos.GetY()][organismPos.GetX()].repaint();
            organismWorld.GetWorldFrame().boardPanelHex.hexButtons[organismPos.GetY()][organismPos.GetX()].setEnabled(false);
        }

    }

    public BoardPoint GetOrganismPos() {
        return organismPos;
    }

    //starting game function, creating every kind of organism once
    public static void CreateEveryOrganism(World organismWorld) {
        //Creating animals
        BoardPoint newPoint = organismWorld.RandomFreePoint();
        organismWorld.AddOrganismToWorld(new Wolf(organismWorld, newPoint));
        newPoint = organismWorld.RandomFreePoint();
        organismWorld.AddOrganismToWorld(new Sheep(organismWorld, newPoint));
        newPoint = organismWorld.RandomFreePoint();
        organismWorld.AddOrganismToWorld(new Fox(organismWorld, newPoint));
        newPoint = organismWorld.RandomFreePoint();
        organismWorld.AddOrganismToWorld(new Turtle(organismWorld, newPoint));
        newPoint = organismWorld.RandomFreePoint();
        organismWorld.AddOrganismToWorld(new Antelope(organismWorld, newPoint));

        //Creating plants
        newPoint = organismWorld.RandomFreePoint();
        organismWorld.AddOrganismToWorld(new Grass(organismWorld, newPoint));
        newPoint = organismWorld.RandomFreePoint();
        organismWorld.AddOrganismToWorld(new SowThistle(organismWorld, newPoint));
        newPoint = organismWorld.RandomFreePoint();
        organismWorld.AddOrganismToWorld(new Guarana(organismWorld, newPoint));
        newPoint = organismWorld.RandomFreePoint();
        organismWorld.AddOrganismToWorld(new Belladonna(organismWorld, newPoint));
        newPoint = organismWorld.RandomFreePoint();
        organismWorld.AddOrganismToWorld(new SosnowskysHogweed(organismWorld, newPoint));
    }

    //creating new organism depending on given specie and position
    public static Organism CreateNewOrganism(Species organismSpecie, World organismWorld, BoardPoint organismPoint) {
        switch (organismSpecie) {
            case WOLF:
                return new Wolf(organismWorld, organismPoint);
            case SHEEP:
                return new Sheep(organismWorld, organismPoint);
            case FOX:
                return new Fox(organismWorld, organismPoint);
            case HUMAN:
                return new Human(organismWorld, organismPoint);
            case ANTELOPE:
                return new Antelope(organismWorld, organismPoint);
            case GRASS:
                return new Grass(organismWorld, organismPoint);
            case SOW_THISTLE:
                return new SowThistle(organismWorld, organismPoint);
            case GUARANA:
                return new Guarana(organismWorld, organismPoint);
            case BELLADONNA:
                return new Belladonna(organismWorld, organismPoint);
            case SOSNOWSKYS_HOGWEED:
                return new SosnowskysHogweed(organismWorld, organismPoint);

            default:
                return null;
        }
    }

    public Species GetSpecie() {
        return organismSpecie;
    }

    public boolean GetKilled() {
        return killed;
    }

    public int GetInitiative() {
        return initiative;
    }

    public int GetWhenBorn() {
        return whenBorn;
    }

    public String GetOrganismSign() {
        return organismSign;
    }

    public int GetStrength() {
        return strength;
    }

    public void SetStrength(int strength) {
        this.strength = strength;
    }

    public void SetKilled(boolean killed) {
        this.killed = killed;
    }
}
