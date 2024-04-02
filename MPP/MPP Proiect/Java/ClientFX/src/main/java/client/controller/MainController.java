package client.controller;


import client.exception.ModelException;
import client.validator.ValidateNumber;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Participant;
import model.Referee;
import model.Test;
import service.IObserver;
import service.IService;
import service.MyException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable, IObserver{

    private Referee referee;

    private IService service;

    private Stage mainStage;

    private Participant selectedParticipant = null;

    @FXML
    private Label nameLabel;

    @FXML
    private ListView<Participant> participantsListView;

    ObservableList<Participant> participantsModelGrade = FXCollections.observableArrayList();

    @FXML
    Label selectedParticipantLabel;

    @FXML
    Label resultErrorLabel;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField pointsLabel;

    @FXML
    private Pane pane;


    @FXML
    private Label validatorLabel;


    @FXML
    private ListView<Participant> activeParticipantsListView;

    ObservableList<Participant> activeParticipantsModelGrade = FXCollections.observableArrayList();

    @FXML
    private Button logoutBtn;


    public void setReferee(Referee referee){
        this.referee = referee;
        nameLabel.setText(referee.toString());
    }

    public void setService(IService service){
        System.out.println("Service");
        this.service = service;
        refreshAll(new Test(1, 1, 0, ""));
    }

    public void setMainStage(Stage mainStage){
        this.mainStage = mainStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        participantsListView.setItems(participantsModelGrade);
        activeParticipantsListView.setItems(activeParticipantsModelGrade);
        selectedParticipantLabel.setText("Selected participant: none");
        participantsModelGradeSelectionChanges();
        pane.setVisible(false);
        validatorLabel.setVisible(false);
    }

    @FXML
    void addResult(){
        try{
            if(selectedParticipant == null)
                throw new ModelException("There is no user selected");
            resultErrorLabel.setVisible(false);
            pane.setVisible(true);
        }catch(ModelException ex){
            resultErrorLabel.setVisible(true);
            resultErrorLabel.setTextFill(Color.RED);
            resultErrorLabel.setText(ex.getMessage());
        }
    }


    @FXML
    void addResultToDatabase(){
        try{
            ValidateNumber.Validate(pointsLabel.getText());
            if(datePicker.getValue() == null)
                throw new ModelException("Date picked is null");
            Test test = new Test(selectedParticipant.getId(), referee.getId(),
                    Integer.parseInt(pointsLabel.getText()),
                    datePicker.getValue().toString());
            service.addTest(test);
            refreshAll(test);
            selectedParticipant = null;
            selectedParticipantLabel.setText("There is no user selected");
            pointsLabel.clear();
            datePicker.setValue(null);
            pane.setVisible(false);
        }catch(ModelException | MyException ex){
            validatorLabel.setVisible(true);
            validatorLabel.setTextFill(Color.RED);
            validatorLabel.setText(ex.getMessage());
        }
    }

    @FXML
    void logout(){
        try{
            service.logout(referee, this);
        }catch(MyException e){
            throw new RuntimeException(e);
        }
        Stage currentStage = (Stage) logoutBtn.getScene().getWindow();
        currentStage.hide();
        mainStage.show();
    }


    private void participantsModelGradeSelectionChanges(){
        participantsListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selectedParticipant = newValue;
                    if(selectedParticipant != null)
                        selectedParticipantLabel.setText("Selected participant: "
                                + selectedParticipant.getName());
                });
    }

    public void refreshAll(Test test){
        Platform.runLater(() -> {
            refreshParticipants();
            refreshActiveParticipants();
        });
    }

    private void refreshActiveParticipants(){
        try{
            activeParticipantsModelGrade.setAll(
                    (ArrayList<Participant>) service.getAllActiveParticipants(referee.getId()));
        }catch(MyException e){
            resultErrorLabel.setVisible(true);
            resultErrorLabel.setTextFill(Color.RED);
            resultErrorLabel.setText(e.getMessage());
        }
    }

    private void refreshParticipants(){
        ArrayList<Participant> participants;
        try{
            participants = (ArrayList<Participant>) service.getAllParticipants();
            participantsModelGrade.setAll(participants
                    .stream()
                    .sorted(Comparator.comparing(Participant::getName))
                    .collect(Collectors.toList()));
        }catch(MyException e){
            resultErrorLabel.setVisible(true);
            resultErrorLabel.setTextFill(Color.RED);
            resultErrorLabel.setText(e.getMessage());
        }
    }

}
