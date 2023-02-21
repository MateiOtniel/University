package project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import project.entity.Persoana;
import project.exception.InputException;
import project.service.Service;
import project.validator.ValidateName;
import project.validator.ValidatePhoneNumber;

import java.io.IOException;
import java.util.ArrayList;

public class MainController {

    private Service service;

    @FXML
    TextField numeTxt;

    @FXML
    TextField prenumeTxt;

    @FXML
    TextField usernameTxt;

    @FXML
    PasswordField parolaTxt;

    @FXML
    ComboBox<String> oras;

    @FXML
    TextField stradaTxt;

    @FXML
    TextField numarTxt;

    @FXML
    TextField telefonTxt;

    @FXML
    ListView<Persoana> persoaneListView;

    ObservableList<Persoana> persoaneModelGrade = FXCollections.observableArrayList();

    public void setService() {
        this.service = Service.getInstance();
        initialize();
    }

    private void initialize() {
        String[] orase = {"Bucuresti", "Cluj-Napoca", "Constanta"};
        oras.getItems().addAll(orase);
        observers = new ArrayList<>();
        persoaneModelGrade.setAll();
        persoaneListView.setItems(persoaneModelGrade);
        persoana = null;
        refreshPersoane();
        persoaneSelectionChanges();
    }

    Persoana persoana;

    private void persoaneSelectionChanges() {
        persoaneListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            persoana = newValue;
            if (persoana != null)
                newTabs(persoana);
        });
    }

    ArrayList<AjutController> observers;

    private void newTabs(Persoana persoana) {
        try {
            Stage stage1 = new Stage();
            FXMLLoader fxmlLoader1 = new FXMLLoader(MainController.class.getResource("/view/ajut.fxml"));
            Scene scene1 = new Scene(fxmlLoader1.load());
            AjutController ajutController = fxmlLoader1.getController();
            ajutController.setPersoana(persoana);
            ajutController.setMainController(this);
            ajutController.setService(service);
            observers.add(ajutController);
            stage1.setTitle("Denumire: " + persoana.getNume()
                    + " " + persoana.getPrenume() + " | Oras: " + persoana.getOras());
            stage1.setScene(scene1);
            stage1.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPersoane() {
        persoaneModelGrade.setAll(service.getAllPersoane());
    }

    @FXML
    public void inregistrare() {
        try {
            ValidateName validateName = new ValidateName();
            validateName.validate(numeTxt.getText());
            validateName.validate(prenumeTxt.getText());
            ValidatePhoneNumber validatePhoneNumber = new ValidatePhoneNumber();
            validatePhoneNumber.validate(telefonTxt.getText());
            if (usernameTxt.getText().isEmpty() || parolaTxt.getText().isEmpty() || oras.getValue().isEmpty()
                    || stradaTxt.getText().isEmpty() || Integer.parseInt(numarTxt.getText()) == 0)
                throw new InputException("Input invalid!");
            service.addPersoana(numeTxt.getText(), prenumeTxt.getText(), usernameTxt.getText(), parolaTxt.getText(),
                    oras.getValue(), stradaTxt.getText(), numarTxt.getText(), telefonTxt.getText());
            refreshPersoane();
            newTabs(new Persoana(service.getMaxId() + 1, numeTxt.getText(), prenumeTxt.getText(), usernameTxt.getText(), parolaTxt.getText(),
                    oras.getValue(), stradaTxt.getText(), numarTxt.getText(), telefonTxt.getText()));
        } catch (InputException | NumberFormatException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(e.getMessage());
            a.show();
        }
    }

    public void update() {
        for (AjutController ajutController : observers)
            ajutController.refreshNevoi();
    }
}