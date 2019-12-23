package project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class MapCell {
    private HashSet<IMapElement> elements;

    MapCell() {
        this.elements = new HashSet<>();
    }

    void add(IMapElement iMapElement) {
        this.elements.add(iMapElement);
    }

    void remove(IMapElement iMapElement) {
        this.elements.remove(iMapElement);
    }

    boolean isEmpty() {
        return this.elements.isEmpty();
    }

    Fruit getFruit() {
        for (IMapElement iMapElement : this.elements) {
            if (iMapElement.getClass() == Fruit.class) {
                return (Fruit) iMapElement;
            }
        }
        return null;
    }

    List<EvolvingAnimal> getStrongestAnimals() {
        int strongest = Integer.MIN_VALUE;
        List<EvolvingAnimal> result = new ArrayList<>();
        for (IMapElement iMapElement : this.elements) {
            if (iMapElement.getClass() == EvolvingAnimal.class) {
                EvolvingAnimal animal = (EvolvingAnimal) iMapElement; // this is ugly
                if (animal.getEnergy() > strongest) {
                    strongest = animal.getEnergy();
                    result.clear();
                    result.add(animal);
                } else if (animal.getEnergy() == strongest) {
                    result.add(animal);
                }
            }
        }
        return result;
    }

    List<EvolvingAnimal> getTwoStrongestAnimals() {
        int strongest = Integer.MIN_VALUE;
        EvolvingAnimal strongestAnimal = null;
        EvolvingAnimal secondStrongestAnimal = null;
        for (IMapElement iMapElement : this.elements) {
            if (iMapElement.getClass() == EvolvingAnimal.class) {
                EvolvingAnimal animal = (EvolvingAnimal) iMapElement;
                if (animal.getEnergy() >= strongest || secondStrongestAnimal == null) {
                    secondStrongestAnimal = strongestAnimal;
                    strongestAnimal = animal;
                    strongest = animal.getEnergy();
                }
            }
        }
        if (secondStrongestAnimal == null) {
            return null;
        }
        ArrayList<EvolvingAnimal> result = new ArrayList<>();
        result.add(strongestAnimal);
        result.add(secondStrongestAnimal);
        return result;
    }

    IMapElement getFirst() {
        return elements.iterator().next();
    }

    public String toString() {
        return this.elements.toString();
    }
}
