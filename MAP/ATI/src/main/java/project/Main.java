package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.controller.PacientController;
import project.controller.PatController;
import project.service.Service;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage1) throws IOException {
        Service service = Service.getInstance();

        FXMLLoader fxmlLoader1 = new FXMLLoader(PatController.class.getResource("/view/pat.fxml"));
        Scene scene1 = new Scene(fxmlLoader1.load());
        PatController patController = fxmlLoader1.getController();
        patController.setService(service);
        stage1.setTitle("Hello!");
        stage1.setScene(scene1);
        stage1.show();

        FXMLLoader fxmlLoader2 = new FXMLLoader(PacientController.class.getResource("/view/pacient.fxml"));
        Stage stage2 = new Stage();
        Scene scene2 = new Scene(fxmlLoader2.load());
        PacientController pacientController = fxmlLoader2.getController();
        pacientController.setService(service);
        stage2.setTitle("Hello!");
        stage2.setScene(scene2);
        stage2.show();
        pacientController.setPatController(patController);
    }

    public static void main(String[] args) {
        launch();
    }
}
