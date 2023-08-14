package op.JavaProject.Organisms.Animals;

import op.JavaProject.Functionality.BoardPoint;
import op.JavaProject.Functionality.Species;
import op.JavaProject.Game.Animal;
import op.JavaProject.Game.World;

import java.awt.*;

public class Sheep extends Animal {

    public Sheep(World organismWorld, BoardPoint point) {
        super(organismWorld, point);
        this.strength = 4;
        this.initiative = 4;
        this.organismSign = "S";
        this.organismSpecie = Species.SHEEP;
        this.organismColor = new Color(227, 227, 227);
    }
}
