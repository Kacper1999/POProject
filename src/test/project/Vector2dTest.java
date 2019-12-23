package project;

import org.junit.Test;
import project.Vector2d;

import static org.junit.Assert.*;

public class Vector2dTest {
    @Test
    public void testEquals() {
        assertTrue(new Vector2d(1, 2).equals(new Vector2d(1, 2)));
    }

    @Test
    public void testToString() {
        assertEquals("[1,2]", new Vector2d(1, 2).toString());
    }

    @Test
    public void testPrecedes() {
        assertTrue(new Vector2d(1, 2).precedes(new Vector2d(1, 2)));
        assertTrue(new Vector2d(1, 2).precedes(new Vector2d(3, 4)));
        assertFalse(new Vector2d(1, 2).precedes(new Vector2d(0, 1)));
    }

    @Test
    public void testFollows() {
        assertTrue(new Vector2d(1, 2).follows(new Vector2d(1, 2)));
        assertTrue(new Vector2d(1, 2).follows(new Vector2d(0, 1)));
        assertFalse(new Vector2d(1, 2).follows(new Vector2d(2, 3)));
    }

    @Test
    public void testUpperRight() {
        assertEquals(new Vector2d(1, 1).upperRight(new Vector2d(1, 1)), new Vector2d(1, 1));
        assertEquals(new Vector2d(1, 1).upperRight(new Vector2d(0, 0)), new Vector2d(1, 1));
        assertEquals(new Vector2d(1, 1).upperRight(new Vector2d(2, 3)), new Vector2d(2, 3));
        assertEquals(new Vector2d(1, 1).upperRight(new Vector2d(0, 3)), new Vector2d(1, 3));
    }

    @Test
    public void testLowerLeft() {
        assertEquals(new Vector2d(1, 1).lowerLeft(new Vector2d(1, 1)), new Vector2d(1, 1));
        assertEquals(new Vector2d(1, 1).lowerLeft(new Vector2d(0, 0)), new Vector2d(0, 0));
        assertEquals(new Vector2d(1, 1).lowerLeft(new Vector2d(2, 3)), new Vector2d(1, 1));
        assertEquals(new Vector2d(1, 1).lowerLeft(new Vector2d(0, 3)), new Vector2d(0, 1));
    }

    @Test
    public void testAdd() {
        assertEquals(new Vector2d(1, 1).add(new Vector2d(1, 1)), new Vector2d(2, 2));
        assertEquals(new Vector2d(1, 1).add(new Vector2d(0, 0)), new Vector2d(1, 1));
        assertEquals(new Vector2d(1, 1).add(new Vector2d(2, 3)), new Vector2d(3, 4));
        assertEquals(new Vector2d(1, 1).add(new Vector2d(0, 3)), new Vector2d(1, 4));
    }

    @Test
    public void testSubtract() {
        assertEquals(new Vector2d(1, 1).subtract(new Vector2d(1, 1)), new Vector2d(0, 0));
        assertEquals(new Vector2d(1, 1).subtract(new Vector2d(0, 0)), new Vector2d(1, 1));
        assertEquals(new Vector2d(1, 1).subtract(new Vector2d(2, 3)), new Vector2d(-1, -2));
        assertEquals(new Vector2d(1, 1).subtract(new Vector2d(0, 3)), new Vector2d(1, -2));
    }

    @Test
    public void testOpposite() {
        assertEquals(new Vector2d(1, 2).opposite(), new Vector2d(-1, -2));
        assertEquals(new Vector2d(1, 1).opposite(), new Vector2d(-1, -1));
    }
}
