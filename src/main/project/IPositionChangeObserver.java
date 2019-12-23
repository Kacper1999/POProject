package project;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, IMapElement iMapElement);
}
