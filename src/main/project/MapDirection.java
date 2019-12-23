package project;

enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public String toString() {
        switch (this) {
            case NORTH:
                return "North";
            case NORTHEAST:
                return "North-east";
            case EAST:
                return "East";
            case SOUTHEAST:
                return "South-east";
            case SOUTH:
                return "South";
            case SOUTHWEST:
                return "South-west";
            case WEST:
                return "West";
            case NORTHWEST:
                return "North-west";
            default:
                return "";
        }
    }

    Vector2d toVector() {
        switch (this) {
            case NORTH:
                return new Vector2d(0, 1);
            case NORTHEAST:
                return new Vector2d(1, 1);
            case EAST:
                return new Vector2d(1, 0);
            case SOUTHEAST:
                return new Vector2d(1, -1);
            case SOUTH:
                return new Vector2d(0, -1);
            case SOUTHWEST:
                return new Vector2d(-1, -1);
            case WEST:
                return new Vector2d(-1, 0);
            case NORTHWEST:
                return new Vector2d(-1, 1);
            default:
                return new Vector2d(0, 0);
        }
    }

    MapDirection next() {
        switch (this) {
            case NORTH:
                return NORTHEAST;
            case NORTHEAST:
                return EAST;
            case EAST:
                return SOUTHEAST;
            case SOUTHEAST:
                return SOUTH;
            case SOUTH:
                return SOUTHWEST;
            case SOUTHWEST:
                return WEST;
            case WEST:
                return NORTHWEST;
            case NORTHWEST:
                return NORTH;
            default:
                return this;
        }
    }

    MapDirection previous() {
        switch (this) {
            case NORTH:
                return NORTHWEST;
            case NORTHWEST:
                return WEST;
            case WEST:
                return SOUTHWEST;
            case SOUTHWEST:
                return SOUTH;
            case SOUTH:
                return SOUTHEAST;
            case SOUTHEAST:
                return EAST;
            case EAST:
                return NORTHEAST;
            case NORTHEAST:
                return NORTH;
            default:
                return this;
        }
    }

    MapDirection rotatedByNTimes45(int n) {
        return this.rotatedBy(Rotation.values()[n]);
    }

    MapDirection rotatedBy(Rotation rotation) {
        MapDirection result = this;
        for (int i = 0; i < rotation.ordinal(); i++) {
            result = result.next();
        }
        return result;
    }
}
