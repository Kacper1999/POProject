package project;

public class Animal extends AbstractMapElement implements IMapElement {
    private MapDirection direction = MapDirection.NORTH;
    private IWorldMap map;

    Animal(IWorldMap map) {
        this(map, new Vector2d(2,2));
    }

    Animal(IWorldMap map, Vector2d initialPosition) {
        super(map, initialPosition);
        this.map = map;
    }

    public String toString() {
        switch (this.direction) {
            case NORTH:
                return ("N");
            case NORTHEAST:
                return "NE";
            case EAST:
                return ("E");
            case SOUTHEAST:
                return ("SE");
            case SOUTH:
                return ("S");
            case SOUTHWEST:
                return ("SW");
            case WEST:
                return ("W");
            case NORTHWEST:
                return ("NW");
            default:
                return ("");
        }
    }

    MapDirection getDirection() {
        return (this.direction);
    }

    void rotate(Rotation rotation) {
        this.direction = this.direction.rotatedBy(rotation);
    }

    void move(MoveDirection moveDirection) {
        Vector2d newPosition;
        MapDirection tmpDirection;
        switch (moveDirection) {
            case RIGHT:
                tmpDirection = this.direction.rotatedBy(Rotation.ROTATE90);
                newPosition = this.map.newPosition(this.position, tmpDirection.toVector());
                if (this.map.canMoveTo(newPosition)) {
                    Vector2d oldPosition = this.position;
                    this.position = newPosition;
                    positionChanged(oldPosition);
                }
                break;
            case LEFT:
                tmpDirection = this.direction.rotatedBy(Rotation.ROTATE270);
                newPosition = this.map.newPosition(this.position, tmpDirection.toVector());
                if (this.map.canMoveTo(newPosition)) {
                    Vector2d oldPosition = this.position;
                    this.position = newPosition;
                    positionChanged(oldPosition);
                }
                break;
            case BACKWARD:
                tmpDirection = this.direction.rotatedBy(Rotation.ROTATE180);
                newPosition = this.map.newPosition(this.position, tmpDirection.toVector());
                if (this.map.canMoveTo(newPosition)) {
                    Vector2d oldPosition = this.position;
                    this.position = newPosition;
                    positionChanged(oldPosition);
                }
                break;
            case FORWARD:
                newPosition = this.map.newPosition(this.position, this.direction.toVector());
                if (this.map.canMoveTo(newPosition)) {
                    Vector2d oldPosition = this.position;
                    this.position = newPosition;
                    positionChanged(oldPosition);
                }
                break;
            default:
                break;
        }
    }
}
