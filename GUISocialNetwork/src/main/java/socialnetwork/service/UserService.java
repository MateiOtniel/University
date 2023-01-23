package socialnetwork.service;

import socialnetwork.domain.Friendship;
import socialnetwork.domain.Message;
import socialnetwork.domain.User;
import socialnetwork.exceptions.InputException;
import socialnetwork.exceptions.NetworkException;
import socialnetwork.exceptions.RepositoryException;
import socialnetwork.repository.abstractrepo.AbstractFriendshipRepository;
import socialnetwork.repository.abstractrepo.AbstractMessageRepository;
import socialnetwork.repository.abstractrepo.AbstractUserRepository;
import socialnetwork.repository.db.DBFriendshipRepository;
import socialnetwork.repository.db.DBMessageRepository;
import socialnetwork.repository.db.DBUserRepository;
import socialnetwork.validators.MailValidator;
import socialnetwork.validators.NameValidator;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class UserService {
    private final MailValidator mailValidator;
    private final NameValidator nameValidator;
    private final AbstractUserRepository repository;
    private final AbstractFriendshipRepository network;
    private final AbstractMessageRepository messages;

    /***
     * Single instance for class constructor
     */
    public UserService(MailValidator mailValidator, NameValidator nameValidator,
                       DBUserRepository dbRepository, DBFriendshipRepository dbNetwork,
                       DBMessageRepository dbMessages) {
        this.mailValidator = mailValidator;
        this.nameValidator = nameValidator;
        this.repository = dbRepository;
        this.network = dbNetwork;
        this.messages = dbMessages;
    }

    /***
     * Singleton method to get instance.
     * @return
     */
    public static UserService getInstance(int i) {
        return new UserService(new MailValidator(), new NameValidator(),
                new DBUserRepository("jdbc:postgresql://localhost:5432/postgres",
                        "postgres", "Respectu20."),
                new DBFriendshipRepository("jdbc:postgresql://localhost:5432/postgres",
                        "postgres", "Respectu20."),
                new DBMessageRepository("jdbc:postgresql://localhost:5432/postgres",
                        "postgres", "Respectu20."));
    }

    /***
     * Creating user and sending it to be added in repository
     * and network.
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @throws InputException
     * @throws RepositoryException
     */
    public void addUser(String firstName, String lastName, String email, String password) throws InputException, RepositoryException, NetworkException {
        validateInput(firstName, lastName, email);
        repository.checkNewUser(email);
        int id;
        do {
            id = (int) Math.round(Math.random() * 10000);
        } while (!repository.isNewId(id));
        User user = new User(id, email, firstName, lastName, password);
        repository.add(user);
        network.addUser(user.getId());
    }

    /***
     * Validating input1.
     * @param firstName
     * @param lastName
     * @param email
     * @throws InputException
     */
    private void validateInput(String firstName, String lastName, String email) throws InputException {
        nameValidator.validate(firstName);
        nameValidator.validate(lastName);
        mailValidator.validate(email);
    }


    /***
     * Removing user by id.
     * @param id
     * @throws InputException
     * @throws RepositoryException
     */
    public void removeUser(int id) throws InputException, RepositoryException {
        repository.remove(id);
        network.remove(id);
    }

    /***
     * Returns the list of users.
     * @return
     */
    public Vector<User> getAll() {
        return repository.getAll();
    }

    /***
     * Adds 2 friends by id after validating input.
     * @param id1
     * @param id2
     * @throws InputException
     * @throws NetworkException
     * @throws RepositoryException
     */
    public void addFriend(int id1, int id2) throws InputException, NetworkException, RepositoryException {
        repository.userExists(id1);
        repository.userExists(id2);
        LocalDateTime time = LocalDateTime.now();
        network.add(new Friendship(id1, id2, time, 2));
    }

    /***
     * Removes 2 friends by id after validating input.
     * @param id1
     * @param id2
     * @throws InputException
     * @throws RepositoryException
     * @throws NetworkException
     */
    public void removeFriend(int id1, int id2) throws InputException, RepositoryException, NetworkException {
        repository.userExists(id1);
        repository.userExists(id2);
        network.removeFriend(id1, id2);
    }

    /***
     * Returns number of communities.
     * @return
     */
    public int numberOfCommunities() {
        return network.numberOfCommunities();
    }

    /***
     * Returns the StringBuilder that represents the biggest community.
     * @return StringBuilder
     */
    public StringBuilder biggestCommunity() {
        List<Integer> ids = new LinkedList<>();
        network.biggestCommunity(ids);
        StringBuilder rez = new StringBuilder();
        for (Integer id : ids)
            rez.append(repository.getUserById(id).toString()).append("\n");
        if (rez.length() != 0)
            return new StringBuilder(rez.substring(0, rez.length() - 1));
        else return null;
    }

    public List<Friendship> getAllFriendships(int id){
        return network.getAllFriendships(id);
    }

    public User getUserById(int id){
        return repository.getUserById(id);
    }

    public void acceptRequest(int id1, int id2) {
        network.acceptRequest(id1, id2);
    }

    public void addMessage(int id1, int id2, String text) {
        String date = LocalDateTime.now().toString();
        messages.add(new Message(id1, id2, text, date));
    }

    public List<Message> getAllMessages() {
        return messages.getAll();
    }

    public void refreshMessages() {
        messages.read();
    }
}
