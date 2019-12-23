package project;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class InitialConditions implements EventHandler {
    private Button createButton;
    private TextField startEnergyTextField;
    private TextField fruitEnergyTextField;
    private TextField mapWidthTextField;
    private TextField mapHeightTextField;
    private TextField jungleRatioTextField;
    private TextField numOfAnimalsTextField;
    private TextField moveEnergyTextField;
    private Stage stage;

    InitialConditions(Stage stage) {
        this.stage = stage;
        GridPane grid = getGrid();
        Scene setConditions = new Scene(grid, 550, 550);
        stage.setScene(setConditions);
        stage.show();
    }

    private GridPane getGrid() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(3, 3, 3, 3));

        int rowIndex = 0;
        int gap = 7;
        int leftColumnIndex = 0;
        int rightColumnIndex = 7;
        Label startEnergyLabel = new Label("Start Energy");
        GridPane.setConstraints(startEnergyLabel, leftColumnIndex, rowIndex);
        startEnergyTextField = new TextField("1");
        startEnergyTextField.setPromptText("Int only");
        GridPane.setConstraints(startEnergyTextField, leftColumnIndex, rowIndex + 1);

        Label fruitEnergyLabel = new Label("Fruit energy");
        GridPane.setConstraints(fruitEnergyLabel, rightColumnIndex, rowIndex);
        fruitEnergyTextField = new TextField("1");
        fruitEnergyTextField.setPromptText("Int only");
        GridPane.setConstraints(fruitEnergyTextField, rightColumnIndex, rowIndex + 1);
        rowIndex += gap;

        Label mapWidthLabel = new Label("Map width");
        GridPane.setConstraints(mapWidthLabel, leftColumnIndex, rowIndex);
        mapWidthTextField = new TextField("4");
        mapWidthTextField.setPromptText("Int only");
        GridPane.setConstraints(mapWidthTextField, leftColumnIndex, rowIndex + 1);

        Label mapHeightLabel = new Label("Map height");
        GridPane.setConstraints(mapHeightLabel, rightColumnIndex, rowIndex);
        mapHeightTextField = new TextField("4");
        mapHeightTextField.setPromptText("Int only");
        GridPane.setConstraints(mapHeightTextField, rightColumnIndex, rowIndex + 1);
        rowIndex += gap;

        Label jungleRatioLabel = new Label("Jungle ratio");
        GridPane.setConstraints(jungleRatioLabel, leftColumnIndex, rowIndex);
        jungleRatioTextField = new TextField("0.5");
        jungleRatioTextField.setPromptText("0 < n < 1");
        GridPane.setConstraints(jungleRatioTextField, leftColumnIndex, rowIndex + 1);

        Label numOfAnimalsLabel = new Label("Number of starting animals");
        GridPane.setConstraints(numOfAnimalsLabel, rightColumnIndex, rowIndex);
        numOfAnimalsTextField = new TextField("7");
        numOfAnimalsTextField.setPromptText("Int only");
        GridPane.setConstraints(numOfAnimalsTextField, rightColumnIndex, rowIndex + 1);
        rowIndex += gap;

        Label moveEnergyLabel = new Label("Move Energy");
        GridPane.setConstraints(moveEnergyLabel, (rightColumnIndex + leftColumnIndex) / 2, rowIndex);
        moveEnergyTextField = new TextField("1");
        moveEnergyTextField.setPromptText("Int only");
        GridPane.setConstraints(moveEnergyTextField, (rightColumnIndex + leftColumnIndex) / 2, rowIndex + 1);

        createButton = new Button();
        createButton.setText("Create World");
        createButton.setOnAction(this);
        GridPane.setConstraints(createButton, (rightColumnIndex + leftColumnIndex) / 2, 40);

        grid.setVgap(8);
        grid.setHgap(10);
        grid.getChildren().addAll(startEnergyLabel, startEnergyTextField,
                fruitEnergyLabel, fruitEnergyTextField,
                mapWidthLabel, mapWidthTextField,
                mapHeightLabel, mapHeightTextField,
                jungleRatioLabel, jungleRatioTextField,
                numOfAnimalsLabel, numOfAnimalsTextField,
                moveEnergyLabel, moveEnergyTextField,
                createButton);
        return grid;
    }

    @Override
    public void handle(Event event) {
        if (event.getSource() == createButton) {
            int moveEnergy;
            int startEnergy;
            int fruitEnergy;
            int mapWidth;
            int mapHeight;
            int numOfAnimals;
            double jungleRatio;
            try {
                moveEnergy = Integer.parseInt(moveEnergyTextField.getText());
                startEnergy = Integer.parseInt(startEnergyTextField.getText());
                fruitEnergy = Integer.parseInt(fruitEnergyTextField.getText());
                mapWidth = Integer.parseInt(mapWidthTextField.getText());
                mapHeight = Integer.parseInt(mapHeightTextField.getText());
                numOfAnimals = Integer.parseInt(numOfAnimalsTextField.getText());
            } catch (NumberFormatException e) {
                System.out.println("You need to input an int.");
                return;
            }
            try {
                jungleRatio = Double.parseDouble(jungleRatioTextField.getText());
            } catch (NumberFormatException e) {
                System.out.println("Jungle ratio needs to be a double.");
                return;
            }
            if (jungleRatio <= 0 || jungleRatio >= 1) {
                System.out.println("Jungle ratio needs to be smaller than 1 and bigger than 0.");
                return;
            }
            stage.close();
            new Animation(numOfAnimals, mapWidth, mapHeight, jungleRatio, fruitEnergy, moveEnergy, startEnergy);
        }
    }
}
