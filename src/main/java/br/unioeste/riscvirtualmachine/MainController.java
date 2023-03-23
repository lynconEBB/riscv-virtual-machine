package br.unioeste.riscvirtualmachine;

import br.unioeste.riscvirtualmachine.utils.FlagsUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML
    private HBox flagContainer;

    @FXML
    private Button fileChooserBtn;
    @FXML
    private Button startBtn;
    @FXML
    private Button nextBtn;
    @FXML
    private Label fileLabel;
    @FXML
    private VBox registerContainer1;
    @FXML
    private VBox registerContainer2;

    private Stage myStage;

    private List<CheckBox> flagCheckBoxes;

    private List<Label> registerLabels;

    private Processor processor;

    @FXML
    public void initialize() {
        FileChooser fileChooser = new FileChooser();
        fileChooserBtn.setOnAction( e -> {
            File file = fileChooser.showOpenDialog(myStage);
            if (file != null) {
                fileLabel.setText(file.getName());
                startBtn.setDisable(false);
                nextBtn.setDisable(false);
            } else {
                fileLabel.setText("No file");
                startBtn.setDisable(true);
                nextBtn.setDisable(true);
            }
        });

        flagCheckBoxes = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            CheckBox check = new CheckBox();
            check.setDisable(true);
            check.setText(FlagsUtil.namesMap.get(i));
            flagCheckBoxes.add(check);
            flagContainer.getChildren().add(check);
        }

        registerLabels = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            Label label = new Label();
            label.setText("x" + i + ": 0");
            if (i > 15)
                registerContainer1.getChildren().add(label);
            else
                registerContainer2.getChildren().add(label);
            registerLabels.add(label);
        }
    }

    public void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }
}