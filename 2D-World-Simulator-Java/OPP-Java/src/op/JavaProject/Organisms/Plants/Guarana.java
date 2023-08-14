package op.JavaProject.Organisms.Plants;

import op.JavaProject.Functionality.BoardPoint;
import op.JavaProject.Functionality.Species;
import op.JavaProject.Game.Organism;
import op.JavaProject.Game.Plant;
import op.JavaProject.Game.World;

import java.awt.*;

import static op.JavaProject.Game.OrganismsStatics.ADDITIONAL_STRENGTH;

public class Guarana extends Plant {

    public Guarana(World organismWorld, BoardPoint point) {
        super(organismWorld, point);
        this.strength = 0;
        this.initiative = 0;
        this.organismSign = "+";
        this.organismSpecie = Species.GUARANA;
        this.organismColor = new Color(255, 43, 28);
    }

    //checking defence successfulness
    @Override
    public boolean Defence(Organism attackingOrganism) {

        if (IncreaseStrength(attackingOrganism)) return true;
        return false;
    }

    //performing defence of guarana -> adding strength to attacking organism
    private boolean IncreaseStrength(Organism attackingOrganism) {
        int currentStrength = attackingOrganism.GetStrength();
        attackingOrganism.SetStrength(currentStrength + ADDITIONAL_STRENGTH);
        organismWorld.GetCommentator().AddComment(attackingOrganism.GetOrganismSign() + " gained +3 STRENGTH by eating GUARANA!");
        //plant must be eaten anyway -> means we need to come back to collision()
        return false;
    }

}



