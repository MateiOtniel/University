package project;

import project.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.repository.utils.HibernateUtils;
import project.service.Service;
import org.hibernate.SessionFactory;

import java.io.IOException;

public class StartApp extends Application{
    @Override
    public void start(Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(StartApp.class.getResource("LoginController.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LoginController controller = fxmlLoader.getController();
        SessionFactory sessionFactory = HibernateUtils.initialize();
        controller.setService(Service.getInstance(sessionFactory));
        stage.setTitle("Firma");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch();
    }
}