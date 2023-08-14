package op.JavaProject.Organisms.Animals;

import op.JavaProject.Functionality.BoardPoint;
import op.JavaProject.Functionality.Species;
import op.JavaProject.Game.Animal;
import op.JavaProject.Game.World;

import java.awt.*;


public class Wolf extends Animal {

    public Wolf(World organismWorld, BoardPoint point) {
        super(organismWorld, point);
        this.strength = 9;
        this.initiative = 5;
        this.organismSign = "W";
        this.organismSpecie = Species.WOLF;
        this.organismColor = new Color(147, 147, 147);
    }
}
