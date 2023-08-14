package op.JavaProject.Game;

import javax.swing.*;
import java.awt.*;

public class Commentator extends JPanel {
    private JTextArea textBox;
    private static final int TEXTBOX_WIDTH = 400;
    private static final int TEXTBOX_HIEGHT = 500;
    private static final int COLOR = 238;
    private static final int FONT_SIZE = 16;

    public Commentator() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.width - TEXTBOX_WIDTH, TEXTBOX_WIDTH / 2, TEXTBOX_WIDTH, TEXTBOX_HIEGHT);
        textBox = new JTextArea();
        textBox.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        textBox.setBackground(new Color(COLOR, COLOR, COLOR));
        add(textBox);
    }

    public void AddComment(String comment) {
        textBox.append(comment + "\n");
    }

    public void cleanComments() {
        textBox.setText("");
    }
}
