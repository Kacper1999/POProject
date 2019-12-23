package project;

import java.util.HashMap;
import java.util.List;

public class RolledMapWithJungle extends AbstractWorldMap implements IWorldMap {
    final private Vector2d mapLowerLeft;
    final private Vector2d mapUpperRight;
    final private Vector2d jungleLowerLeft;
    final private Vector2d jungleUpperRight;
    final private int fruitEnergy;
    final private int width;
    final private int height;
    final private int jungleWidth;
    final private int jungleHeight;
    HashMap<Vector2d, MapCell> map;


    RolledMapWithJungle(int width, int height, double jungleRatio, int fruitEnergy) {
        this(width, height, (int) (width * jungleRatio), (int) (height * jungleRatio), fruitEnergy);
    }

    RolledMapWithJungle(int width, int height, int jungleWidth, int jungleHeight, int fruitEnergy) {
        if (width <= jungleWidth || height <= jungleHeight) {
            throw new IllegalArgumentException("invalid jungle width or jungle height " +
                    "(bigger or equal than maps width/height)");
        }
        this.fruitEnergy = fruitEnergy;
        this.map = new HashMap<>();
        this.width = width;
        this.height = height;
        this.jungleWidth = jungleWidth;
        this.jungleHeight = jungleHeight;
        this.jungleLowerLeft = new Vector2d((width - jungleWidth) / 2, (height - jungleHeight) / 2);
        // in jungleUpperRight we need to remember that we are indexing from 0
        // that's why we are subtracting 1 from jungleWidth / Height
        this.jungleUpperRight = new Vector2d(jungleLowerLeft.x + (jungleWidth - 1), jungleLowerLeft.y + (jungleHeight - 1));
        this.mapLowerLeft = new Vector2d(0, 0);
        this.mapUpperRight = new Vector2d(width - 1, height - 1);
    }

    Vector2d getJungleLowerLeft() {
        return this.jungleLowerLeft;
    }

    Vector2d getJungleUpperRight() {
        return this.jungleUpperRight;
    }

    void newDay() {
        moveAnimals();
        eat();
        breed();
        placeFruits();
    }

    private void breed() {
        for (Animal animal : super.animals) {
            List<EvolvingAnimal> twoStrongestAnimals = this.map.get(animal.getPosition()).getTwoStrongestAnimals();
            if (twoStrongestAnimals != null) {
                EvolvingAnimal baby = twoStrongestAnimals.get(0).breed(twoStrongestAnimals.get((1)));
                this.place(baby);
            }
        }
    }

    private void moveAnimals() {
        for (Animal animal : super.animals) {
            animal.move(MoveDirection.FORWARD);
        }
    }

    void eat() {
        for (Animal animal : super.animals) {
            MapCell mapCell = this.map.get(animal.getPosition());
            Fruit fruit = mapCell.getFruit();
            if (fruit != null) {
                this.remove(fruit);
                List<EvolvingAnimal> strongestAnimals = mapCell.getStrongestAnimals();
                for (EvolvingAnimal strongestAnimal : strongestAnimals) {
                    strongestAnimal.eat(fruit.getEnergy() / strongestAnimals.size());
                }
            }
        }
    }

    void placeFruits() {
        if (this.isSteppeFull()) {
            return;
        }
        PositionGenerator steppeRPG = new PositionGenerator(this, mapLowerLeft, mapUpperRight, jungleLowerLeft, jungleUpperRight);
        Vector2d pos1 = steppeRPG.getRandomPosition();
        Fruit fruit1 = new Fruit(this, pos1, this.fruitEnergy);
        this.place(fruit1);

        if (this.isJungleFull()) {
            return;
        }
        PositionGenerator jungleRPG = new PositionGenerator(this, jungleLowerLeft, jungleUpperRight);
        Vector2d pos2 = jungleRPG.getRandomPosition();
        Fruit fruit2 = new Fruit(this, pos2, this.fruitEnergy);
        this.place(fruit2);
    }

    boolean isFull() {
        return this.map.size() == this.width * this.height;
    }

    boolean isJungleFull() {
        if (this.isFull()) {
            return true;
        }
        for (int i = 0; i < this.jungleWidth; i++) {
            for (int j = 0; j < this.jungleHeight; j++) {
                if (!isOccupied(new Vector2d(i + this.jungleLowerLeft.x, j + this.jungleLowerLeft.y))) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean isSteppeFull() {
        if (this.isFull()) {
            return true;
        }
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                Vector2d pos = new Vector2d(i, j);
                if (!(pos.follows(jungleLowerLeft) && pos.precedes(jungleUpperRight))) {
                    if (!isOccupied(pos)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, IMapElement iMapElement) {
        Vector2d newPosition = iMapElement.getPosition();
        this.map.get(oldPosition).remove(iMapElement);
        super.map.remove(oldPosition);
        this.map.computeIfAbsent(newPosition, k -> new MapCell());
        this.map.get(newPosition).add(iMapElement);
        super.map.put(newPosition, iMapElement);
    }

    @Override
    protected Vector2d calcUpperRightBoundary() {
        return this.mapUpperRight;
    }

    @Override
    protected Vector2d calcLowerLeftBoundary() {
        return this.mapLowerLeft;
    }

    @Override
    public Vector2d newPosition(Vector2d oldPosition, Vector2d move) {
        Vector2d newPosition;
        Vector2d tmp = oldPosition.add(move);
        int x = tmp.x % this.width;
        int y = tmp.y % this.height;
        if (tmp.x < 0) {
            x = this.width + tmp.x;
        }
        if (tmp.y < 0) {
            y = this.height + tmp.y;
        }
        newPosition = new Vector2d(x, y);
        if (this.canMoveTo(newPosition)) {
            return newPosition;
        }
        return oldPosition;
    }

    @Override
    public void remove(IMapElement iMapElement) {
        this.map.get(iMapElement.getPosition()).remove(iMapElement);
        super.map.remove(iMapElement.getPosition());
        iMapElement.removeObserver(this);
        if (iMapElement instanceof Animal) {
            super.animals.remove(iMapElement);
        }
    }

    @Override
    public boolean place(IMapElement iMapElement) {
        Vector2d pos = iMapElement.getPosition();
        if (canMoveTo(pos)) {
            if (iMapElement instanceof Animal) {
                super.animals.add((Animal) iMapElement);
                ((Animal) iMapElement).addObserver(this);
            }
            super.map.put(iMapElement.getPosition(), iMapElement);
            this.map.computeIfAbsent(pos, k -> new MapCell());
            this.map.get(pos).add(iMapElement);
            return true;
        }
        throw new IllegalArgumentException("Invalid place coordinates");
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(this.mapLowerLeft) && position.precedes(this.mapUpperRight);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        if (this.map.get(position) == null) {
            return false;
        }
        return !this.map.get(position).isEmpty();
    }

    @Override
    public Object objectAt(Vector2d position) {
        return this.map.get(position);
    }
}
