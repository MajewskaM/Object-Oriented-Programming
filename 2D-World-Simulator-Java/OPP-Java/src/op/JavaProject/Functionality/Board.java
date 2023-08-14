package op.JavaProject.Functionality;

import op.JavaProject.Game.Organism;
import op.JavaProject.Game.World;
import op.JavaProject.Organisms.Animals.*;
import op.JavaProject.Organisms.Plants.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static op.JavaProject.Functionality.FunctionalityStatics.*;

public class Board extends JPanel {

    public JButton[][] board;
    public Board(int boardX, int boardY, World newWorld, JFrame frame) {
        setLayout(new GridLayout(boardY, boardX));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int boardSize = (int) (Math.min(screenSize.width, screenSize.height) * SCREEN_OCCUPATION);

        //calculating board and font size
        int possibleWidth = (int) (screenSize.width * SCREEN_OCCUPATION);
        int fontSizeW = possibleWidth / (boardX + FONT_SCALAR);

        int possibleHeight = (int) (screenSize.height * SCREEN_OCCUPATION);
        int fontSizeH = possibleHeight / (boardY + FONT_SCALAR);

        int fontSize = Math.min(fontSizeH, fontSizeW) / FONT_SCALAR;

        setPreferredSize(new Dimension(boardSize, boardSize));

        //creating the board
        board = new JButton[boardY][boardX];
        for (int i = 0; i < boardY; i++) {
            for (int j = 0; j < boardX; j++) {
                board[i][j] = new JButton();
                board[i][j].setFont(new Font("Arial", Font.BOLD, fontSize));
                board[i][j].setBackground(new Color(207, 217, 207));

                int finalI = j;
                int finalJ = i;
                board[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();

                        //new window to choose an animal to put
                        JFrame listOfAnimals = new JFrame();
                        listOfAnimals.setBounds(mouseLocation.x, mouseLocation.y, ANIMALS_WINDOW_WIDTH, ANIMALS_WINDOW_HEIGHT);

                        //creating buttons of every kind of organism
                        JButton wolfB = new JButton("Wolf");
                        JButton sheepB = new JButton("Sheep");
                        JButton foxB = new JButton("Fox");
                        JButton turtleB = new JButton("Turtle");
                        JButton antelopeB = new JButton("Antelope");
                        JButton grassB = new JButton("Grass");
                        JButton sowThistleB = new JButton("Sow Thistle");
                        JButton guaranaB = new JButton("Guarana");
                        JButton belladonnaB = new JButton("Belladonna");
                        JButton sosnowskysHogweedB = new JButton("Sosnowsky's Hogweed");

                        //adding buttons with action listeners
                        listOfAnimals.add(wolfB);
                        AddActionListenerToButton(wolfB, Species.WOLF, newWorld, finalI, finalJ, frame, listOfAnimals);

                        listOfAnimals.add(sheepB);
                        AddActionListenerToButton(sheepB, Species.SHEEP, newWorld, finalI, finalJ, frame, listOfAnimals);

                        listOfAnimals.add(foxB);
                        AddActionListenerToButton(foxB, Species.FOX, newWorld, finalI, finalJ, frame, listOfAnimals);

                        listOfAnimals.add(turtleB);
                        AddActionListenerToButton(turtleB, Species.TURTLE, newWorld, finalI, finalJ, frame, listOfAnimals);

                        listOfAnimals.add(antelopeB);
                        AddActionListenerToButton(antelopeB, Species.ANTELOPE, newWorld, finalI, finalJ, frame, listOfAnimals);

                        listOfAnimals.add(grassB);
                        AddActionListenerToButton(grassB, Species.GRASS, newWorld, finalI, finalJ, frame, listOfAnimals);

                        listOfAnimals.add(sowThistleB);
                        AddActionListenerToButton(sowThistleB, Species.SOW_THISTLE, newWorld, finalI, finalJ, frame, listOfAnimals);

                        listOfAnimals.add(guaranaB);
                        AddActionListenerToButton(guaranaB, Species.GUARANA, newWorld, finalI, finalJ, frame, listOfAnimals);

                        listOfAnimals.add(belladonnaB);
                        AddActionListenerToButton(belladonnaB, Species.BELLADONNA, newWorld, finalI, finalJ, frame, listOfAnimals);

                        listOfAnimals.add(sosnowskysHogweedB);
                        AddActionListenerToButton(sosnowskysHogweedB, Species.SOSNOWSKYS_HOGWEED, newWorld, finalI, finalJ, frame, listOfAnimals);

                        listOfAnimals.setVisible(true);
                        listOfAnimals.setLayout(new GridLayout(ANIMALS_ROWS, ANIMALS_COLS));
                    }
                });
                //add button to grid
                add(board[i][j]);
            }
        }
    }

    //adding action listener to given button
    private void AddActionListenerToButton(JButton button, Species specie, World newWorld,
                                           int finalI, int finalJ, JFrame frame, JFrame listOfAnimals) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //adding organism according to chosen specie
                Organism newOrganism;
                switch (specie) {
                    case WOLF:
                        newWorld.AddOrganismToWorld(new Wolf(newWorld, new BoardPoint(finalI, finalJ)));
                        break;
                    case SHEEP:
                        newWorld.AddOrganismToWorld(new Sheep(newWorld, new BoardPoint(finalI, finalJ)));
                        break;
                    case FOX:
                        newWorld.AddOrganismToWorld(new Fox(newWorld, new BoardPoint(finalI, finalJ)));
                        break;
                    case TURTLE:
                        newWorld.AddOrganismToWorld(new Turtle(newWorld, new BoardPoint(finalI, finalJ)));
                        break;
                    case ANTELOPE:
                        newWorld.AddOrganismToWorld(new Antelope(newWorld, new BoardPoint(finalI, finalJ)));
                        break;
                    case GRASS:
                        newWorld.AddOrganismToWorld(new Grass(newWorld, new BoardPoint(finalI, finalJ)));
                        break;
                    case SOW_THISTLE:
                        newWorld.AddOrganismToWorld(new SowThistle(newWorld, new BoardPoint(finalI, finalJ)));
                        break;
                    case GUARANA:
                        newWorld.AddOrganismToWorld(new Guarana(newWorld, new BoardPoint(finalI, finalJ)));
                        break;
                    case BELLADONNA:
                        newWorld.AddOrganismToWorld(new Belladonna(newWorld, new BoardPoint(finalI, finalJ)));
                        break;
                    case SOSNOWSKYS_HOGWEED:
                        newWorld.AddOrganismToWorld(new SosnowskysHogweed(newWorld, new BoardPoint(finalI, finalJ)));
                        break;

                    default:
                        break;
                }
                listOfAnimals.setVisible(false);
                //coming back with focus on general game frame
                frame.requestFocus();
                newWorld.DrawWorld();
            }
        });
    }
}
