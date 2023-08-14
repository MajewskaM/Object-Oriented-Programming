package op.JavaProject.Functionality;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static op.JavaProject.Functionality.FunctionalityStatics.*;
import static op.JavaProject.Game.OrganismsStatics.START_FILL;

public class Menu {

    private JTextField newWidth;
    private JTextField newHeight;
    private JFrame frame;

    //constructor of Manu class
    public Menu() {
        frame = new JFrame("2D-World-Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(MENU_WIDTH, MENU_HEIGHT);
        //getting screen dimension
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (screenSize.width - MENU_WIDTH) / 2;
        int centerY = (screenSize.height - MENU_HEIGHT) / 2;

        //placing frame at the center of a screen
        frame.setLocation(centerX, centerY);

        //label to set world width
        JLabel labelWidth = new JLabel("Set World Width:");
        labelWidth.setBounds(MENU_WIDTH / 2 - LABEL_WIDTH / 2, 20, LABEL_WIDTH, LABEL_HEIGHT);

        newWidth = new JTextField();
        newWidth.setBounds(MENU_WIDTH / 2 - TEXT_WIDTH / 2, 40, TEXT_WIDTH, TEXT_HEIGHT);

        //label to set world height
        JLabel labelHeight = new JLabel("Set World Height:");
        labelHeight.setBounds(MENU_WIDTH / 2 - LABEL_WIDTH / 2, 60, LABEL_WIDTH, LABEL_HEIGHT);

        newHeight = new JTextField();
        newHeight.setBounds(MENU_WIDTH / 2 - TEXT_WIDTH / 2, 80, TEXT_WIDTH, TEXT_HEIGHT);

        //creating instance of JButton to start a game
        JButton startButton = new JButton("Start Game Grid");
        startButton.setBounds(MENU_WIDTH / 2 - BUTTON_WIDTH / 2, 120, BUTTON_WIDTH, BUTTON_HEIGHT);

        JButton startButtonHex = new JButton("Start Game Hexagonal");
        startButtonHex.setBounds(MENU_WIDTH / 2 - BUTTON_WIDTH / 2, 180, BUTTON_WIDTH, BUTTON_HEIGHT);

        //adding buttons to JFrame
        frame.add(labelWidth);
        frame.add(newWidth);
        frame.add(labelHeight);
        frame.add(newHeight);
        frame.add(startButton);
        frame.add(startButtonHex);

        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);

        addActionListenerToButton(startButton, false);
        addActionListenerToButton(startButtonHex, true);

    }

    //performing playing game and displaying proper frame
    private void PlayingGame(int width, int height, boolean ifHex) {
        frame = new GameFrame(width, height, ifHex);
    }

    //perform actions after clicking specific button
    private void addActionListenerToButton(JButton button, boolean ifHex) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String widthStr = newWidth.getText();
                String heightStr = newHeight.getText();
                int width, height;

                try {
                    width = Integer.parseInt(widthStr);
                    height = Integer.parseInt(heightStr);

                    //checking proper format of input value
                    if (height > 0) {
                        if (width > 0) {
                            if (width * height > ((START_FILL * TOTAL_NUMBER_OF_ORGANISMS) + 1)) {
                                //we can start a game
                                frame.setVisible(false);
                                PlayingGame(width, height, ifHex);
                            } else {
                                newWidth.setText("Too small values!");
                                newHeight.setText("Too small values!");
                            }
                        } else {
                            newWidth.setText("Invalid Width!");
                        }

                    } else {
                        newHeight.setText("Invalid Height!");
                    }
                } catch (NumberFormatException ex) {
                    newWidth.setText("Invalid input!");
                    newHeight.setText("Invalid input!");
                }
            }
        });
    }

}
