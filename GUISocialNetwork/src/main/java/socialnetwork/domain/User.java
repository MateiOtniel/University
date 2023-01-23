package socialnetwork.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class User{
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Message[] messages;
    private List<Friendship> friends;

    /***
     * Class constructor.
     * @param id
     * @param email
     * @param firstName
     * @param lastName
     * @param password
     */
    public User(int id, String email, String firstName, String lastName, String password) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.messages = null;
        this.friends = new ArrayList<>();
    }

    public User(User another){
        this.id = another.getId();
        this.email = another.getEmail();
        this.firstName = another.getFirstName();
        this.lastName = another.getLastName();
        this.password = another.getPassword();
        this.messages = another.getMessages();
        this.friends = another.getFriends();
    }

    /***
     * Returns the id.
     * @return
     */
    public int getId() {
        return id;
    }

    /***
     * Sets a new id.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /***
     * Returns user's email
     * @return
     */
    public String getEmail() {
        return email;
    }

    /***
     * Sets a new email for user.
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /***
     * Returns user's firstname.
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /***
     * Sets user's firstname.
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /***
     * Returns user's lastname.
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /***
     * Sets a new lastname for user.
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    /***
     * Sets a new password for user.
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /***
     * Returns user's messages.
     * @return
     */
    public Message[] getMessages() {
        return messages;
    }

    /***
     * Sets user's new messages.
     * @param messages
     */
    public void setMessages(Message[] messages) {
        this.messages = messages;
    }

    /***
     * Override for equals function.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(password, user.password) && Arrays.equals(messages, user.messages);
    }

    /***
     * Override for hashCode.
     * @return
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(id, email, firstName, lastName, password);
        result = 31 * result + Arrays.hashCode(messages);
        return result;
    }

    /***
     * Override for toString.
     * @return
     */
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public List<Friendship> getFriends() {
        return friends;
    }

    public void setFriends(List<Friendship> friends) {
        this.friends = friends;
    }
}
