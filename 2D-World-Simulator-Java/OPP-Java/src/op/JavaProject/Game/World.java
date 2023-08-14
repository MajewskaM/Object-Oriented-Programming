package op.JavaProject.Game;

import op.JavaProject.Functionality.BoardPoint;
import op.JavaProject.Functionality.GameFrame;
import op.JavaProject.Functionality.Species;
import op.JavaProject.Organisms.Animals.Human;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import static op.JavaProject.Functionality.Species.HUMAN;
import static op.JavaProject.Game.OrganismsStatics.NO_POINT;
import static op.JavaProject.Game.OrganismsStatics.START_FILL;

public class World implements Serializable {
    private final int width;
    private final int height;
    private final int startFill;
    private boolean humanAlive;
    private int turnNumber;
    private boolean ifHex;
    private transient GameFrame worldFrame;
    private transient Commentator worldCommentator;
    private Human worldHuman;
    private ArrayList<Organism> listOfOrganisms = new ArrayList<>();
    private ArrayList<Organism> toRemove = new ArrayList<>();
    private Organism[][] statesOfFields;

    public World(World world, GameFrame frame) {
        this.height = world.height;
        this.width = world.width;
        this.startFill = START_FILL;
        this.humanAlive = world.humanAlive;
        this.turnNumber = world.turnNumber;
        this.worldCommentator = new Commentator();
        this.listOfOrganisms = world.listOfOrganisms;
        this.statesOfFields = world.statesOfFields;
        this.worldHuman = world.worldHuman;
        this.worldFrame = frame;
        this.ifHex = world.ifHex;
    }

    public World(int width, int height, GameFrame frame, boolean ifHex) {
        this.height = height;
        this.width = width;
        this.startFill = START_FILL;
        this.humanAlive = true;
        this.ifHex = ifHex;
        this.turnNumber = 1;
        this.worldFrame = frame;
        this.worldCommentator = new Commentator();

        this.statesOfFields = new Organism[this.height][this.width];

        for (int i = 0; i < this.height; ++i) {
            for (int j = 0; j < this.width; ++j) {
                this.statesOfFields[i][j] = null;
            }
        }

        //always at the beginning we need to add one human
        Organism newHuman = Organism.CreateNewOrganism(HUMAN, this, RandomFreePoint());
        this.worldHuman = (Human) newHuman;
        AddOrganismToWorld(newHuman);

        //startFill - how much times we want to add each organism
        for (int i = 0; i < startFill; i++) {
            Organism.CreateEveryOrganism(this);
        }

    }

    //adding organism to world
    public void AddOrganismToWorld(Organism newOrganism) {
        if (newOrganism != null) {
            int x = newOrganism.GetOrganismPos().GetX();
            int y = newOrganism.GetOrganismPos().GetY();
            listOfOrganisms.add(newOrganism);
            statesOfFields[y][x] = newOrganism;
        }

    }

    //generating random new point
    public BoardPoint RandomFreePoint() {
        BoardPoint newPoint = new BoardPoint();
        int x, y;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                if (statesOfFields[i][j] == null) {
                    do {

                        newPoint.SetRandomPoint(this);
                        x = newPoint.GetX();
                        y = newPoint.GetY();
                    } while (statesOfFields[y][x] != null);
                    return newPoint;
                }
            }
        }
        return new BoardPoint(NO_POINT, NO_POINT);
    }


    //in each turn all animals perform their actions in specified order
    public void MakeTurn() {

        worldCommentator.cleanComments();
        turnNumber++;
        //we need to sort organism, to decide which one can move as a first one
        SortOrganisms();

        //now using the vector we perform action of each organism
        for (int i = 0; i < listOfOrganisms.size(); i++) {
            //to not perform action on newly born organisms or death organisms
            if (listOfOrganisms.get(i).GetWhenBorn() != turnNumber && !listOfOrganisms.get(i).GetKilled()) {
                listOfOrganisms.get(i).Action();
            }
        }

        RemoveOrganisms();

    }

    //removing objects according to remove vector
    private void RemoveOrganisms() {

        for (int i = 0; i < toRemove.size(); i++) {
            Organism orgToRemove = toRemove.get(i);
            if (listOfOrganisms.contains(orgToRemove)) {
                listOfOrganisms.remove(orgToRemove);
            }
        }
        toRemove.clear();


    }

    //sorting organisms using comparator
    private void SortOrganisms() {
        Collections.sort(listOfOrganisms, new OrganismComparator());
    }

    //changing fields in the board panels
    public void DrawWorld() {

        //clean all fields of the board
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (!ifHex) {
                    worldFrame.boardPanel.board[i][j].setText("");
                    worldFrame.boardPanel.board[i][j].setEnabled(true);
                    worldFrame.boardPanel.board[i][j].setBackground(new Color(207, 217, 207));
                } else {
                    worldFrame.boardPanelHex.hexButtons[i][j].changeColor(new Color(207, 217, 207));
                    worldFrame.boardPanelHex.hexButtons[i][j].setEnabled(true);
                    worldFrame.boardPanelHex.hexButtons[i][j].repaint();
                }

            }
        }

        //add the organisms to board visualization
        Iterator<Organism> iterator = listOfOrganisms.iterator();
        while (iterator.hasNext()) {
            Organism o = iterator.next();
            o.Draw();
        }
    }

    public int GetTurnNumber() {
        return this.turnNumber;
    }

    public int GetWidth() {
        return this.width;
    }

    public int GetHeight() {
        return this.height;
    }

    public boolean GetHumanAlive() {
        return this.humanAlive;
    }

    public boolean GetIfHex() {
        return this.ifHex;
    }

    public GameFrame GetWorldFrame() {
        return this.worldFrame;
    }

    public Human GetHuman() {
        return this.worldHuman;
    }

    public Commentator GetCommentator() {
        return this.worldCommentator;
    }

    public void SetHumanAlive(boolean humanAlive) {
        this.humanAlive = humanAlive;
    }

    public ArrayList<Organism> GetListOfOrganisms() {
        return this.listOfOrganisms;
    }

    public Organism[][] GetStatesOfFields() {
        return statesOfFields;
    }

    //getting organism at given position
    public Organism GetOrganismAtPos(BoardPoint point) {

        return statesOfFields[point.GetY()][point.GetX()];
    }

    //deleting organism
    public void DeleteOrganism(Organism organism) {

        int x = organism.GetOrganismPos().GetX();
        int y = organism.GetOrganismPos().GetY();
        statesOfFields[y][x] = null;
        organism.SetKilled(true);
        if (organism.GetSpecie() == Species.HUMAN) SetHumanAlive(false);
        AddToRemove(organism);

    }

    //adding organisms to remove vector
    private void AddToRemove(Organism organism) {
        toRemove.add(organism);
    }

    //saving game to file
    public void SaveGameToFile(File file) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(World.this);
            out.close();

        } catch (IOException exception) {
            System.out.println("Error while saving to File");
            exception.printStackTrace();
        }
    }

    //loading game from file
    public World LoadGameFromFile(JFileChooser chosenFile) {

        try {
            FileInputStream file = new FileInputStream(chosenFile.getSelectedFile());
            ObjectInputStream in = new ObjectInputStream(file);
            World newWorld = (World) in.readObject();

            in.close();
            file.close();

            return newWorld;

        } catch (IOException | ClassNotFoundException exception) {
            System.out.println("Error while loading from File");
            exception.printStackTrace();
        }

        return null;

    }

}
