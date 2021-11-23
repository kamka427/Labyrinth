package model;

public class Character {
    private Position location;
    private Position startLocation;

    public Character(Position startLocation) {
        this.startLocation = startLocation;
        this.location = startLocation;
    }

    public Position getLocation() {
        return location;
    }

    public void setLocation(Position location) {
        this.location = location;
    }

    public Position getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Position startLocation) {
        this.startLocation = startLocation;
    }

    public Position moveChar(Direction d){
        return new Position(location.x + d.x, location.y + d.y);
    }
}
