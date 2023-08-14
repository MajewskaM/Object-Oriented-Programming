package op.JavaProject.Organisms.Animals;

import op.JavaProject.Functionality.BoardPoint;
import op.JavaProject.Functionality.Species;
import op.JavaProject.Game.Animal;
import op.JavaProject.Game.Organism;
import op.JavaProject.Game.World;

import java.awt.*;

public class Fox extends Animal {

    public Fox(World organismWorld, BoardPoint point) {
        super(organismWorld, point);
        this.strength = 3;
        this.initiative = 7;
        this.organismSign = "F";
        this.organismSpecie = Species.FOX;
        this.organismColor = new Color(255, 139, 18);
    }

    @Override
    //performing action for fox
    public void Action() {

        BoardPoint newPoint = GetNearPoint(this.GetOrganismPos(),false);
        Organism newPos = organismWorld.GetOrganismAtPos(newPoint);

        //fox is not moving when on desired position there is organism with which he may lose the fight
        if (newPos != null) {
            if (newPos.GetStrength() < strength) {
                Collision(newPos);
            }
            else if (newPos.GetStrength() == strength) {
                if (newPos.GetWhenBorn() > whenBorn) {
                    Collision(newPos);
                }
            }
            else {
                organismWorld.GetCommentator().AddComment(organismSign + " smells " + newPos.GetOrganismSign() + "'s stink...");
            }
        }
        else {
            MoveToGivenField(newPoint);
        }

    }
}
