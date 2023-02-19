package project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import project.entity.Localitate;
import project.entity.LocalitateDTO;
import project.entity.Rau;
import project.service.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public class LocalitateController {

    @FXML
    ListView<LocalitateDTO> localitateListView;

    ObservableList<LocalitateDTO> localitateModelGrade = FXCollections.observableArrayList();

    private Service service;

    public void setService(Service service) {
        this.service = service;
        initialize();
    }

    private void initialize() {
        localitateModelGrade.setAll();
        localitateListView.setItems(localitateModelGrade);
        refreshLocalitati();
    }

    public void refreshLocalitati() {
        ArrayList<Localitate> listaLocalitati = service.getAllLocalitati();
        ArrayList<LocalitateDTO> listaDTO =  new ArrayList<>();

        for(Localitate localitate : listaLocalitati){
            Rau rau = service.getRauById(localitate.getIdRau());
            String risc = "0inexistent";
            if(rau != null){
                if(rau.getCotaMedie() < localitate.getCmdr())
                    risc = "1redus";
                else if(rau.getCotaMedie() >= localitate.getCmdr() && rau.getCotaMedie() <= localitate.getCma())
                    risc = "2mediu";
                else
                    risc = "3ridicat";
            }
            listaDTO.add(new LocalitateDTO(localitate.getNume(), risc));
        }
        Collections.sort(listaDTO, Comparator.comparing(LocalitateDTO::getRisc));
        localitateModelGrade.setAll(listaDTO);
    }
}