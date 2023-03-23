package br.unioeste.riscvirtualmachine;

import br.unioeste.riscvirtualmachine.utils.FlagsUtil;
import br.unioeste.riscvirtualmachine.utils.MemoryEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

// Classe responsável por controlar os componentes visuais da janela
// e executar ações baseadas em eventos disparados
public class MainController {
    @FXML
    private HBox flagContainer;
    @FXML
    private Button fileChooserBtn;
    @FXML
    private Label pcLabel;
    @FXML
    private Label messageLabel;
    @FXML
    private Button nextBtn;
    @FXML
    private Label fileLabel;
    @FXML
    private VBox registerContainer1;
    @FXML
    private VBox registerContainer2;

    @FXML
    private TableView<MemoryEntry> memoryTable;
    @FXML
    private TableColumn<MemoryEntry, Integer> addressCol;
    @FXML
    private TableColumn<MemoryEntry, Integer> decimalCol;
    @FXML
    private TableColumn<MemoryEntry, Integer> binaryCol;

    private Stage myStage;
    private List<CheckBox> flagCheckBoxes;
    private List<Label> registerLabels;
    private File file;
    private Processor processor;

    // Função executada após a janela ter sido criada,
    // responsável por inicializar as estruturas que os componenetes
    // do processador iram atualizar para exibir os resultados
    @FXML
    public void initialize() {
        fileChooserBtn.setOnAction(this::tryLoadFile);
        nextBtn.setOnAction(this::nextInstruction);

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

        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        decimalCol.setCellValueFactory(new PropertyValueFactory<>("decimalValue"));
        binaryCol.setCellValueFactory(new PropertyValueFactory<>("binaryValue"));

        ObservableList<MemoryEntry> memoryEntries = FXCollections.observableArrayList();
        for (int i = 0; i < 64; i++) {
            memoryEntries.add(new MemoryEntry(i * 4,0,"0b00000000000000000000000000000000"));
        }
        memoryTable.setItems(memoryEntries);
    }

    // Tenta executar a próxima instrução presente na memória de instrução
    // caso seja a ultima instrução o botão de próximo é desabilitado indicando fim da execução
    private void nextInstruction(ActionEvent event) {
        try {
            boolean shouldContinue = this.processor.tick();
            if (!shouldContinue) {
               nextBtn.setDisable(true);
               pcLabel.setText("PC: end");
            } else {
                pcLabel.setText("PC: " + processor.getCurrentPC() / 4);
            }
        } catch (Exception e) {
            reset();
            messageLabel.setText("Failed to execute instruction " + processor.getCurrentPC() / 4);
            throw new RuntimeException(e);
        }
    }

    // Abre a janela para seleção de um arquivo
    private void tryLoadFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(myStage);

        if (file != null) {
            try {
                this.processor = new Processor(file, memoryTable, registerLabels, flagCheckBoxes);
                reset();
                fileLabel.setText(file.getName());
                nextBtn.setDisable(false);
                messageLabel.setText("");
                pcLabel.setText("PC: 0");
            } catch (Exception e) {
                messageLabel.setText("File with invalid instructions!");
                reset();
                throw new RuntimeException(e);
            }
        } else {
            reset();
        }
    }

    // Reinicia os dados de flags, registradores e memória
    // exibidos na janela
    private void reset() {
        pcLabel.setText("");
        int index = 0;
        for (Label regLabel: registerLabels) {
            regLabel.setText("x" + index + ": " + "0");
            index++;
        }

        for (CheckBox check : flagCheckBoxes) {
            check.setSelected(false);
        }

        for (MemoryEntry item : memoryTable.getItems()) {
            item.update(0);
        }
        nextBtn.setDisable(true);
        fileLabel.setText("No File");
    }

    public void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }
}