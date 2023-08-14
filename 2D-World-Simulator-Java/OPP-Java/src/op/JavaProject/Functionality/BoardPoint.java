package op.JavaProject.Functionality;

import op.JavaProject.Game.World;

import java.io.Serializable;
import java.util.Random;

public class BoardPoint implements Serializable {
    private int x;
    private int y;

    //default constructor
    public BoardPoint() {
        this.x = 0;
        this.y = 0;
    }

    //constructor with two parameters
    public BoardPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //getting random point in the range of world size
    public void SetRandomPoint(World organismWorld) {
        int randX = GetRandomValue(0, organismWorld.GetWidth());
        int randY = GetRandomValue(0, organismWorld.GetHeight());
        SetX(randX);
        SetY(randY);
    }

    //getting random value
    public static int GetRandomValue(int minimumValue, int maximumValue) {
        Random randomValue = new Random();
        return randomValue.nextInt(maximumValue - minimumValue) + minimumValue;
    }

    public int GetX() {
        return this.x;
    }

    public int GetY() {
        return this.y;
    }

    public void SetX(int x) {
        this.x = x;
    }

    public void SetY(int y) {
        this.y = y;
    }

    //function to compare instances of points
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BoardPoint) {
            BoardPoint other = (BoardPoint) obj;
            return (x == other.x && y == other.y);
        }
        return false;
    }

}
