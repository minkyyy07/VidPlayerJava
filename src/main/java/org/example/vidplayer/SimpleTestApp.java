package org.example.vidplayer;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SimpleTestApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Тест JavaFX на macOS");
        Button button = new Button("Нажми меня");

        button.setOnAction(e -> label.setText("Кнопка нажата!"));

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(label, button);

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("Простой тест JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
