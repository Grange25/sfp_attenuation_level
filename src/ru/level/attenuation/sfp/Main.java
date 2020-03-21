package ru.level.attenuation.sfp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "main.fxml"));
            stage.setTitle("Проверка уровня затухания волны SFP");
            Parent root = loader.load();
            Controller controller = loader.getController();
            Button button = controller.buttonM;

            button.setOnAction(event -> {
                try {
                    controller.BBB(stage);
                } catch (Exception e) {
                    System.out.println("button.setOnAction " + e.getMessage());
                }
            });

            stage.setResizable(true);
            stage.setMaxWidth(480);
            stage.setMinWidth(480);
            stage.setMinHeight(200);
            stage.setHeight(640);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("start" + e.getMessage());
        }
    }
}
