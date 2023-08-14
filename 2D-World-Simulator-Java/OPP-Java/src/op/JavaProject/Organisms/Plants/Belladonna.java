package op.JavaProject.Organisms.Plants;

import op.JavaProject.Functionality.BoardPoint;
import op.JavaProject.Functionality.Species;
import op.JavaProject.Game.Organism;
import op.JavaProject.Game.Plant;
import op.JavaProject.Game.World;

import java.awt.*;

public class Belladonna extends Plant {

    public Belladonna(World organismWorld, BoardPoint point) {
        super(organismWorld, point);
        this.strength = 99;
        this.initiative = 0;
        this.organismSign = "O";
        this.organismSpecie = Species.GRASS;
        this.organismColor = new Color(156, 127, 230);
    }

    //checking defence successfulness
    @Override
    public boolean Defence(Organism attackingOrganism) {

        if (KillAnimal(attackingOrganism)) return true;
        return false;
    }

    //performing defence -> killing animal
    private boolean KillAnimal(Organism attackingOrganism) {
        organismWorld.DeleteOrganism(attackingOrganism);
        organismWorld.GetCommentator().AddComment(attackingOrganism.GetOrganismSign() + " died by eating BELLADONNA!");
        return true;
    }

}