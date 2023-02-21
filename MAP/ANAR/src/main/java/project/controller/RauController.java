package project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import project.entity.Localitate;
import project.entity.Rau;
import project.exception.InputException;
import project.service.Service;
import project.validator.ValidareCota;

import java.util.ArrayList;

public class RauController {

    @FXML
    ListView<Rau> rauriListView;

    ObservableList<Rau> rauriModelGrade = FXCollections.observableArrayList();

    private Service service;

    public void setService(Service service) {
        this.service = service;
        initialize();
    }

    private void initialize() {
        rauriModelGrade.setAll();
        rauriListView.setItems(rauriModelGrade);
        refreshRauri();
        rauSelectionChanges();
        rauSelectat = null;
        rauLabel.setText("Nu este selectat nici un rau.");
    }

    private void rauSelectionChanges() {
        rauriListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            rauSelectat = newValue;
            if(rauSelectat != null)
                rauLabel.setText("Ati selectat raul: " + rauSelectat.getNume());
            else
                rauLabel.setText("Nu este selectat nici un rau.");
        });
    }

    private void refreshRauri() {
        ArrayList<Rau> listaRauri = service.getAllRauri();
        rauriModelGrade.setAll(listaRauri);
    }

    Rau rauSelectat;

    @FXML
    Label rauLabel;

    @FXML
    TextField cota;

    @FXML
    public void modificaCota(){
        if(rauSelectat == null){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Nu este selectat nici un rau!");
            a.show();
        } else {
            ValidareCota validareCota = new ValidareCota();
            try {
                String numar = cota.getText();
                validareCota.validate(numar);
                service.modificareCota(rauSelectat, numar);
                refreshRauri();
                localitateController.refreshLocalitati();
            } catch (InputException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Nu este un numar valid!");
                a.show();
                cota.clear();
            }
        }
    }
    private LocalitateController localitateController;
    public void setLocalitateController(LocalitateController localitateController) {
        this.localitateController = localitateController;
    }
}