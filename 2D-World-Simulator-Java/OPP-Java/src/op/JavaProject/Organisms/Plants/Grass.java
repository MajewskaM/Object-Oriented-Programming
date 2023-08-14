package op.JavaProject.Organisms.Plants;

import op.JavaProject.Functionality.BoardPoint;
import op.JavaProject.Functionality.Species;
import op.JavaProject.Game.Plant;
import op.JavaProject.Game.World;

import java.awt.*;

public class Grass extends Plant {

    public Grass(World organismWorld, BoardPoint point) {
        super(organismWorld, point);
        this.strength = 0;
        this.initiative = 0;
        this.organismSign = "#";
        this.organismSpecie = Species.GRASS;
        this.organismColor = new Color(27, 90, 0);
    }

}
