package client;

import client.controller.LoginController;
import client.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import protobuffprotocol.ProtoProxy;
import rpcprotocol.ServicesRpcProxy;
import service.IService;

import java.io.IOException;
import java.util.Properties;

public class Main extends Application {

    private static final int defaultServerPort = 55555;
    private static final String defaultServer = "localhost";

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("In start");
        Properties properties = new Properties();
        try {
            properties.load(Main.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            properties.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties " + e);
            return;
        }

        String serverIp = properties.getProperty("client.server.host", defaultServer);
        int serverPort = defaultServerPort;

        try {
            serverPort = Integer.parseInt(properties.getProperty("client.server.port"));
        } catch (NumberFormatException e) {
            System.err.println("Wrong port number " + e.getMessage());
            System.out.println("Using default port: " + defaultServerPort);
        }

        System.out.println("Using server IP " + serverIp);
        System.out.println("Using server port " + serverPort);

        IService service = new ProtoProxy(serverIp, serverPort);

        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass()
                .getResource("/referee/main.fxml"));
        Scene mainControllerScene = new Scene(fxmlLoader1.load());
        MainController mainController = fxmlLoader1.getController();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("/referee/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LoginController loginController = fxmlLoader.getController();
        loginController.setMainController(mainController);
        loginController.setMainControllerScene(mainControllerScene);
        loginController.setService(service);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
