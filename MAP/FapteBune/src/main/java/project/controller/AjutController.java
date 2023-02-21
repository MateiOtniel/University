package project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import project.entity.Nevoie;
import project.entity.NevoieDTO;
import project.entity.Persoana;
import project.exception.InputException;
import project.service.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class AjutController {
    private Service service;

    private Persoana persoana;

    @FXML
    TableView<NevoieDTO> nevoiTableView;

    @FXML
    TableColumn<NevoieDTO, String> titlu;

    @FXML
    TableColumn<NevoieDTO, String> nume;

    @FXML
    TableColumn<NevoieDTO, String> descriereA;

    ObservableList<NevoieDTO> nevoiModelGrade = FXCollections.observableArrayList();

    Nevoie nevoie;

    @FXML
    TableView<NevoieDTO> ajutatiTableView;

    @FXML
    TableColumn<NevoieDTO, String> numeA1;

    @FXML
    TableColumn<NevoieDTO, String> titluA;

    @FXML
    TableColumn<NevoieDTO, String> descriereA1;

    ObservableList<NevoieDTO> nevoiAModelGrade = FXCollections.observableArrayList();

    public void setService(Service service) {
        this.service = service;
        initialize();
    }

    private void initialize() {
        nevoie = null;
        titlu.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        nume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        descriereA.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        nevoiModelGrade.setAll();
        nevoiTableView.setItems(nevoiModelGrade);

        titluA.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        numeA1.setCellValueFactory(new PropertyValueFactory<>("nume"));
        descriereA1.setCellValueFactory(new PropertyValueFactory<>("descriere"));
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

    public void refreshNevoi() {
        ArrayList<NevoieDTO> nevoi = (ArrayList<NevoieDTO>) service.getAllNevoi().stream()
                .filter(x -> x.getOmSalvator() == 0 && x.getOmInNevoie() != persoana.getId()
                        && service.getPersoanaById(x.getOmInNevoie()) != null
                        && Objects.equals(service.getPersoanaById(x.getOmInNevoie()).getOras(), persoana.getOras()))
                .map(x -> new NevoieDTO(x, service.getPersoanaById(x.getOmInNevoie()).getNume()))
                .collect(Collectors.toList());
        System.out.println(nevoi);
        nevoiModelGrade.setAll(nevoi);

        ArrayList<NevoieDTO> nevoi1 = (ArrayList<NevoieDTO>) service.getAllNevoi().stream()
                .filter(x -> x.getOmSalvator() == persoana.getId())
                .map(x -> new NevoieDTO(x, service.getPersoanaById(x.getOmInNevoie()).getNume()))
                .collect(Collectors.toList());
        nevoiAModelGrade.setAll(nevoi1);
    }

    public void setPersoana(Persoana persoana) {
        this.persoana = persoana;
    }

    @FXML
    public void ajuta() {
        if (nevoie != null && persoana != null) {
            service.ajuta(nevoie, persoana);
            mainController.update();
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Om ajutat cu succes!");
            a.show();
            nevoie = null;
        }
    }

    @FXML
    private DatePicker data;

    @FXML
    private TextField descriereTxt;

    @FXML
    private TextField titluTxt;

    @FXML
    void adaugaCerereAjutor() {
        try {
            String titluCerere = titluTxt.getText();
            String descriereCerere = descriereTxt.getText();
            LocalDateTime dataCerere = data.getValue().atStartOfDay();
            if (titluCerere.isEmpty() || descriereCerere.isEmpty() ||
                    dataCerere.isBefore(LocalDateTime.now()))
                throw new InputException("Input invalid!");
            service.addNevoie(persoana, titluCerere, descriereCerere, dataCerere);
            mainController.update();
        } catch (InputException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText(e.getMessage());
            a.show();
        }
    }

    MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
