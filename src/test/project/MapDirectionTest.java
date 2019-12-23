package project;

import org.junit.Test;
import project.MapDirection;
import project.Vector2d;

import static org.junit.Assert.assertEquals;

public class MapDirectionTest {
    @Test
    public void testNext() {
        assertEquals(MapDirection.NORTH.next(), MapDirection.NORTHEAST);
        assertEquals(MapDirection.NORTHEAST.next(), MapDirection.EAST);
        assertEquals(MapDirection.EAST.next(), MapDirection.SOUTHEAST);
        assertEquals(MapDirection.SOUTHEAST.next(), MapDirection.SOUTH);
        assertEquals(MapDirection.SOUTH.next(), MapDirection.SOUTHWEST);
        assertEquals(MapDirection.SOUTHWEST.next(), MapDirection.WEST);
        assertEquals(MapDirection.WEST.next(), MapDirection.NORTHWEST);
        assertEquals(MapDirection.NORTHWEST.next(), MapDirection.NORTH);
    }

    @Test
    public void testPrevious() {
        assertEquals(MapDirection.NORTH.previous(), MapDirection.NORTHWEST);
        assertEquals(MapDirection.NORTHWEST.previous(), MapDirection.WEST);
        assertEquals(MapDirection.WEST.previous(), MapDirection.SOUTHWEST);
        assertEquals(MapDirection.SOUTHWEST.previous(), MapDirection.SOUTH);
        assertEquals(MapDirection.SOUTH.previous(), MapDirection.SOUTHEAST);
        assertEquals(MapDirection.SOUTHEAST.previous(), MapDirection.EAST);
        assertEquals(MapDirection.EAST.previous(), MapDirection.NORTHEAST);
        assertEquals(MapDirection.NORTHEAST.previous(), MapDirection.NORTH);
    }

    @Test
    public void testToVector() {
        assertEquals(MapDirection.NORTH.toVector(), new Vector2d(0, 1));
        assertEquals(MapDirection.NORTHEAST.toVector(), new Vector2d(1, 1));
        assertEquals(MapDirection.EAST.toVector(), new Vector2d(1, 0));
        assertEquals(MapDirection.SOUTHEAST.toVector(), new Vector2d(1, -1));
        assertEquals(MapDirection.SOUTH.toVector(), new Vector2d(0, -1));
        assertEquals(MapDirection.SOUTHWEST.toVector(), new Vector2d(-1, -1));
        assertEquals(MapDirection.WEST.toVector(), new Vector2d(-1, 0));
        assertEquals(MapDirection.NORTHWEST.toVector(), new Vector2d(-1, 1));
    }
}
