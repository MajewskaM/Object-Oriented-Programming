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

public class BoardHex extends JPanel {
    private static float hexSize;
    public hexagonButton[][] hexButtons;
    public BoardHex(int x, int y, World newWorld, JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int boardSize = (int) (Math.min(screenSize.width, screenSize.height) * SCREEN_OCCUPATION);

        hexSize = boardSize / (y * 2);
        setPreferredSize(new Dimension((int) (Math.sqrt(3) * hexSize) * (x + 1), (int) (2 * hexSize) * y));

        setLayout(null);
        hexButtons = new hexagonButton[x][y];
        int posX = 0;
        int posY = 0;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                int finalI = j;
                int finalJ = i;
                hexButtons[i][j] = new hexagonButton(i, j);
                hexButtons[i][j].addActionListener(new ActionListener() {
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
                add(hexButtons[i][j]);
                hexButtons[i][j].setBounds(posX, posY, (int) (Math.sqrt(3) * hexSize), (int) (2 * hexSize));
                hexButtons[i][j].setVisible(true);
                posX += (int) (Math.sqrt(3) * hexSize) - 1;
            }
            if (i % 2 == 0) {
                posX = (int) (Math.sqrt(3) * hexSize) / 2;
            } else {
                posX = 0;
            }
            posY += (HEX_ACCURACY * (Math.sqrt(3) * hexSize));
        }
    }

    //instance of one hexagonal button
    public class hexagonButton extends JButton {
        private static final int SIDES = 6;
        private Color color = new Color(207, 217, 207);
        public hexagonButton(int x, int y) {
            setContentAreaFilled(false);
            setFocusPainted(true);
            setBorderPainted(false);
            setPreferredSize(new Dimension((int) (Math.sqrt(3) * hexSize), (int) (2 * hexSize)));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Polygon hex = new Polygon();
            for (int i = 1; i <= SIDES; i++) {
                //calculating degree
                var degree = 60 * i - 30;
                var radians = Math.PI / 180 * degree;
                hex.addPoint((int) ((int) (Math.sqrt(3) * hexSize) / 2 + hexSize * Math.cos(radians)),
                        (int) (hexSize + hexSize * Math.sin(radians)));
            }
            g.setColor(Color.BLACK);
            g.drawPolygon(hex);
            g.setColor(color);
            g.fillPolygon(hex);
        }

        //changing color of button
        public void changeColor(Color c1) {
            color = c1;
        }

    }

    private void AddActionListenerToButton(JButton button, Species specie, World newWorld,
                                           int finalI, int finalJ, JFrame frame, JFrame listOfAnimals) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
