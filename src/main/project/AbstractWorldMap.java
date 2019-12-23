package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractWorldMap implements IPositionChangeObserver, IWorldMap {
    List<Animal> animals;
    HashMap<Vector2d, IMapElement> map;

    AbstractWorldMap() {
        this(new ArrayList<>());
    }

    AbstractWorldMap(List<IMapElement> mapElements) {
        this.animals = new ArrayList<>();
        this.map = new HashMap<>();

        for (IMapElement mapElement : mapElements) {
            map.put(mapElement.getPosition(), mapElement);
        }
    }

    protected abstract Vector2d calcUpperRightBoundary();

    protected abstract Vector2d calcLowerLeftBoundary();

    public String toString() {
        Vector2d lowerLeftBoundary = calcLowerLeftBoundary();
        Vector2d upperRightBoundary = calcUpperRightBoundary();
        MapVisualizer temp = new MapVisualizer(this);
        return temp.draw(lowerLeftBoundary, upperRightBoundary);
    }

    @Override
    public Vector2d newPosition(Vector2d oldPosition, Vector2d move) {
        Vector2d newPosition = oldPosition.add(move);
        if (!isOccupied(newPosition))
            return newPosition;
        return oldPosition;
    }

    @Override
    public boolean place(IMapElement mapElement) {
        if (canMoveTo(mapElement.getPosition())) {
            if (mapElement.getClass().equals(Animal.class)) {
                animals.add((Animal) mapElement);
                ((Animal) mapElement).addObserver(this);
            }
            map.put(mapElement.getPosition(), mapElement);
            return true;
        }
        throw new IllegalArgumentException("Invalid place coordinates");
    }

    @Override
    public void run(MoveDirection[] directions) {
        for (int i = 0; i < directions.length; i++) {
            executeOrder(directions[i], i);
        }
    }

    private void executeOrder(MoveDirection direction, int which_move) {
        Animal animal = animals.get(which_move % animals.size());
        animal.move(direction);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return this.map.get(position);
    }

    @Override
    public void remove(IMapElement mapElement) {
        mapElement.removeObserver(this);
        if (mapElement.getClass() == Animal.class) {
            this.animals.remove(mapElement);
        }
        this.map.remove(mapElement.getPosition());
    }
}
