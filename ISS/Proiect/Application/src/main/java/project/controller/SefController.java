package project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import project.exception.MyException;
import project.model.Angajat;
import project.model.Sef;
import project.service.Service;
import project.utils.Constants;

import java.util.Collection;

public class SefController {
    private Sef sef;
    private Service service;

    private Angajat selectedAngajat;

    @FXML
    private ListView<Angajat> angajatiListView;

    private final ObservableList<Angajat> angajatiModel = FXCollections.observableArrayList();

    @FXML
    private TextField numeTextField;

    @FXML
    private TextField parolaTextField;

    @FXML
    private TextField prenumeTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextArea sarcinaTextArea;

    @FXML
    private TextField durataTextField;

    @FXML
    public void initialize() {
        angajatiListView.setItems(angajatiModel);
        angajatiListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedAngajat = newValue;
            refreshAngajatTextFields();

        });
    }

    private void refreshAngajatTextFields() {
        if(selectedAngajat != null) {
            numeTextField.setText(selectedAngajat.getNume());
            prenumeTextField.setText(selectedAngajat.getPrenume());
            usernameTextField.setText(selectedAngajat.getUsername());
            parolaTextField.setText(selectedAngajat.getParola());
        } else {
            numeTextField.setText("");
            prenumeTextField.setText("");
            usernameTextField.setText("");
            parolaTextField.setText("");
        }
    }

    public void refreshDescriereTextFields(){
        sarcinaTextArea.setText("");
        durataTextField.setText("");
    }

    public void setUser(Sef user) {
        this.sef = user;
    }

    public void setService(Service service) {
        this.service = service;
        refreshAngajati();
    }

    private void refreshAngajati() {
        angajatiModel.setAll((Collection<? extends Angajat>) service.getAllAngajati());
    }

    @FXML
    void saveAngajat() {
        try {
            service.addAngajat(numeTextField.getText(), prenumeTextField.getText(), usernameTextField.getText(),
                    parolaTextField.getText());
            refreshAngajati();
        } catch(MyException ex) {
            showAlert(ex.getMessage());
        }
    }

    @FXML
    void modifyAngajat() {
        if(selectedAngajat == null) {
            showAlert(Constants.NO_SELECTED_ANGAJAT);
            return;
        }
        try {
            service.modifyAngajat(selectedAngajat.getId(), numeTextField.getText(), prenumeTextField.getText(), usernameTextField.getText(),
                    parolaTextField.getText());
            refreshAngajati();
        } catch(MyException e) {
            refreshAngajatTextFields();
            showAlert(e.getMessage());
        }
    }

    @FXML
    void deleteAngajat() {
        if(selectedAngajat == null) {
            showAlert(Constants.NO_SELECTED_ANGAJAT);
            return;
        }
        service.deleteAngajat(selectedAngajat.getId());
        refreshAngajati();
        refreshAngajatTextFields();

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    @FXML
    void addSarcina() {
        if(selectedAngajat == null) {
            showAlert(Constants.NO_SELECTED_ANGAJAT);
            return;
        }
        if(sarcinaTextArea.getText().isEmpty() || durataTextField.getText().isEmpty()) {
            showAlert(Constants.NO_SARCINA_OR_DURATA);
            return;
        }
        service.addSarcina(selectedAngajat.getId(), sef.getId(), sarcinaTextArea.getText(),
                durataTextField.getText());
        refreshDescriereTextFields();
    }

}
