package op.JavaProject.Organisms.Animals;

import op.JavaProject.Functionality.BoardPoint;
import op.JavaProject.Functionality.Species;
import op.JavaProject.Game.Animal;
import op.JavaProject.Game.Organism;
import op.JavaProject.Game.World;

import java.awt.*;

import static op.JavaProject.Game.OrganismsStatics.*;

public class Turtle extends Animal {

    public Turtle(World organismWorld, BoardPoint point) {
        super(organismWorld, point);
        this.strength = 2;
        this.initiative = 1;
        this.organismSign = "T";
        this.organismSpecie = Species.TURTLE;
        this.organismColor = new Color(10, 144, 0);
    }

    //turtle has 75% chance to stay in the same place, so it may not move
    @Override
    public void Action() {

        int chance = organismPos.GetRandomValue(1, PROBABILITY_RANGE);

        if (chance > CHANCE_TO_STAY) {
            //just performing casual animal action, parent function
            super.Action();
        }
        //else he just stays in the same place

    }

    //checking defence successfulness
    @Override
    public boolean Defence(Organism attackingOrganism) {
        if (ReflectAttack(attackingOrganism)) return true;
        return false;
    }

    //reflecting attack from an opponent
    private boolean ReflectAttack(Organism attackingOrganism) {

        //that means he defended himself from attacking organism
        if (attackingOrganism.GetStrength() < DEFENCE_STRENGTH) {
            organismWorld.GetCommentator().AddComment(organismSign + " -> reflected attack from " + attackingOrganism.GetOrganismSign());
            return true;
        }
        return false;
    }
}