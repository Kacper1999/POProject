package project;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application  {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));

        Button newWindowButton = new Button();
        newWindowButton.setText("Open new primaryStage");
        newWindowButton.setOnAction(l -> Statistics.display("New primaryStage", "You have opened new primaryStage"));

//        Stage stage1 = new Stage();
//        Group group = new Group();
//
//        int rectangleWidth = 20;
//        int rectangleHeight = 20;
//        Rectangle tmp = new Rectangle(0, 0, rectangleWidth, rectangleHeight);
//        tmp.setFill(Color.DARKGREEN);
//        group.getChildren().add(tmp);
//
//        Scene scene = new Scene(group, 300, 300);
//
//        stage1.setScene(scene);
//        stage1.show();
//        try {
//            java.util.concurrent.TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Circle circle1 = new Circle(1.5 * rectangleWidth, rectangleHeight / 2, rectangleWidth / 2, Color.RED);
//        group.getChildren().add(circle1);


        new InitialConditions(primaryStage);

//        grid.getChildren().add(circle1);
//
//
//
//        Button button2 = new Button();
//        button2.setText("Return");
//        button2.setOnAction(l -> primaryStage.setScene(setConditions));
//
//        StackPane layout2 = new StackPane();
//        layout2.getChildren().add(button2);
//
//
//        animation = new Scene(layout2, 850, 750);
    }


    public static void main(String[] args) {
        launch(args);
    }

}
