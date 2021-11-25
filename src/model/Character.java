package model;

import java.util.Objects;

public class Character {
    private Position location;
    private final Position startLoc;

    public Character(Position startLocation) {
        this.location = startLocation;
        this.startLoc = startLocation;
    }

    public Position getStartLoc() {
        return startLoc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return Objects.equals(getLocation(), character.getLocation());
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public Position getLocation() {
        return location;
    }

    public void moveChar(Direction d) {
        location = new Position(location.x + d.x, location.y + d.y);
    }
}
