package model;

import java.util.ArrayList;
import java.util.Random;

public class Dragon extends Character{
    private Direction currentD;
    Dragon(Position startLocation){
        super(startLocation);
        newDirection();
    }

    public Direction getCurrentD() {
        return currentD;
    }

    void newDirection(){currentD = Direction.values()[new Random().nextInt(Direction.values().length)];}

    void newDirection(boolean up , boolean down, boolean left, boolean right){

        ArrayList<Direction> available = new ArrayList<>();
        if(up)
            available.add(Direction.UP);
        if (down)
            available.add(Direction.DOWN);
        if (left)
            available.add(Direction.LEFT);
        if (right)
            available.add(Direction.RIGHT);

        currentD = available.get(new Random().nextInt(available.size()));
    }
}
