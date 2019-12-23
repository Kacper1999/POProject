package project;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMapElement implements IMapElement {
    Vector2d position;
    private List<IPositionChangeObserver> observers;
    private IWorldMap map;

    AbstractMapElement(IWorldMap map, Vector2d position) {
        this.position = position;
        this.map = map;
        this.observers = new ArrayList<>();
    }

    void addObserver(IPositionChangeObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(IPositionChangeObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void positionChanged(Vector2d oldPosition) {
        for (IPositionChangeObserver observer : this.observers) {
            observer.positionChanged(oldPosition, this);
        }
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public IWorldMap getMap() {
        return this.map;
    }
}
