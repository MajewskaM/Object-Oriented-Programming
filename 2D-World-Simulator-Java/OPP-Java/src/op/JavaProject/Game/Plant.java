package op.JavaProject.Game;

import op.JavaProject.Functionality.BoardPoint;

import static op.JavaProject.Game.OrganismsStatics.PROBABILITY_RANGE;
import static op.JavaProject.Game.OrganismsStatics.SOW_PROBABILITY;

public abstract class Plant extends Organism {
    //constructor
    protected Plant(World organismWorld, BoardPoint point) {
        super(organismWorld, point);
    }

    //plant sowing with some probability
    @Override
    public void Action() {

        //calculating probability of sowing
        int chanceToSow = organismPos.GetRandomValue(1, PROBABILITY_RANGE);
        if (chanceToSow <= SOW_PROBABILITY) {
            SowPlant();
        }

    }

    //performing basic sow action for every organism
    protected void SowPlant() {

        //new position for plant
        BoardPoint newPoint = GetNearPoint(organismPos, true);

        //if there is free field to spawn new plant
        if (newPoint.equals(organismPos)) return;

        Organism newOrganism = CreateNewOrganism(organismSpecie, organismWorld, newPoint);
        organismWorld.AddOrganismToWorld(newOrganism);

        organismWorld.GetCommentator().AddComment(organismSign + " -> Was sowed (action)");
    }

    //performing basic collision for plants -> most of the plants do not perform collision
    @Override
    public void Collision(Organism otherOrganism) {
    }

    //default defence action
    @Override
    public boolean Defence(Organism AttackingOrganism) {
        return false;
    }

    @Override
    public boolean CheckIfOrganismIsAnimal() {
        return false;
    }

}
