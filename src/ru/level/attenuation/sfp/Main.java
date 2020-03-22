package ru.level.attenuation.sfp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
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
            root.getStylesheets().add(getClass().getResource("style/style.css").toExternalForm());
            Controller controller = loader.getController();
            Button button = controller.buttonM;
            TextArea textArea = controller.text_area;
            textArea.getStyleClass().add("text-area");
            button.setOnAction(event -> {
                try {
                    controller.BBB(stage);
                } catch (Exception e) {
                    System.out.println("button.setOnAction " + e.getMessage());
                }
            });

            stage.setResizable(true);
            stage.setMaxWidth(500);
            stage.setMinWidth(500);
            stage.setMinHeight(200);
            stage.setHeight(640);
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("res/icon.png")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("start" + e.getMessage());
        }
    }
}
