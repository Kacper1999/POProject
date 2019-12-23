package project;

import java.util.Random;


class EvolvingAnimal extends Animal implements IMapElement {
    private final Genes genes;
    final private int moveEnergy;
    final private int startEnergy;
    private int age;
    private int numOfChildren;
    private int energy;

    EvolvingAnimal(IWorldMap map, Vector2d position, Genes genes, int energy, int moveEnergy, int startEnergy) {
        super(map, position);
        int rotation = new Random().nextInt(Rotation.values().length);
        this.rotate(Rotation.values()[rotation]);
        this.age = 0;
        this.numOfChildren = 0;
        this.genes = genes;
        this.energy = energy;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
    }

    @Override
    void move(MoveDirection moveDirection) {
        int rotation = this.genes.genes[new Random().nextInt(genes.size)];
        this.rotate(Rotation.values()[rotation]);
        super.move(moveDirection);
        this.age++;
        loseEnergy(this.moveEnergy);
    }

    void eat(int energy) {
        this.energy += energy;
    }

    private void loseEnergy(int energy) {
        this.energy -= energy;
        if (this.energy <= 0) {
            this.getMap().remove(this);
        }
    }

    EvolvingAnimal breed(EvolvingAnimal secAnimal) {
        if (!(this.getEnergy() > this.startEnergy / 2 && secAnimal.getEnergy() > this.startEnergy / 2)) {
            int babyEnergy = this.getEnergy() / 4 + secAnimal.getEnergy() / 4;
            this.loseEnergy(this.getEnergy() / 4);
            secAnimal.loseEnergy(secAnimal.getEnergy() / 4);
            this.numOfChildren++;
            secAnimal.numOfChildren++;
            return new EvolvingAnimal(this.getMap(), this.position,
                    this.genes.mergeGenes(secAnimal.genes), babyEnergy, this.moveEnergy, this.startEnergy);
        }
        return null;
    }

    int getEnergy() {
        return this.energy;
    }
}
