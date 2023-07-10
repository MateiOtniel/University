package project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import project.model.Angajat;
import project.model.Sarcina;
import project.service.Service;

import java.util.Collection;

public class AngajatController {
    private Service service;

    private Angajat angajat;

    @FXML
    private ListView<Sarcina> sarciniListView;

    private final ObservableList<Sarcina> sarciniModel = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        sarciniListView.setItems(sarciniModel);
    }

    public void setAngajat(Angajat user) {
        this.angajat = user;
    }

    public void setService(Service service) {
        this.service = service;
        refreshSarcini();
    }

    private void refreshSarcini() {
        sarciniModel.setAll((Collection<? extends Sarcina>) service.getAllSarciniByAngajatId(angajat.getId()));
    }

    @FXML
    void addPrezenta() {
        service.addPrezenta(angajat);
    }

    @FXML
    void refreshSarciniAction() {
        refreshSarcini();
    }
}
