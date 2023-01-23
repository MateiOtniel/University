package service;

import domain.Friendship;
import domain.User;
import exceptions.NetworkException;
import exceptions.RepositoryException;
import exceptions.InputException;
import repository.abstractrepo.AbstractFriendshipRepository;
import repository.abstractrepo.AbstractUserRepository;
import repository.db.DBFriendshipRepository;
import repository.db.DBUserRepository;
import repository.file.FileUserRepository;
import repository.file.FileFriendshipRepository;
import validators.MailValidator;
import validators.NameValidator;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class UserService {
    private final MailValidator mailValidator;
    private final NameValidator nameValidator;
    private final AbstractUserRepository repository;
    private final AbstractFriendshipRepository network;
    /***
     * Single instance for class constructor
     */

    public UserService(MailValidator mailValidator, NameValidator nameValidator, FileUserRepository repository, FileFriendshipRepository network) {
        this.mailValidator = mailValidator;
        this.nameValidator = nameValidator;
        this.repository = repository;
        this.network = network;
    }

    public UserService(MailValidator mailValidator, NameValidator nameValidator, DBUserRepository dbRepository, DBFriendshipRepository dbNetwork) {
        this.mailValidator = mailValidator;
        this.nameValidator = nameValidator;
        this.repository = dbRepository;
        this.network = dbNetwork;
    }

    /***
     * Singleton method to get instance.
     * @return
     */
    public static UserService getInstance(int i) {
        if(i == 1){
            return new UserService(new MailValidator(), new NameValidator(),
                    new FileUserRepository("data/users.csv"), new FileFriendshipRepository("data/network.csv"));
        } else{
            return new UserService(new MailValidator(), new NameValidator(),
                    new DBUserRepository("jdbc:postgresql://localhost:5432/postgres",
                            "postgres", "Respectu20."),
                    new DBFriendshipRepository("jdbc:postgresql://localhost:5432/postgres",
                            "postgres", "Respectu20."));
        }
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
        network.add(new Friendship(id1, id2, time));
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
}
