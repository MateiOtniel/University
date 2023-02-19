package project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import project.entity.Pacient;
import project.entity.Pat;
import project.service.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class PacientController {
    @FXML
    ListView<Pacient> listaPacienti;

    Pacient pacientSelectat = null;

    ObservableList<Pacient> modelGrade = FXCollections.observableArrayList();

    private Service service;

    public void setService(Service service) {
        this.service = service;
        initialize();
    }

    private void initialize() {
        modelGrade.setAll();
        listaPacienti.setItems(modelGrade);
        refreshListaPacienti();
        pacientViewSelectionModel();
    }

    private void refreshListaPacienti() {
        ArrayList<Pacient> lista = service.getAllPacienti();
        ArrayList<Pat> listaPaturi = service.getAllPaturi();

        lista.removeIf(pacient -> {
            for(Pat pat : listaPaturi)
                if(Objects.equals(pat.getPacientCNP(), pacient.getCNP()))
                    return true;
            return false;
        });

        Collections.sort(lista, Comparator.comparingInt(Pacient::getGravitate));
        Collections.reverse(lista);
        modelGrade.setAll(lista);
    }

    private void pacientViewSelectionModel() {
        listaPacienti.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            pacientSelectat = newValue;
            System.out.println(pacientSelectat);
        });
    }

    @FXML
    Button TICBtn;

    @FXML
    Button TIMBtn;

    @FXML
    Button TIIPBtn;

    @FXML
    private void TICClicked(){
        if(pacientSelectat != null && service.suntPaturiDisponibile("TIC")) {
            service.adaugaPacient(pacientSelectat.getCNP(), "TIC");
            refreshListaPacienti();
            System.out.println("Pacient adaugat cu succes");
            patController.refreshLabels();
        }
        pacientSelectat = null;
    }

    @FXML
    private void TIMClicked(){
        if(pacientSelectat != null && service.suntPaturiDisponibile("TIM")) {
            service.adaugaPacient(pacientSelectat.getCNP(), "TIM");
            refreshListaPacienti();
            System.out.println("Pacient adaugat cu succes");
            patController.refreshLabels();
        }
        pacientSelectat = null;
    }

    @FXML
    private void TIIPClicked(){
        if(pacientSelectat != null && service.suntPaturiDisponibile("TIIP")) {
            service.adaugaPacient(pacientSelectat.getCNP(), "TIIP");
            refreshListaPacienti();
            System.out.println("Pacient adaugat cu succes");
            patController.refreshLabels();
        }
        pacientSelectat = null;
    }
    private PatController patController;

    public void setPatController(PatController patController) {
        this.patController = patController;
    }
}