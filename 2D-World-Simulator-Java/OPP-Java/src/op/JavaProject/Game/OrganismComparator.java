package op.JavaProject.Game;

import java.util.Comparator;

public class OrganismComparator implements Comparator<Organism> {

    //comparing two instances of organisms, used to sort
    @Override
    public int compare(Organism a, Organism b) {
        int initA = a.GetInitiative();
        int initB = b.GetInitiative();
        int initiative = Integer.compare(initB, initA);
        if (initiative != 0) {
            return initiative;
        }
        //initiatives are equal
        int ageA = a.GetWhenBorn();
        int ageB = b.GetWhenBorn();
        return Integer.compare(ageA, ageB);
    }
}
