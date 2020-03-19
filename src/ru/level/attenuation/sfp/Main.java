package ru.level.attenuation.sfp;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {


    @Override
    public void start(Stage stage) {

        stage.setTitle("Проверка уровня затухания волны SFP");
        stage.setMaxWidth(480);
        stage.setMinWidth(420);
        stage.setMinHeight(320);
        stage.setWidth(460);
        stage.setHeight(640);
        Scene scene = new Scene(new Group());

        VBox vBox1 = new VBox();
        VBox vBox2 = new VBox();

        CheckBox checkBox = new CheckBox();
        String sCB = String.format("Если включён, то произойдет перезапись содержимого окна%nпри следующем перетаскивании.");
        checkBox.setText(sCB);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(vBox2);

        vBox1.getChildren().addAll(checkBox, scrollPane);
        scrollPane.setVisible(false);
        vBox2.setAlignment(Pos.CENTER);

        vBox1.setOnDragOver(event -> {
            if (event.getGestureSource() != scrollPane && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        vBox1.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                try {
                    ParseCSV.main(db.getUrl().substring(6));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                scrollPane.setVisible(true);

                if (checkBox.isSelected()) {
                    vBox2.getChildren().clear();
                }

                for (int i = 0; i < ParseCSV.getArrString().size(); i++) {

                    String s = ParseCSV.getArrString().get(i);

                    Text text = new Text();
                    text.setText(s);
//                    text.setFill(Color.web("#191970"));
                    text.setStyle("-fx-font-weight: bold");
                    text.setStyle("-fx-font: 13 arial;");

                    System.out.println(s);

                    vBox2.getChildren().add(text);

                }
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

        scene.setRoot(vBox1);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
