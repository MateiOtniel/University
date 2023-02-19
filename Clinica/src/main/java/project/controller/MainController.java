package project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import project.entity.Consultatie;
import project.entity.Medic;
import project.entity.Sectie;
import project.service.Service;

import java.io.IOException;
import java.util.ArrayList;

public class MainController {

    @FXML
    ListView<Sectie> sectiiListView;

    ObservableList<Sectie> sectiiModelGrade = FXCollections.observableArrayList();

    @FXML
    ListView<Medic> mediciListView;

    Medic selectedMedic;

    ObservableList<Medic> mediciModelGrade = FXCollections.observableArrayList();
    private Service service;

    public void setService() {
        this.service = Service.getInstance();
        initialize();
    }

    private void initialize() {
        selectedMedic = null;
        selectedSectie = null;
        listaControllere = new ArrayList<>();

        sectiiModelGrade.setAll();
        sectiiListView.setItems(sectiiModelGrade);
        refreshSectii();
        sectieViewSelectionChanges();

        mediciModelGrade.setAll();
        mediciListView.setItems(mediciModelGrade);
        refreshMedici();
        mediciViewSelectionChanges();
    }

    private void refreshMedici() {
        ArrayList<Medic> listaMedici = service.getAllMedici();
        mediciModelGrade.setAll(listaMedici);
    }

    private void mediciViewSelectionChanges() {
        mediciListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedMedic = newValue;
            if(selectedMedic != null)
                createWindowForMedic();
        });
    }

    private Sectie selectedSectie;

    private void sectieViewSelectionChanges() {
        sectiiListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedSectie = newValue;
            if(selectedSectie != null)
                createWindowForSectie();
        });
    }

    private void createWindowForSectie() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/view/programare.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            ProgramareController controller = fxmlLoader.getController();
            controller.setMainController(this);
            controller.setSectie(selectedSectie);
            controller.setService(service);
            stage.setTitle("Programare sectie " + selectedSectie.getNume());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    ArrayList<ConstultatiiController> listaControllere;

    private void createWindowForMedic() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/view/consultatiiMedic.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            ConstultatiiController controller = fxmlLoader.getController();
            controller.setMedic(selectedMedic);
            controller.setService(service);
            listaControllere.add(controller);
            stage.setTitle("Consultatii " + selectedMedic.getNume());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshSectii() {
        ArrayList<Sectie> listaSectii = service.getAllSectii();
        sectiiModelGrade.setAll(listaSectii);
    }

    public void update(){
        for(ConstultatiiController controller : listaControllere)
            controller.refreshConsultatii();
    }
}