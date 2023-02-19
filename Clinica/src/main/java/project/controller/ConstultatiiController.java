package project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import project.entity.Consultatie;
import project.entity.Medic;
import project.entity.Sectie;
import project.service.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ConstultatiiController {

    @FXML
    ListView<Consultatie> consultatiiListView;

    ObservableList<Consultatie> consultatiiModelGrade = FXCollections.observableArrayList();

    private Service service;

    private Medic medicSelectat;
    public void setService(Service service) {
        this.service = service;
        initialize();
    }

    private void initialize() {
        consultatiiModelGrade.setAll();
        consultatiiListView.setItems(consultatiiModelGrade);
        refreshConsultatii();
    }

    public void refreshConsultatii() {
        ArrayList<Consultatie> listaConsultatii = (ArrayList<Consultatie>) service.getAllConstultatii()
                .stream()
                .filter(x -> x.getIdMedic() == medicSelectat.getId())
                .collect(Collectors.toList());
        Collections.sort(listaConsultatii, Comparator.comparing(Consultatie::getData));
        Collections.sort(listaConsultatii, Comparator.comparing(Consultatie::getOra));
        listaConsultatii.removeIf(x -> x.getData().isBefore(LocalDate.now())
                || (x.getData().isEqual(LocalDate.now()) && x.getOra() < LocalDateTime.now().getHour()));
        consultatiiModelGrade.setAll(listaConsultatii);
    }

    public void setMedic(Medic selectedMedic) {
        medicSelectat = selectedMedic;
    }
}
