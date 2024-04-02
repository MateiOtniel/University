package client.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Referee;
import service.IService;
import service.MyException;

public class LoginController{

    private IService service;

    private MainController mainController;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button loginBtn;

    @FXML
    private Label errorLabel;
    private Scene mainControllerScene;

    public void initialize(){
        emailTextField.setFocusTraversable(false);
        passwordTextField.setFocusTraversable(false);
        loginBtn.setFocusTraversable(false);
    }

    public void setService(IService service){
        this.service = service;
    }

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    void login(){
        try{
            Referee referee = service.login(new Referee("Name",
                    emailTextField.getText(), passwordTextField.getText()), mainController);
            openMainWindow(referee);
        } catch(MyException ex){
            errorLabel.setVisible(true);
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText(ex.getMessage());
        }
    }

    private void openMainWindow(Referee referee){
        Stage currentStage = (Stage) loginBtn.getScene().getWindow();
        Stage newStage = new Stage();
        mainController.setReferee(referee);
        mainController.setService(service);
        mainController.setMainStage(currentStage);
        currentStage.hide();
        newStage.setScene(mainControllerScene);
        newStage.show();
    }

    public void setMainControllerScene(Scene mainControllerScene){
        this.mainControllerScene = mainControllerScene;
    }
}