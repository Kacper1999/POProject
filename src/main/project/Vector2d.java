package project;

class Vector2d {
    final int x;
    final int y;

    Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "[" + this.x + "," + this.y + "]";
    }

    boolean precedes(Vector2d v) {
        return (this.x <= v.x && this.y <= v.y);
    }

    boolean follows(Vector2d v) {
        return (this.x >= v.x && this.y >= v.y);
    }

    Vector2d upperRight(Vector2d v) {
        int x = Math.max(this.x, v.x);
        int y = Math.max(this.y, v.y);
        return new Vector2d(x, y);
    }

    Vector2d lowerLeft(Vector2d v) {
        int x = Math.min(this.x, v.x);
        int y = Math.min(this.y, v.y);
        return new Vector2d(x, y);
    }

    Vector2d add(Vector2d v) {
        return new Vector2d(this.x + v.x, this.y + v.y);
    }

    Vector2d subtract(Vector2d v) {
        return new Vector2d(this.x - v.x, this.y - v.y);
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return (that.x == this.x && that.y == this.y);
    }

    Vector2d opposite() {
        return new Vector2d(-this.x, -this.y);
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }
}
