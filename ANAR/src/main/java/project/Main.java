package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.controller.LocalitateController;
import project.controller.RauController;
import project.service.Service;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Service service = Service.getInstance();

        FXMLLoader fxmlLoader = new FXMLLoader(RauController.class.getResource("/view/rau.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        RauController rauController = fxmlLoader.getController();
        rauController.setService(service);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        Stage stage1 = new Stage();
        FXMLLoader fxmlLoader1 = new FXMLLoader(LocalitateController.class.getResource("/view/localitate.fxml"));
        Scene scene1 = new Scene(fxmlLoader1.load());
        LocalitateController localitateController = fxmlLoader1.getController();
        localitateController.setService(service);
        stage1.setTitle("Hello!");
        stage1.setScene(scene1);
        stage1.show();

        rauController.setLocalitateController(localitateController);
    }

    public static void main(String[] args) {

        launch();
    }
}
