package project;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo
 */
public interface IWorldMap {
    /**
     * Indicate if any object can move to the given position.
     *
     * @param position The position checked for the movement possibility.
     * @return True if the object can move to that position.
     */
    boolean canMoveTo(Vector2d position);

    /**
     * Place a element on the map, if this element is a animal then also add it to animals list
     *
     * @param mapElement The element to place on the map.
     * @return True if the element was placed. The element cannot be placed if the map is already occupied.
     */
    boolean place(IMapElement mapElement);

    /**
     *
     * @param oldPosition old position of an object
     * @param move the move object wants to do (moving up (0, 1) left(1, 0) e.t.c)
     * @return new position on map or the same if object can't move to desired position
     */
    Vector2d newPosition(Vector2d oldPosition, Vector2d move);

    /**
     * Move the animal on the map according to the provided move directions. Every
     * n-th direction should be sent to the n-th animal on the map.
     *
     * @param directions Array of move directions.
     */
    void run(MoveDirection[] directions);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an object at a given position.
     *
     * @param position The position of the object.
     * @return Object or null if the position is not occupied.
     */
    Object objectAt(Vector2d position);

    /**
     * Removes given object.
     *
     * @param mapElement object to be removed.
     */
    void remove(IMapElement mapElement);
}
