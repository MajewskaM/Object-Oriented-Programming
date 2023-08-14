package op.JavaProject.Functionality;

import op.JavaProject.Game.World;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;

import static op.JavaProject.Functionality.FunctionalityStatics.*;

public class InfoPanel extends JPanel implements Serializable {

    public InfoPanel(World newWorld, JFrame frame) {

        JButton turnButton = new JButton("Next Turn");
        turnButton.setBounds(BUTTON_MARGIN_WIDTH, BUTTON_MARGIN_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);

        JButton saveButton = new JButton("Save Game");
        saveButton.setBounds(BUTTON_MARGIN_WIDTH, BUTTON_MARGIN_HEIGHT + 60, BUTTON_WIDTH, BUTTON_HEIGHT);

        JButton loadButton = new JButton("Load Game");
        loadButton.setBounds(BUTTON_MARGIN_WIDTH, BUTTON_MARGIN_HEIGHT + 120, BUTTON_WIDTH, BUTTON_HEIGHT);

        //performing 'next round'
        turnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newWorld.MakeTurn();
                newWorld.DrawWorld();
            }
        });

        //saving game to file
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showSaveDialog(frame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    newWorld.SaveGameToFile(file);
                    JOptionPane.showMessageDialog(frame, "Game saved to file: " + file.getName());
                    frame.requestFocus();
                }
            }
        });

        //loading game from file
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showSaveDialog(frame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    World toLoad = newWorld.LoadGameFromFile(fileChooser);
                    frame.setVisible(false);
                    GameFrame newFrame = new GameFrame(toLoad);
                }

            }
        });

        add(turnButton);
        add(saveButton);
        add(loadButton);

    }
}
