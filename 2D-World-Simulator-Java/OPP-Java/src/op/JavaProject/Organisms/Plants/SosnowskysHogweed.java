package op.JavaProject.Organisms.Plants;

import op.JavaProject.Functionality.BoardPoint;
import op.JavaProject.Functionality.Species;
import op.JavaProject.Game.Organism;
import op.JavaProject.Game.Plant;
import op.JavaProject.Game.World;

import java.awt.*;

import static op.JavaProject.Game.OrganismsStatics.*;

public class SosnowskysHogweed extends Plant {

    public SosnowskysHogweed(World organismWorld, BoardPoint point) {
        super(organismWorld, point);
        this.strength = 10;
        this.initiative = 0;
        this.organismSign = "Y";
        this.organismSpecie = Species.SOSNOWSKYS_HOGWEED;
        this.organismColor = new Color(219, 246, 160);
    }

    //kill every animal in neighborhood cells
    @Override
    public void Action() {

        int x = organismPos.GetX(), y = organismPos.GetY();
        BoardPoint newPoint = new BoardPoint();
        Organism tmpOrganism;
        int sides = SIDES;
        //hexagonal world means more fields around sosnowskyshogweed
        if (organismWorld.GetIfHex()) sides = SIDES_HEX;

        for (int i = 0; i < sides; i++) {

            switch (i) {
                case UP:
                    newPoint = new BoardPoint(x, y - 1);
                    break;
                case DOWN:
                    newPoint = new BoardPoint(x, y + 1);
                    break;
                case RIGHT:
                    newPoint = new BoardPoint(x + 1, y);
                    break;
                case LEFT:
                    newPoint = new BoardPoint(x - 1, y);
                    break;
                case SECOND_DOWN:
                    if (y % 2 == 0) {
                        newPoint = new BoardPoint(x - 1, y + 1);
                    } else {
                        newPoint = new BoardPoint(x + 1, y + 1);
                    }
                    break;
                case SECOND_UP:
                    if (y % 2 == 0) {
                        newPoint = new BoardPoint(x - 1, y - 1);
                    } else {
                        newPoint = new BoardPoint(x + 1, y - 1);
                    }

                    break;
                default:
                    break;
            }

            if (super.CheckPointCorrectness(newPoint.GetX(), newPoint.GetY())) {
                tmpOrganism = organismWorld.GetOrganismAtPos(newPoint);
                //we cannot kill plants!
                if (tmpOrganism != null && tmpOrganism.CheckIfOrganismIsAnimal()) {
                    organismWorld.GetCommentator().AddComment(tmpOrganism.GetOrganismSign() + " died by SOSNOWSKYHOGWEED!");
                    organismWorld.DeleteOrganism(tmpOrganism);
                }

            }
        }

        //afterwards just perform sowing
        super.Action();
    }

    //checking defence successfulness
    @Override
    public boolean Defence(Organism attackingOrganism) {
        if (KillAnimal(attackingOrganism)) return true;
        return false;
    }

    //kill every animal in its neighborhood
    private boolean KillAnimal(Organism attackingOrganism) {
        organismWorld.DeleteOrganism(attackingOrganism);
        organismWorld.GetCommentator().AddComment(attackingOrganism.GetOrganismSign() + " died by eating SOSNOWSKYHOGWEED!");
        return true;
    }
}