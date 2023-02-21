package socialnetwork;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import socialnetwork.exceptions.InputException;
import socialnetwork.test.Test;
import socialnetwork.user_interface.Controller;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Controller controller = fxmlLoader.getController();
        controller.setService();
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/view/styles.css")).toExternalForm());
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/view/icon.png"))));
        stage.setTitle("Otniel's Social Network");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Test test = new Test();
        try {
            test.runAllTests();
        } catch (InputException e) {
            throw new RuntimeException(e);
        }
        launch();
    }
}