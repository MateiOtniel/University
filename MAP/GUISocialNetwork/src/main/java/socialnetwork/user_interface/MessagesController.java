package socialnetwork.user_interface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import socialnetwork.domain.Message;
import socialnetwork.domain.MessageDTO;
import socialnetwork.domain.User;
import socialnetwork.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

public class MessagesController {
    UserService service;
    @FXML
    ListView<User> friendsList;

    @FXML
    Button sendBtn;

    @FXML
    Label nameLabel;

    @FXML
    TableView<MessageDTO> messagesTable;

    @FXML
    TableColumn<MessageDTO, String> messageFrom;

    @FXML
    TableColumn<MessageDTO, String> messageTo;

    ObservableList<MessageDTO> messagesModelGrade = FXCollections.observableArrayList();

    private User loggedUser;

    private User selectedFriend;

    ObservableList<User> friendsModelGrade = FXCollections.observableArrayList();
    private Stage mainStage;

    @FXML
    public void initialize() {
        friendsList.setItems(friendsModelGrade);
        friendsModelGrade.setAll();
        friendsListSelectionChanges();

        messageFrom.setCellValueFactory(new PropertyValueFactory<>("messageFrom"));

        messageTo.setCellValueFactory(new PropertyValueFactory<>("messageTo"));

        messagesTable.setItems(messagesModelGrade);
        messagesModelGrade.setAll();
        messageText.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                sendMessage();
                service.refreshMessages();
                showMessagesWithSelectedFriend();
            }
        });
    }

    private void friendsListSelectionChanges() {
        friendsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedFriend = newValue;
            showMessagesWithSelectedFriend();
        });
    }

    private void showMessagesWithSelectedFriend() {
        messagesModelGrade.clear();
        List<Message> messageList = service.getAllMessages();
        List<MessageDTO> newList = messageList.stream().filter(x -> (x.getTo() == loggedUser.getId() && x.getFrom() == selectedFriend.getId()) || (x.getTo() == selectedFriend.getId() && x.getFrom() == loggedUser.getId())).map(x -> {
            if (x.getFrom() == loggedUser.getId()) return new MessageDTO("", x.getMessage());
            return new MessageDTO(x.getMessage(), "");
        }).collect(Collectors.toList());
        messagesModelGrade.setAll(newList);
    }

    public void setService(UserService service) {
        this.service = service;
        refreshFriendsList();
    }

    private void refreshFriendsList() {
        friendsModelGrade.setAll(service.getAllFriendships(loggedUser.getId()).stream().map(x -> service.getUserById(x.getTo())).collect(Collectors.toList()));
    }

    @FXML
    private void switchToMain() {
        Stage currentStage = (Stage) sendBtn.getScene().getWindow();
        currentStage.hide();
        mainStage.show();
    }

    public void getMainStage(Stage currentStage) {
        this.mainStage = currentStage;
    }

    public void setUser(User loggedInUser) {
        this.loggedUser = loggedInUser;
        nameLabel.setText(loggedUser.getFirstName() + " " + loggedUser.getLastName() + "'s Friends");
    }

    @FXML
    TextField messageText;

    @FXML
    private void sendMessage() {
        if (loggedUser != null && selectedFriend != null && messageText.getText() != null) {
            service.addMessage(loggedUser.getId(), selectedFriend.getId(), messageText.getText());
            messageText.clear();
            showMessagesWithSelectedFriend();
        }
    }

    @FXML
    private void refresh() {
        service.refreshMessages();
        showMessagesWithSelectedFriend();
    }
}
