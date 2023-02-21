package project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import project.entity.Medic;
import project.entity.Sectie;
import project.exception.InputException;
import project.service.Service;
import project.validator.ValidateDate;
import project.validator.ValidateName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProgramareController {
    private Service service;

    private MainController mainController;

    private Sectie selectedSectie;

    @FXML
    ListView<Medic> mediciListView;

    @FXML
    Label label;

    @FXML
    DatePicker datePicker;

    @FXML
    Spinner spinner;

    Medic selectedMedic;

    ObservableList<Medic> mediciModelGrade = FXCollections.observableArrayList();


    public void setService(Service service) {
        this.service = service;
        initialize();
    }

    private void initialize() {
        mediciModelGrade.setAll();
        mediciListView.setItems(mediciModelGrade);
        refreshMedici();
        mediciViewSelectionChanges();
        datePicker.setValue(LocalDate.now());
    }

    private void refreshMedici() {
        ArrayList<Medic> listaMedici = (ArrayList<Medic>) service.getAllMedici()
                .stream()
                .filter(x -> x.getIdSectie() == selectedSectie.getId())
                .collect(Collectors.toList());
        ArrayList<Sectie> listaSectii = service.getAllSectii();
        for (Sectie sectie : listaSectii) {
            Medic medic = service.getMedicById(sectie.getId());
            if (medic != null && medic.getIdSectie() == selectedSectie.getId() && !listaMedici.contains(medic))
                listaMedici.add(medic);
        }
        mediciModelGrade.setAll(listaMedici);
    }

    private void mediciViewSelectionChanges() {
        mediciListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedMedic = newValue;
            if (selectedMedic != null)
                label.setText("Ati selectat medicul " + selectedMedic.getNume());
            else label.setText("Nu este nici un medic selectat!");
        });
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setSectie(Sectie selectedSectie) {
        this.selectedSectie = selectedSectie;
    }

    @FXML
    TextField CNPLabel;

    @FXML
    TextField numeLabel;

    @FXML
    public void programare() {
        if (selectedMedic != null) {
            LocalDate date = datePicker.getValue();
            int hour = (int) spinner.getValue();
            String CNP = CNPLabel.getText();
            String nume = numeLabel.getText();
            ValidateDate validateDate = new ValidateDate();
            ValidateName validateName = new ValidateName();
            try {
                validateDate.validate(date);
                validateName.validate(nume);
                if (date.isEqual(LocalDate.now()) && hour < LocalDateTime.now().getHour())
                    throw new InputException("Input invalid!");
                service.programeaza(selectedMedic, nume, CNP,date, hour);
                refreshAll();
                mainController.update();
            } catch (InputException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText(e.getMessage());
                a.show();
            }
        }
    }

    private void refreshAll() {
        numeLabel.setText("");
        CNPLabel.setText("");
    }
}
