package project.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import project.entity.Pat;
import project.service.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class PatController {
    @FXML
    Label paturiOcupate;
    @FXML
    Label TIC;
    @FXML
    Label TIM;
    @FXML
    Label TIIP;

    private Service service;

    public void setService(Service service) {
        this.service = service;
        refreshLabels();
    }

    public void refreshLabels() {
        ArrayList<Pat> list = service.getAllPaturi();
        int nrPaturiTIC = (int) list.stream().filter(x -> Objects.equals(x.getTip(), "TIC")
                && (x.getPacientCNP().equals("") || x.getPacientCNP().length() == 0)).count();
        int nrPaturiTIM = (int) list.stream().filter(x -> Objects.equals(x.getTip(), "TIM")
                && (x.getPacientCNP().equals("") || x.getPacientCNP().length() == 0)).count();
        int nrPaturiTIIP = (int) list.stream().filter(x -> Objects.equals(x.getTip(), "TIIP")
                && (x.getPacientCNP().equals("") || x.getPacientCNP().length() == 0)).count();
        paturiOcupate.setText("Paturi ocupate: " + (list.size() - nrPaturiTIIP - nrPaturiTIC - nrPaturiTIM));
        if(nrPaturiTIC == 0)
            TIC.setTextFill(Paint.valueOf("red"));
        else TIC.setTextFill(Paint.valueOf("green"));

        TIC.setText("TIC " + nrPaturiTIC + " paturi libere");
        TIM.setText("TIM " + nrPaturiTIM + " paturi libere");
        TIIP.setText("TIIP " + nrPaturiTIIP + " paturi libere");
    }

}