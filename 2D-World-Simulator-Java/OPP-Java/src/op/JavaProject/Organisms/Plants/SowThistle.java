package op.JavaProject.Organisms.Plants;

import op.JavaProject.Functionality.BoardPoint;
import op.JavaProject.Functionality.Species;
import op.JavaProject.Game.Plant;
import op.JavaProject.Game.World;

import java.awt.*;

import static op.JavaProject.Game.OrganismsStatics.MAX_ATTEMPT;

public class SowThistle extends Plant {

    public SowThistle(World organismWorld, BoardPoint point) {
        super(organismWorld, point);
        this.strength = 0;
        this.initiative = 0;
        this.organismSign = "*";
        this.organismSpecie = Species.SOW_THISTLE;
        this.organismColor = new Color(254, 255, 0);
    }

    //sowThistle has 3 attempts to sow in one round
    @Override
    public void Action() {
        int attemps = MAX_ATTEMPT;
        while (attemps-- > 0) {
            super.Action();
        }

    }

}

