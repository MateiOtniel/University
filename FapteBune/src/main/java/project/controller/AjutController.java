package project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import project.entity.Nevoie;
import project.entity.Persoana;
import project.service.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class AjutController {
    private Service service;

    private Persoana persoana;

    @FXML
    TableView<Nevoie> nevoiTableView;

    @FXML
    TableColumn<Nevoie, String> titlu;

    ObservableList<Nevoie> nevoiModelGrade = FXCollections.observableArrayList();

    Nevoie nevoie;

    @FXML
    TableView<Nevoie> ajutatiTableView;

    @FXML
    TableColumn<Nevoie, String> titluA;

    ObservableList<Nevoie> nevoiAModelGrade = FXCollections.observableArrayList();

    public void setService(Service service) {
        this.service = service;
        initialize();
    }

    private void initialize() {
        nevoie = null;
        titlu.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        nevoiModelGrade.setAll();
        nevoiTableView.setItems(nevoiModelGrade);

        titluA.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        nevoiModelGrade.setAll();
        ajutatiTableView.setItems(nevoiAModelGrade);

        refreshNevoi();
        nevoiSelectionChanges();
    }

    private void nevoiSelectionChanges() {
        nevoiTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            nevoie = newValue;
        });
    }

    private void refreshNevoi() {
        ArrayList<Nevoie> nevoi = (ArrayList<Nevoie>) service.getAllNevoi().stream()
                .filter(x -> x.getOmSalvator() == 0 && x.getOmInNevoie() != persoana.getId()
                && service.getPersoanaById(x.getOmInNevoie()) != null
                && Objects.equals(service.getPersoanaById(x.getOmInNevoie()).getOras(), persoana.getOras()))
                .collect(Collectors.toList());
        System.out.println(nevoi);
        nevoiModelGrade.setAll(nevoi);

        ArrayList<Nevoie> nevoi1 = (ArrayList<Nevoie>) service.getAllNevoi().stream()
                .filter(x -> x.getOmSalvator() == persoana.getId())
                .collect(Collectors.toList());
        nevoiAModelGrade.setAll(nevoi1);
    }

    public void setPersoana(Persoana persoana) {
        this.persoana = persoana;
    }

    @FXML
    public void ajuta(){
        if(nevoie != null && persoana != null) {
            service.ajuta(nevoie, persoana);
            refreshNevoi();
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Om ajutat cu succes!");
            a.show();
            nevoie = null;
        }
    }
}
