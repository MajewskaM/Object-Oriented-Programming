package op.JavaProject.Game;

import op.JavaProject.Functionality.BoardPoint;

public abstract class Animal extends Organism {
    protected Animal(World organismWorld, BoardPoint point) {
        super(organismWorld, point);
    }

    //animal moves to a randomly selected neighbourhood field or fight with other organism
    @Override
    public void Action() {

        //looking for near field close to our organism
        BoardPoint orgPoint = organismPos;
        BoardPoint newPoint = GetNearPoint(organismPos, false);

        //checking collisions
        Organism newPosition = organismWorld.GetOrganismAtPos(newPoint);
        if (newPosition != null) Collision(newPosition);
        else MoveToGivenField(newPoint);

    }

    @Override
    //look whether given organism is an animal
    public boolean CheckIfOrganismIsAnimal() {
        return true;
    }

    //perform collision for given organism specie
    @Override
    public void Collision(Organism otherOrganism) {

        //multiplication of two same species
        if (organismSpecie == otherOrganism.GetSpecie()) {
            MultiplyOrganisms(otherOrganism);
        } else {

            //other organism may defend itself or perform some other action when something tries to eat it
            if (otherOrganism.Defence(this)) return;

            String organismSignString, opponentSignString;

            //looking for the result of the fight depending on organisms strength
            if (strength >= otherOrganism.GetStrength()) {
                BoardPoint newPoint = otherOrganism.GetOrganismPos();
                organismSignString = otherOrganism.GetOrganismSign();
                opponentSignString = organismSign;
                organismWorld.DeleteOrganism(otherOrganism);
                MoveToGivenField(newPoint);
            } else {
                organismSignString = organismSign;
                opponentSignString = otherOrganism.GetOrganismSign();
                organismWorld.DeleteOrganism(this);
            }
            //adding new content to commentator report
            organismWorld.GetCommentator().AddComment(organismSignString + " -> Was killed (collision) by " + opponentSignString);
        }
    }

    //multiplying organisms (of the same specie), adding new organism to near field
    public void MultiplyOrganisms(Organism otherOrganism) {
        Organism newOrganism;
        //getting new organism position
        BoardPoint newPoint = GetNearPoint(this.organismPos, true);

        //that means we cannot spawn organism, there is no free space around
        if (newPoint.equals(organismPos)) {
            newPoint = otherOrganism.GetNearPoint(otherOrganism.GetOrganismPos(), true);

            //looking for free space around second organism
            if (newPoint.equals(otherOrganism.GetOrganismPos())) {
                return;
            } else {
                //there is no space around first organism but there is free space around second organism
                newOrganism = CreateNewOrganism(organismSpecie, organismWorld, newPoint);
                if (newOrganism == null) {

                }
                organismWorld.AddOrganismToWorld(newOrganism);

            }
        } else {
            //there is free space around first organism
            newOrganism = CreateNewOrganism(organismSpecie, organismWorld, newPoint);
            organismWorld.AddOrganismToWorld(newOrganism);
        }

        //adding commentator report
        organismWorld.GetCommentator().AddComment(organismSign + " -> Was born (multiplication)");
    }

    //default defence "move" for an animal
    @Override
    public boolean Defence(Organism attackingOrganism) {
        return false;
    }
}
