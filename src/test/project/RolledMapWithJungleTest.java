package project;

import org.junit.Test;
import project.*;

import static org.junit.Assert.*;

public class RolledMapWithJungleTest {
    private int defWidth = 8;
    private int defHeight = 8;
    private int defJungleWidth = 2;
    private int defJungleHeight = 2;
    private int startEnergy = 100;
    private int energy = 100;
    private int moveEnergy = 5;
    private int fruitEnergy = 5;
    private Vector2d defInitialPosition = new Vector2d(2, 2);
    private RolledMapWithJungle defMap = new RolledMapWithJungle(defWidth, defHeight, defJungleWidth, defJungleHeight, fruitEnergy);
    private Genes defGenes = new Genes(new int[]{1, 1, 2, 3, 3, 4, 4, 4, 5, 5, 7, 7, 7});
    private EvolvingAnimal defTestAnimal = new EvolvingAnimal(defMap, defInitialPosition, defGenes, energy, moveEnergy, startEnergy);

    private RolledMapWithJungle smallMap = new RolledMapWithJungle(3, 3, 1, 1, fruitEnergy);

    @Test
    public void placeTest() {
        assertTrue(defMap.place(defTestAnimal));
        Vector2d pos = new Vector2d(3, 4);
        assertTrue(defMap.place(new Animal(defMap, pos)));
        assertTrue(defMap.place(new Animal(defMap, pos)));
        Object tmp = defMap.objectAt(pos);
        assertEquals("[N, N]", tmp.toString());
    }

    @Test
    public void movingOutOfMapTest() {
        // RolledMap should position animal on the other side of the map if it gets to the edge
        Vector2d pos1 = new Vector2d(defWidth - 1, defHeight - 1);
        Vector2d move1 = new Vector2d(1, 1);
        assertEquals(new Vector2d(0, 0), defMap.newPosition(pos1, move1));

        Vector2d pos2 = new Vector2d(0, 0);
        Vector2d move2 = new Vector2d(-1, -1);
        assertEquals(new Vector2d(defWidth - 1, defHeight - 1), defMap.newPosition(pos2, move2));
    }

    @Test
    public void moveTest() {
        Animal animal = new Animal(smallMap, new Vector2d(0, 0));
        smallMap.place(animal);
        animal.move(MoveDirection.FORWARD);
        assertFalse(smallMap.isOccupied(new Vector2d(0, 0)));
    }

    @Test
    public void isFullTest() {
        smallMap.place(new Animal(smallMap, new Vector2d(0, 0)));
        smallMap.place(new Animal(smallMap, new Vector2d(1, 0)));
        smallMap.place(new Animal(smallMap, new Vector2d(2, 0)));
        smallMap.place(new Animal(smallMap, new Vector2d(0, 1)));
        smallMap.place(new Animal(smallMap, new Vector2d(1, 1)));
        smallMap.place(new Animal(smallMap, new Vector2d(2, 1)));
        smallMap.place(new Animal(smallMap, new Vector2d(0, 2)));
        smallMap.place(new Animal(smallMap, new Vector2d(1, 2)));
        smallMap.place(new Animal(smallMap, new Vector2d(1, 2)));

        System.out.println(smallMap.map.size());
        assertFalse(smallMap.isFull());
        smallMap.place(new Animal(smallMap, new Vector2d(2, 2)));
        System.out.println(smallMap.map.size());
        assertTrue(smallMap.isFull());
    }

    @Test
    public void isJungleFullTest() {
        assertFalse(smallMap.isJungleFull());
        smallMap.place(new Animal(smallMap, new Vector2d(1, 1)));
        assertTrue(smallMap.isJungleFull());
    }

    @Test
    public void isSteppeFullTest() {
        smallMap.place(new Animal(smallMap, new Vector2d(0, 0)));
        smallMap.place(new Animal(smallMap, new Vector2d(1, 0)));
        smallMap.place(new Animal(smallMap, new Vector2d(2, 0)));
        smallMap.place(new Animal(smallMap, new Vector2d(0, 1)));
        smallMap.place(new Animal(smallMap, new Vector2d(2, 1)));
        smallMap.place(new Animal(smallMap, new Vector2d(0, 2)));
        smallMap.place(new Animal(smallMap, new Vector2d(1, 2)));
        smallMap.place(new Animal(smallMap, new Vector2d(1, 2)));
        assertFalse(smallMap.isSteppeFull());

        smallMap.place(new Animal(smallMap, new Vector2d(2, 2)));
        assertTrue(smallMap.isSteppeFull());

    }

    @Test
    public void placeFruitsTest() {
        smallMap.place(new Animal(smallMap, new Vector2d(0, 0)));
        smallMap.place(new Animal(smallMap, new Vector2d(1, 0)));
        smallMap.place(new Animal(smallMap, new Vector2d(2, 0)));
        smallMap.place(new Animal(smallMap, new Vector2d(0, 1)));
        smallMap.place(new Animal(smallMap, new Vector2d(2, 1)));
        smallMap.place(new Animal(smallMap, new Vector2d(0, 2)));
        smallMap.place(new Animal(smallMap, new Vector2d(1, 2)));
        smallMap.place(new Animal(smallMap, new Vector2d(1, 2)));
        assertFalse(smallMap.isFull());
        smallMap.placeFruits();
        assertTrue(smallMap.isFull());
    }

    @Test
    public void removeTest() {
        Animal animal = new Animal(smallMap, new Vector2d(0, 0));
        Animal animal1 = new Animal(smallMap, new Vector2d(0, 0));
        smallMap.place(animal);
        smallMap.place(animal1);
        Fruit fruit = new Fruit(smallMap, new Vector2d(1, 2), fruitEnergy);
        smallMap.place(fruit);
        smallMap.remove(fruit);
        smallMap.remove(animal);

        assertEquals("[N]", smallMap.objectAt(new Vector2d(0, 0)).toString());
        // I don't know how to bypass this type conversion
        assertTrue(((MapCell) smallMap.objectAt(new Vector2d(1, 2))).isEmpty());
        smallMap.remove(animal1);
        assertTrue(((MapCell) smallMap.objectAt(new Vector2d(1, 2))).isEmpty());
        assertFalse(smallMap.animals.contains(animal));
    }

    @Test
    public void eatTest() {
        Vector2d pos1 = new Vector2d(0, 0);
        EvolvingAnimal animal1 = new EvolvingAnimal(smallMap, pos1, defGenes, 15, 3, 30);
        EvolvingAnimal animal2 = new EvolvingAnimal(smallMap, pos1, defGenes, 5, 3, 30);
        EvolvingAnimal animal3 = new EvolvingAnimal(smallMap, pos1, defGenes, 15, 3, 30);
        EvolvingAnimal animal4 = new EvolvingAnimal(smallMap, pos1, defGenes, 25, 3, 30);
        EvolvingAnimal animal5 = new EvolvingAnimal(smallMap, pos1, defGenes, 25, 3, 30);
        EvolvingAnimal[] animals1 = {animal1, animal2, animal3, animal4, animal5};
        for (EvolvingAnimal animal : animals1) {
            smallMap.place(animal);
        }
        Fruit fruit1 = new Fruit(smallMap, pos1, 5);
        smallMap.place(fruit1);

        Vector2d pos2 = new Vector2d(1, 1);
        EvolvingAnimal animal6 = new EvolvingAnimal(smallMap, pos2, defGenes, 15, 3, 30);
        EvolvingAnimal animal7 = new EvolvingAnimal(smallMap, pos2, defGenes, 5, 3, 30);
        EvolvingAnimal animal8 = new EvolvingAnimal(smallMap, pos2, defGenes, 15, 3, 30);
        EvolvingAnimal animal9 = new EvolvingAnimal(smallMap, pos2, defGenes, 20, 3, 30);
        EvolvingAnimal animal10 = new EvolvingAnimal(smallMap, pos2, defGenes, 15, 3, 30);
        EvolvingAnimal[] animals2 = {animal6, animal7, animal8, animal9, animal10};
        for (EvolvingAnimal animal : animals2) {
            smallMap.place(animal);
        }
        Fruit fruit2 = new Fruit(smallMap, pos2, 5);
        smallMap.place(fruit2);

        smallMap.eat();

        assertEquals(27, animal4.getEnergy());
        assertEquals(27, animal5.getEnergy());
        assertEquals(25, animal9.getEnergy());
    }
}
