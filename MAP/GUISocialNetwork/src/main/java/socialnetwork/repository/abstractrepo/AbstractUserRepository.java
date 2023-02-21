package socialnetwork.repository.abstractrepo;

import socialnetwork.domain.User;
import socialnetwork.exceptions.NetworkException;
import socialnetwork.exceptions.RepositoryException;
import socialnetwork.repository.Repository;

import java.util.Objects;
import java.util.Vector;

public  abstract class AbstractUserRepository implements Repository<User> {
    protected final Vector<User> users;

    /***
     * Constructor that sets the filepath to memorize info.
     */
    public AbstractUserRepository() {
        users = new Vector<User>();
    }

    /***
     * This function reads all the data from file and populate the Vector with users.
     */

    public abstract void read();

    /***
     * This function writes all data to the specified file.
     */
    public abstract void save();

    /***
     * Returns the users vector.
     * @return
     */
    public Vector<User> getAll() {
        return this.users;
    }

    /***
     * Adding a new user in the Vector.
     * @param user
     */
    public void add(User user) throws NetworkException {
        users.add(user);
        save();
    }

    /***
     * This function check's if the user is new by email
     * @param email
     * @throws RepositoryException
     */
    public void checkNewUser(String email) throws RepositoryException {
        for (User user : users) {
            if (Objects.equals(user.getEmail(), email))
                throw new RepositoryException();
        }
    }

    /***
     * This function removes a user by id
     * @param id
     * @throws RepositoryException
     */
    public void remove(int id) throws RepositoryException {
        if (!users.removeIf(user -> user.getId() == id))
            throw new RepositoryException("There is no user with this Id!");
        save();
    }

    /***
     *  The function checks if the user's id is not already existing
     * @param id
     * @throws RepositoryException
     */
    public void userExists(int id) throws RepositoryException {
        boolean exists = false;
        for (User user : users) {
            if (user.getId() == id) {
                exists = true;
                break;
            }
        }
        if (!exists)
            throw new RepositoryException("User doesn't exist!");
    }

    /***
     * Returns the user with the specified id.
     * @param id
     * @return
     */
    public User getUserById(Integer id) {
        for (User user : users) {
            if (user.getId() == id)
                return user;
        }
        return null;
    }

    public boolean isNewId(int id) {
        for(User user : users){
            if(user.getId() == id)
                return false;
        }
        return true;
    }
}
