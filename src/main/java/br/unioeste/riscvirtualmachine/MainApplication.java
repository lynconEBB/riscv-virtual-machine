package br.unioeste.riscvirtualmachine;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        String css = this.getClass().getResource("global.css").toExternalForm();
        scene.getStylesheets().add(css);
        MainController controller = fxmlLoader.getController();
        controller.setMyStage(stage);
        stage.setTitle("RISC-V Virtual Machine");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}