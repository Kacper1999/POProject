package project;

public interface IMapElement {
    Vector2d getPosition();

    IWorldMap getMap();

    void removeObserver(IPositionChangeObserver observer);

    void positionChanged(Vector2d oldPosition);
}
