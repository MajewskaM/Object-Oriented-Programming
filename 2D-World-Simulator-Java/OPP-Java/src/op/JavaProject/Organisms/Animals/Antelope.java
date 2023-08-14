package op.JavaProject.Organisms.Animals;

import op.JavaProject.Functionality.BoardPoint;
import op.JavaProject.Functionality.Species;
import op.JavaProject.Game.Animal;
import op.JavaProject.Game.Organism;
import op.JavaProject.Game.World;

import java.awt.*;

import static op.JavaProject.Game.OrganismsStatics.CHANCE_GETAWAY;
import static op.JavaProject.Game.OrganismsStatics.PROBABILITY_RANGE;

public class Antelope extends Animal {

    public Antelope(World organismWorld, BoardPoint point) {
        super(organismWorld, point);
        this.strength = 4;
        this.initiative = 4;
        this.organismSign = "A";
        this.organismSpecie = Species.ANTELOPE;
        this.organismColor = new Color(255, 191, 45);
    }

    //moving two fields instead of one
    @Override
    public void Action() {
        BoardPoint initialPos = organismPos;
        //getting new first point
        BoardPoint newPoint1 = GetNearPoint(organismPos, false);

        //new second point shouldn't be the initial position
        BoardPoint newPoint2;
        do {
            newPoint2 = GetNearPoint(newPoint1, false);
        } while (newPoint2.equals(newPoint1) || newPoint2.equals(initialPos));

        Organism newPos = organismWorld.GetOrganismAtPos(newPoint2);

        //checking if there is other organism
        if (newPos != null) {
            Collision(newPos);
        } else {
            MoveToGivenField(newPoint2);
        }
    }

    //giving info about antelope defence success
    @Override
    public boolean Defence(Organism attackingOrganism) {
        if (GetAway(attackingOrganism)) return true;
        return false;
    }

    //performing antelope defence function
    private boolean GetAway(Organism attackingOrganism) {

        BoardPoint newPoint;
        int chance = organismPos.GetRandomValue(1, PROBABILITY_RANGE);
        if (chance <= CHANCE_GETAWAY) {
            //antelope runs away - moves to neighborhood free field
            newPoint = GetNearPoint(GetOrganismPos(), true);

            //no place to run, fight -> going back in collision function of parent class!
            if (GetOrganismPos().equals(newPoint)) return false;

            //that means antelope defended itself and run away
            organismWorld.GetCommentator().AddComment("ANTELOPE - > run away from " + attackingOrganism.GetOrganismSign());
            return true;
        }
        return false;
    }

}
