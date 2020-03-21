package ru.level.attenuation.sfp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

public class Controller {

    private final FileChooser fileChooser = new FileChooser();
    private List<String> validExtensions = Collections.singletonList("csv");
    @FXML
    public AnchorPane anchor_pane;
    @FXML
    public VBox vBox;
    @FXML
    public CheckBox check_box;
    @FXML
    public Button buttonM;
    @FXML
    public TextArea text_area;

    private List<String> strings;

    public Controller() {
    }

    @FXML
    private void initialize() {
    }

    @FXML
    private void printLog(List<File> files) {
        try {
            if (files == null || files.isEmpty()) {
                return;
            }
            if (check_box.isSelected()) {
                text_area.clear();
            }
            for (File file : files) {
                setFilePath(file);
                ParseCSV.main(file.getAbsolutePath());
                strings = new ArrayList<>();
                strings.addAll(ParseCSV.getArrString());
                printResult(strings);
            }
        } catch (Exception e) {
            System.out.println("printLog " + e.getMessage());
        }
    }

    @FXML
    private void printResult(List<String> strings) {
        if (strings == null || strings.isEmpty()) {
            return;
        }
        for (String string : strings) {
            System.out.println(string);
            text_area.appendText(string + "\n");
        }
    }

    @FXML
    public void onDragOver(DragEvent dragEvent) {
        if (dragEvent.getGestureSource() != text_area && dragEvent.getDragboard().hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        dragEvent.consume();
    }

    @FXML
    public void onDragDropped(DragEvent dragEvent) {
        Dragboard dragboard = dragEvent.getDragboard();
        boolean success = false;
        if (dragboard.hasFiles()) {
            AAA(dragboard, dragEvent);
            printResult(strings);
            success = true;
        }
        dragEvent.setDropCompleted(success);
        dragEvent.consume();
    }

    private String getExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0 && i < fileName.length() - 1) //if the name is not empty
        {
            return fileName.substring(i + 1).toLowerCase();
        }
        System.out.println(extension);
        return extension;
    }

    @FXML
    private void AAA(Dragboard dragboard, DragEvent dragEvent) {
        try {
            if (validExtensions.containsAll(dragEvent.getDragboard().getFiles().stream().map(file -> getExtension(file.getName())).collect(Collectors.toList()))) {
                for (int i = 0; i < dragboard.getFiles().size(); i++) {
                    System.out.println(dragboard.getFiles().get(i).getAbsolutePath());
                    ParseCSV.main(dragboard.getFiles().get(i).getAbsolutePath());
                }
                strings = new ArrayList<>();
                strings.addAll(ParseCSV.getArrString());
                dragEvent.consume();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (check_box.isSelected()) {
            text_area.clear();
        }
    }

    @FXML
    void BBB(Stage stage) {
        try {

            if (getFilePath().exists()) {
                fileChooser.setInitialDirectory(new File(getFilePath().getParent()));
            } else {
                fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            }
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
            List<File> files = fileChooser.showOpenMultipleDialog(stage);
            printLog(files);
        } catch (Exception e) {
            System.out.println("BBB " + e.getMessage());
        }

    }

    private File getFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return new File(System.getProperty("user.dir"));
        }
    }

    private void setFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (file != null) {
            String s = file.getAbsolutePath();
            prefs.put("filePath", s);
        }
    }

}

