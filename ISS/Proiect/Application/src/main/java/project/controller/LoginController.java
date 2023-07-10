package project.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import project.StartApp;
import project.exception.MyException;
import project.model.Angajat;
import project.model.Sef;
import project.service.Service;

import java.io.IOException;
import java.util.Objects;

public class LoginController{

    private Service service;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button loginBtn;

    @FXML
    public void initialize(){
        usernameTextField.setFocusTraversable(false);
        passwordTextField.setFocusTraversable(false);
        loginBtn.setFocusTraversable(false);

        usernameTextField.setOnKeyPressed(event -> {
            if(Objects.requireNonNull(event.getCode()) == KeyCode.ENTER){
                passwordTextField.requestFocus();
            }
        });
        passwordTextField.setOnKeyPressed(event -> {
            if(Objects.requireNonNull(event.getCode()) == KeyCode.ENTER){
                login();
            }
        });
    }

    @FXML
    void login(){
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        System.out.println(username + " " + password);
        try{
            Object user = service.login(username, password);
            System.out.println(user.toString());
            if(user instanceof Angajat)
                showAngajatView((Angajat) user);
            else
                showSefView((Sef) user);
        }catch(MyException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }

    private void showAngajatView(Angajat user){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(StartApp.class.getResource("AngajatController.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage currentStage = (Stage) usernameTextField.getScene().getWindow();
            Stage newStage = new Stage();
            newStage.setOnCloseRequest(event -> currentStage.show());
            AngajatController controller = fxmlLoader.getController();
            controller.setAngajat(user);
            controller.setService(service);
            currentStage.close();
            newStage.setScene(scene);
            newStage.setTitle("Angajat View");
            newStage.show();
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    private void showSefView(Sef user){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(StartApp.class.getResource("SefController.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage currentStage = (Stage) usernameTextField.getScene().getWindow();
            Stage newStage = new Stage();
            newStage.setOnCloseRequest(event -> currentStage.show());
            SefController controller = fxmlLoader.getController();
            controller.setUser(user);
            controller.setService(service);
            currentStage.close();
            newStage.setScene(scene);
            newStage.setTitle("Sef View");
            newStage.show();
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public void setService(Service instance){
        this.service = instance;
    }
}