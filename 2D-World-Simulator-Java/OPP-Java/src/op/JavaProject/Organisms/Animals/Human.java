package op.JavaProject.Organisms.Animals;

import op.JavaProject.Functionality.BoardPoint;
import op.JavaProject.Functionality.MoveDirection;
import op.JavaProject.Functionality.Species;
import op.JavaProject.Game.Animal;
import op.JavaProject.Game.Organism;
import op.JavaProject.Game.World;

import java.awt.*;
import java.io.Serializable;

import static op.JavaProject.Game.OrganismsStatics.*;

public class Human extends Animal implements Serializable {
    private MoveDirection direction = MoveDirection.NONE;
    private MagicalPotion ability = new MagicalPotion();

    //class of human special ability
    private class MagicalPotion implements Serializable {
        boolean activated;
        int coolDownTime;
        int newStrength;

        public MagicalPotion() {
            this.coolDownTime = 0;
            this.activated = false;
            this.newStrength = NEW_STRENGTH;
        }

    }

    public Human(World organismWorld, BoardPoint point) {
        super(organismWorld, point);
        this.strength = 5;
        this.initiative = 4;
        this.organismSign = "H";
        this.organismSpecie = Species.HUMAN;
        this.organismColor = new Color(254, 199, 203);
    }

    public void SetDirection(MoveDirection direction) {
        this.direction = direction;
    }

    //performing human move according to player given direction
    @Override
    public void Action() {
        ReduceCoolDownTime();
        if (ability.activated) {
            ReduceActivation();
        }

        int x = organismPos.GetX(), y = organismPos.GetY();
        BoardPoint newPoint = new BoardPoint();

        if (direction == MoveDirection.NONE) return;
        switch (direction) {
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
            case DOWN_LEFT_EVEN:
                newPoint = new BoardPoint(x - 1, y + 1);
                break;
            case UP_LEFT_EVEN:
                newPoint = new BoardPoint(x - 1, y - 1);
                break;
            case DOWN_RIGHT_ODD:
                newPoint = new BoardPoint(x + 1, y + 1);
                break;
            case UP_RIGHT_ODD:
                newPoint = new BoardPoint(x + 1, y - 1);
                break;
            default:
                break;
        }

        Organism newPos = organismWorld.GetOrganismAtPos(newPoint);

        if (newPos != null) {
            Collision(newPos);
        } else {
            MoveToGivenField(newPoint);
        }
    }

    //activating human ability
    public void ActivateAbility() {

        if (ability.activated) {
            organismWorld.GetCommentator().AddComment("Human ability is already ACIVATED!");
            return;
        }

        if (ability.coolDownTime > 0) {
            organismWorld.GetCommentator().AddComment("Sorry, we cannot activate Human's ability. \n You need to wait " + ability.coolDownTime + " round/s.");
            return;
        }

        if (strength >= POWER_RANGE) {
            organismWorld.GetCommentator().AddComment("ERROR! Human already has power at least 10! ");
            return;
        }
        ability.activated = true;
        ability.newStrength = POWER_RANGE - strength;

        //strength rises to 10
        strength = GetStrength() + ability.newStrength;
        organismWorld.GetCommentator().AddComment("HUMAN ability ACTIVATED!");

    }

    //counting time left for activation
    private void ReduceActivation() {

        //changing human's strength
        if (ability.newStrength > 0) {
            ability.newStrength--;
            strength--;
        } else {
            //we need to switch off ability power
            //strength is == 5, the initial state -> so we do not have to change it
            organismWorld.GetCommentator().AddComment("HUMAN ability DISACTIVATED!");
            ability.activated = false;
            ability.coolDownTime = COOL_DOWN_TIME;
        }
    }

    //reducing cooldown time after activation of ability
    private void ReduceCoolDownTime() {
        if (ability.coolDownTime > 0) ability.coolDownTime--;
    }

    public MoveDirection GetDirection() {
        return this.direction;
    }

}
