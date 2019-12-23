package project;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.Random;

class Animation {
    private final int mapWidth;
    private final int mapHeight;
    private final RolledMapWithJungle map1;
    private final RolledMapWithJungle map2;

    Animation(int numOfAnimals, int mapWidth, int mapHeight, double jungleRatio, int fruitEnergy, int moveEnergy, int startEnergy) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;

        this.map1 = new RolledMapWithJungle(mapWidth, mapHeight, jungleRatio, fruitEnergy);
        int genesSize = 32;
        for (int i = 0; i < numOfAnimals; i++) {
            int x = new Random().nextInt(mapWidth);
            int y = new Random().nextInt(mapHeight);
            Genes genes = new Genes(genesSize);
            EvolvingAnimal animal = new EvolvingAnimal(map1, new Vector2d(x, y), genes, startEnergy, moveEnergy, startEnergy);
            map1.place(animal);
        }

        this.map2 = new RolledMapWithJungle(mapWidth, mapHeight, jungleRatio, fruitEnergy);
        for (int i = 0; i < numOfAnimals; i++) {
            int x = new Random().nextInt(mapWidth);
            int y = new Random().nextInt(mapHeight);
            Genes genes = new Genes(genesSize);
            EvolvingAnimal animal = new EvolvingAnimal(map1, new Vector2d(x, y), genes, startEnergy, moveEnergy, startEnergy);
            map2.place(animal);
        }
        this.animate();
    }

    private void animate() {
        int sceneWidth = 1200;
        int sceneHeight = 800;
        int mapsWidth = sceneWidth / 2 - 50;

        Stage stage = new Stage();
        Group group = new Group();


        Rectangle map1R = new Rectangle(0, 0, mapsWidth, sceneHeight);
        map1R.setFill(Color.LIGHTGREEN);
        Rectangle map2R = new Rectangle(sceneWidth - mapsWidth, 0, mapsWidth, sceneHeight);
        map2R.setFill(Color.LIGHTGREEN);

        Scene animation = new Scene(group, sceneWidth, sceneHeight);
        stage.setScene(animation);

        stage.show();

        // I have no idea why this doesn't work
        int era = 0;
        while (era < 10) {
            group.getChildren().clear();
            group.getChildren().addAll(map1R, map2R);
            embedMap(this.map1, group, 0, mapsWidth, sceneHeight);
            embedMap(this.map2, group, sceneWidth - mapsWidth, mapsWidth, sceneHeight);
            this.map1.newDay();
            this.map2.newDay();
            era++;
        }
        stage.close();
    }

    // minX says where the map rectangle starts (determines if we want to paint on the left or right map)
    private void embedMap (RolledMapWithJungle map, Group root,
                           int minX, double mapRectangleWidth, double mapRectangleHeight) {
        double rectangleWidth = (mapRectangleWidth) / this.mapWidth;
        double rectangleHeight = (mapRectangleHeight) / this.mapHeight;

        for (int i = 0; i < this.mapWidth; i++) {
            for (int j = 0; j < this.mapHeight; j++) {
                Vector2d pos = new Vector2d(i , j);
                MapCell mapCell = map.map.get(pos);
                if (mapCell == null) {
                    continue;
                }
                if (!mapCell.isEmpty()) {
                    IMapElement element = mapCell.getFirst();
                    if (element instanceof EvolvingAnimal) {
                        Rectangle animal = new Rectangle(i * rectangleWidth + minX, j * rectangleHeight,
                                rectangleWidth, rectangleHeight);
                        animal.setFill(Color.RED);
                        root.getChildren().add(animal);
                    } else if (element instanceof Fruit) {
                        Circle fruit = new Circle((((double) i) + 0.5 + minX) * rectangleWidth,
                                (((double) j) - 0.5) * rectangleHeight, rectangleWidth / 2, Color.DARKGREEN);
                        root.getChildren().add(fruit);
                    }
                }
            }
        }
    }
}
