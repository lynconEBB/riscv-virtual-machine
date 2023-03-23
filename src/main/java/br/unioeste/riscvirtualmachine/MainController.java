package br.unioeste.riscvirtualmachine;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    private HBox header;
    @FXML
    private Button fileChooserBtn;

    private Stage myStage;

    @FXML
    public void initialize() {
        FileChooser fileChooser = new FileChooser();
        fileChooserBtn.setOnAction( e -> {
            File file = fileChooser.showOpenDialog(myStage);
            System.out.println(file);
        });
        Processor processor = new Processor();
        processor.tick();
        processor.tick();
        System.out.println("OLA");
    }

    public void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }
}