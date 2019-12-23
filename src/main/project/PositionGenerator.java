package project;

import java.util.Random;


class PositionGenerator {
    final private int originX;
    final private int originY;
    final private int width;
    final private int height;
    final private IWorldMap map;
    final private Vector2d restrictedLowerLeft;
    final private Vector2d restrictedUpperRight;


    PositionGenerator(IWorldMap map, Vector2d lowerLeft, Vector2d upperRight) {
        this(map, lowerLeft, upperRight, new Vector2d(0, 0), new Vector2d(-1, -1));
    }

    PositionGenerator(IWorldMap map, Vector2d lowerLeft, Vector2d upperRight,
                      Vector2d restrictedLowerLeft, Vector2d restrictedUpperRight) {
        this.originX = lowerLeft.x;
        this.originY = lowerLeft.y;
        this.restrictedLowerLeft = restrictedLowerLeft;
        this.restrictedUpperRight = restrictedUpperRight;
        // we are indexing from 0 that's why we add 1
        this.width = upperRight.x - lowerLeft.x + 1;
        this.height = upperRight.y - lowerLeft.y + 1;
        this.map = map;
    }

    // Returns a position that is in rectangle with vertices defined by upperRight and lowerLeft,
    // and and is not in rectangle defined by restrictedUpperRight and restrictedLowerLeft
    Vector2d getRandomPosition() {
        int i = new Random().nextInt(this.width * this.height);
        return getPosition(i);
    }

    Vector2d getPosition(int i) {
        if (i >= this.width * this.height) {
            throw new IllegalArgumentException("invalid argument " + i + " is too big");
        }
        if (i < 0) {
            throw new IllegalArgumentException("invalid argument " + i + " cannot be smaller than 0");
        }

        int buffer = 0; // takes care of infinite loop
        int x = i % this.width + this.originX;
        int y = i / this.width + this.originY;
        Vector2d position = new Vector2d(x, y);
        while (buffer <= this.width * this.height
                && (this.map.isOccupied(position)
                || (this.restrictedLowerLeft.precedes(position) && this.restrictedUpperRight.follows(position)))) {
            i = (i + 1) % (this.height * this.width);
            x = i % this.width + this.originX;
            y = i / this.width + this.originY;
            position = new Vector2d(x, y);
            buffer++;
        }
        if (buffer > this.width * this.height)
            return null;
        return position;
    }
}
