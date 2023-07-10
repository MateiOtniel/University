package socialnetwork.user_interface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import socialnetwork.Main;
import socialnetwork.domain.Friendship;
import socialnetwork.domain.User;
import socialnetwork.exceptions.InputException;
import socialnetwork.exceptions.NetworkException;
import socialnetwork.exceptions.RepositoryException;
import socialnetwork.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Controller {
    private UserService service;
    private User selectedUser;
    private User loggedInUser;

    ObservableList<User> modelGrade = FXCollections.observableArrayList();
    ObservableList<User> friendshipModelGrade = FXCollections.observableArrayList();
    @FXML
    TableView<User> usersView;

    @FXML
    TableView<User> friendsView;

    @FXML
    TableColumn<User, String> userID;

    @FXML
    TableColumn<User, String> userMail;

    @FXML
    TableColumn<User, String> userFirstName;

    @FXML
    TableColumn<User, String> userLastName;

    @FXML
    Label selectedUserLabel;

    @FXML
    TableColumn<User, String> friendID;

    @FXML
    TableColumn<User, String> friendMail;

    @FXML
    TableColumn<User, String> friendFirstName;

    @FXML
    TableColumn<User, String> friendLastName;

    @FXML
    PasswordField passwordText;

    @FXML
    Label loggedInLabel;

    @FXML
    Button loginBtn;

    @FXML
    Button disconnectBtn;

    @FXML
    Button signInBtn;

    @FXML
    TextField search;

    @FXML
    ListView<User> userFriendsList;

    ObservableList<User> userFriendsModelGrade = FXCollections.observableArrayList();
    private User selectedFriend;

    private User selectedFriendToAdd;

    @FXML
    public void initialize() {
        userID.setCellValueFactory(new PropertyValueFactory<>("id"));
        userMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        userFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        userLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        usersView.setItems(modelGrade);
        userViewSelectionChanges();

        friendID.setCellValueFactory(new PropertyValueFactory<>("id"));
        friendMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        friendFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        friendLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        friendsView.setItems(friendshipModelGrade);

        friendViewSelectionChanges();
        disconnectBtn.setVisible(false);
        friendRequestsPane.setVisible(false);
        showBtn.setVisible(false);

        search.textProperty().addListener(o -> searchUserTextField());
        userFriendsModelGrade.setAll();
        userFriendsList.setItems(userFriendsModelGrade);

        friendRequestModelGrade.setAll();
        friendRequestView.setItems(friendRequestModelGrade);

        userFriendsModelGradeSelectionChanges();
        friendRequestViewSelectionChanges();
    }

    private void friendRequestViewSelectionChanges() {
        friendRequestView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedFriendRequest = newValue;
        });
    }

    private void userFriendsModelGradeSelectionChanges() {
        userFriendsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedFriendToAdd = newValue;
        });
    }

    private void searchUserTextField() {
        if (loggedInUser != null) {
            List<User> notfriendList = service.getAll().stream().filter(x -> {
                for (Friendship friendship : service.getAllFriendships(loggedInUser.getId()))
                    if (friendship.getTo() == x.getId()) return false;
                return true;
            }).collect(Collectors.toList());
            List<User> usersTemp = new ArrayList<>();
            for (User user : notfriendList) {
                String name = user.getFirstName() + " " + user.getLastName();
                if (name.startsWith(search.getText()) && user.getId() != loggedInUser.getId()) usersTemp.add(user);
            }
            userFriendsModelGrade.setAll(usersTemp);
        } else userFriendsModelGrade.setAll(new LinkedList<>());
    }

    private void userViewSelectionChanges() {
        usersView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedUser = newValue;
            selectedUserLabel.setText(selectedUser.getFirstName() + " " + selectedUser.getLastName());
        });
    }

    private void friendViewSelectionChanges() {
        friendsView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedFriend = newValue;
        });
    }

    private void refreshFriendsTable() {
        if (loggedInUser != null && !service.getAllFriendships(loggedInUser.getId()).isEmpty()) {
            List<Integer> ids = service.getAllFriendships(loggedInUser.getId()).stream().filter(x -> x.getState() == 1).map(Friendship::getTo).collect(Collectors.toList());
            List<User> friendList = ids.stream().map(u -> service.getUserById(u)).collect(Collectors.toList());
            friendshipModelGrade.setAll(friendList);
        } else {
            friendshipModelGrade.setAll(new LinkedList<>());
        }
    }

    private List<User> getAllUsers() {
        return service.getAll().stream().map(u -> new User(u.getId(), u.getEmail(), u.getFirstName(), u.getLastName(), u.getPassword())).collect(Collectors.toList());
    }


    public void setService() {
        this.service = UserService.getInstance(2);
        modelGrade.setAll(this.getAllUsers());
        initialize();
    }


    @FXML
    public void logIn() {
        if (selectedUser != null && loggedInUser == null) {
            if (passwordText.getCharacters().toString().equals(selectedUser.getPassword())) {
                loggedInUser = new User(selectedUser);
                loggedInLabel.setText("Connected user: " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
                refreshFriendsTable();
                searchUserTextField();
                refreshFriendRequests();
                disconnectBtn.setVisible(true);
                loginBtn.setVisible(false);
            }
        }
    }

    private void refreshFriendRequests() {
        if (loggedInUser != null) {
            List<Friendship> friendRequests = service.getAllFriendships(loggedInUser.getId()).stream().filter(x -> x.getState() == 0 || x.getState() == 2).collect(Collectors.toList());
            friendRequestModelGrade.setAll(friendRequests);
        } else friendRequestModelGrade.setAll(new LinkedList<>());
    }

    @FXML
    public void disconnect() {
        loggedInUser = null;
        loggedInLabel.setText("There is no user connected!");
        refreshFriendsTable();
        searchUserTextField();
        refreshFriendRequests();
        loginBtn.setVisible(true);
        disconnectBtn.setVisible(false);
    }

    @FXML
    public void signIn() {

    }

    @FXML
    public void addFriend() {
        if (loggedInUser != null && selectedFriendToAdd != null) {
            try {
                service.addFriend(loggedInUser.getId(), selectedFriendToAdd.getId());
                searchUserTextField();
                refreshFriendRequests();
            } catch (InputException | NetworkException | RepositoryException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    public void deleteFriend() {
        if (loggedInUser != null && selectedFriend != null) {
            try {
                service.removeFriend(loggedInUser.getId(), selectedFriend.getId());
                selectedFriend = null;
                refreshFriendsTable();
                searchUserTextField();
            } catch (InputException | RepositoryException | NetworkException e) {
                System.out.println("");
            }
        }
    }

    @FXML
    Pane searchPane;

    @FXML
    Pane friendRequestsPane;

    @FXML
    Button friendRequestsBtn;

    @FXML
    Button showBtn;

    @FXML
    ListView<Friendship> friendRequestView;

    ObservableList<Friendship> friendRequestModelGrade = FXCollections.observableArrayList();

    Friendship selectedFriendRequest;

    @FXML
    public void viewFriendRequests() {
        searchPane.setVisible(false);
        friendRequestsPane.setVisible(true);
        friendRequestsBtn.setVisible(false);
        showBtn.setVisible(true);
    }

    @FXML
    public void viewSearch() {
        friendRequestsPane.setVisible(false);
        searchPane.setVisible(true);
        friendRequestsBtn.setVisible(true);
        showBtn.setVisible(false);
    }

    @FXML
    public void acceptRequest() {
        if (loggedInUser != null && selectedFriendRequest != null && selectedFriendRequest.getState() == 0) {
            service.acceptRequest(selectedFriendRequest.getTo(), selectedFriendRequest.getFrom());
            refreshFriendsTable();
            refreshFriendRequests();
            selectedFriendRequest = null;
        }
    }

    @FXML
    public void denyRequest() {
        if (loggedInUser != null && selectedFriendRequest != null && selectedFriendRequest.getState() == 0) {
            removeFriend();
        }
    }

    @FXML
    public void unsendRequest() {
        if (loggedInUser != null && selectedFriendRequest != null && selectedFriendRequest.getState() == 2) {
            removeFriend();
        }
    }

    private void removeFriend() {
        try {
            service.removeFriend(selectedFriendRequest.getTo(), selectedFriendRequest.getFrom());
            refreshFriendsTable();
            refreshFriendRequests();
            searchUserTextField();
            selectedFriendRequest = null;
        } catch (InputException | RepositoryException | NetworkException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void openMessages() {
        if (loggedInUser != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/messages.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage currentStage = (Stage) loginBtn.getScene().getWindow();
                Stage newStage = new Stage();
                MessagesController controller = fxmlLoader.getController();
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/view/styles.css")).toExternalForm());
                controller.setUser(loggedInUser);
                controller.setService(service);
                controller.getMainStage(currentStage);
                currentStage.hide();
                newStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/view/icon.png"))));
                newStage.setScene(scene);
                newStage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
