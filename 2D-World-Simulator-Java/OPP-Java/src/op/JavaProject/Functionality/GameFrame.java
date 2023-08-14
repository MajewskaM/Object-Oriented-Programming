package op.JavaProject.Functionality;

import op.JavaProject.Game.Organism;
import op.JavaProject.Game.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;

import static op.JavaProject.Functionality.FunctionalityStatics.*;

public class GameFrame extends JFrame {

    private World newWorld;
    public Board boardPanel;
    public BoardHex boardPanelHex;
    private boolean hex = false;

    //constructor of game frame using other world instance, useful when loading from file
    public GameFrame(World otherWorld) {
        super("2D-World-Simulator");

        //maximize frame width and height - using full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);

        //checking what kind of world we should create
        if (otherWorld.GetIfHex()) hex = true;
        newWorld = new World(otherWorld, this);

        //setting new world to organisms
        Iterator<Organism> iterator = newWorld.GetListOfOrganisms().iterator();
        while (iterator.hasNext()) {
            Organism i = iterator.next();
            i.SetOrganismWorld(newWorld);
        }

        SetGameFrame();
    }

    //constructor of game frame using given parameters
    public GameFrame(int boardX, int boardY, boolean ifHex) {
        super("2D-World-Simulator");

        //maximize frame width and height - using full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);

        //checking what kind of world we should create
        if (ifHex) hex = true;

        newWorld = new World(boardX, boardY, this, hex);
        SetGameFrame();

    }

    //setting appropriate parameters of a frame
    private void SetGameFrame() {

        //creating new boardPanel
        if (hex) {
            boardPanelHex = new BoardHex(newWorld.GetHeight(), newWorld.GetWidth(), newWorld, newWorld.GetWorldFrame());
            add(boardPanelHex);
        } else {
            boardPanel = new Board(newWorld.GetWidth(), newWorld.GetHeight(), newWorld, newWorld.GetWorldFrame());
            //adding the board panel to the left side of JFrame
            add(boardPanel, BorderLayout.WEST);
        }

        //add organisms to board visualization
        newWorld.DrawWorld();

        //creating and adding new text box
        JPanel textBox = newWorld.GetCommentator();
        add(textBox, BorderLayout.EAST);

        JPanel infoPanel = new InfoPanel(newWorld, this);
        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                int newPosX, newPosY;

                if (newWorld.GetHumanAlive()) {
                    //changing human direction of move
                    newPosX = newWorld.GetHuman().GetOrganismPos().GetX();
                    newPosY = newWorld.GetHuman().GetOrganismPos().GetY();
                    switch (key) {
                        case KeyS:
                            newWorld.GetHuman().SetDirection(MoveDirection.DOWN);
                            newPosY++;
                            if (newWorld.GetIfHex()) {
                                if (newWorld.GetHuman().GetOrganismPos().GetY() % 2 != 0)
                                    newWorld.GetHuman().SetDirection(MoveDirection.DOWN_RIGHT_ODD);
                                newPosX++;
                            }

                            break;
                        case KeyW:
                            newWorld.GetHuman().SetDirection(MoveDirection.UP);
                            newPosY--;
                            if (newWorld.GetIfHex()) {
                                if (newWorld.GetHuman().GetOrganismPos().GetY() % 2 != 0)
                                    newWorld.GetHuman().SetDirection(MoveDirection.UP_RIGHT_ODD);
                                newPosX++;
                            }
                            break;
                        case KeyA:
                            newWorld.GetHuman().SetDirection(MoveDirection.LEFT);
                            newPosX--;
                            break;
                        case KeyD:
                            newWorld.GetHuman().SetDirection(MoveDirection.RIGHT);
                            newPosX++;
                            break;
                        case KeySPACE:
                            //activating human ability
                            newWorld.GetHuman().ActivateAbility();
                            break;
                        default:
                            if (newWorld.GetIfHex()) {
                                if (key == KeyQ) {
                                    newPosY--;
                                    newWorld.GetHuman().SetDirection(MoveDirection.UP);
                                    if (newWorld.GetHuman().GetOrganismPos().GetY() % 2 == 0) {
                                        newPosX--;
                                        newWorld.GetHuman().SetDirection(MoveDirection.UP_LEFT_EVEN);
                                    }
                                } else if (key == KeyZ) {
                                    newPosY++;
                                    newWorld.GetHuman().SetDirection(MoveDirection.DOWN);
                                    if (newWorld.GetHuman().GetOrganismPos().GetY() % 2 == 0) {
                                        newPosX--;
                                        newWorld.GetHuman().SetDirection(MoveDirection.DOWN_LEFT_EVEN);
                                    }
                                } else {
                                    newWorld.GetHuman().SetDirection(MoveDirection.NONE);
                                }
                            } else {
                                newWorld.GetHuman().SetDirection(MoveDirection.NONE);
                            }

                            break;
                    }

                    //we need to check correctness of desired point
                    if (newWorld.GetHuman().CheckPointCorrectness(newPosX, newPosY)) {
                        if (newWorld.GetHuman().GetDirection() != MoveDirection.NONE) {
                            newWorld.MakeTurn();
                            newWorld.DrawWorld();
                        }

                    } else {
                        newWorld.GetCommentator().AddComment("ERROR");
                    }
                    newWorld.GetHuman().SetDirection(MoveDirection.NONE);

                } else {
                    newWorld.GetCommentator().AddComment("HUMAN IS NOT ALIVE!");
                }
                requestFocus();
            }

            @Override
            public void keyTyped(KeyEvent e) {

            }


            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        add(infoPanel, BorderLayout.EAST);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setResizable(false);
        //show JFrame
        setVisible(true);

    }

    public JPanel GetBoardPanel() {
        return boardPanel;
    }

    public JPanel GetBoardPanelHex() {
        return boardPanelHex;
    }


}
