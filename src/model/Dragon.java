package model;

import java.util.Random;

public class Dragon extends Character{

    Direction currentD;

    Dragon(Position startLocation){
        super(startLocation);
        newDirection();

    }
    void newDirection(){
        currentD = Direction.values()[new Random().nextInt(Direction.values().length)];
    }

}
