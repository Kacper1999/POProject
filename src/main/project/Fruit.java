package project;

public class Fruit extends AbstractMapElement implements IMapElement {
    final private int energy;

    Fruit(IWorldMap map, Vector2d position, int energy) {
        super(map, position);
        this.energy = energy;
    }

    public String toString() {
        return "*";
    }

    int getEnergy() {
        return this.energy;
    }
}
