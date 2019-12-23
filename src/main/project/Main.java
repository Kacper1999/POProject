package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application  {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));

        Button newWindowButton = new Button();
        newWindowButton.setText("Open new primaryStage");
        newWindowButton.setOnAction(l -> Statistics.display("New primaryStage", "You have opened new primaryStage"));


        new InitialConditions(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }

}
