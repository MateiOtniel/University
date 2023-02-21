package repository.db;

import domain.User;
import exceptions.NetworkException;
import exceptions.RepositoryException;
import repository.abstractrepo.AbstractUserRepository;

import java.sql.*;

public class DBUserRepository extends AbstractUserRepository {
        private final String URL;
        private final String user;
        private final String password;

        /***
         * Constructor that sets the filepath to memorize info.
         */
        public DBUserRepository(String URL, String user, String password) {
            super();
            this.URL = URL;
            this.user = user;
            this.password = password;
            read();
        }

        /***
         * This function reads all the data from file and populate the Vector with users.
         */
        @Override
        public void read() {
            try {
                Connection connection = DriverManager.getConnection(URL, user, password);
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    users.add(new User(id, email, firstName, lastName, password));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        /***
         * Adding a new user in the Vector.
         * @param user
         */
        @Override
        public void add(User user) throws NetworkException {
            users.add(user);
            addUserInDB(user);
        }

    /***
     *  Adds 1 user in database.
     * @param user1
     */
    private void addUserInDB(User user1) {
            try {
                Connection connection = DriverManager.getConnection(URL, user, password);
                String sql = "INSERT INTO users (id, first_name,last_name,email, password) VALUES (?,?,?,?,?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, user1.getId());
                ps.setString(2, user1.getFirstName());
                ps.setString(3, user1.getLastName());
                ps.setString(4, user1.getEmail());
                ps.setString(5, user1.getPassword());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    /***
     * Removes 1 user from list of users.
     * @param id
     * @throws RepositoryException
     */
    @Override
        public void remove(int id) throws RepositoryException {
            if (!users.removeIf(user -> user.getId() == id))
                throw new RepositoryException("There is no user with this Id!");
            removeUserFromDB(id);
        }

    /***
     * Removes 1 user from database.
     * @param id
     */
    private void removeUserFromDB(int id) {
            try {
                Connection connection = DriverManager.getConnection(URL, user, password);
                String sql = "DELETE FROM users WHERE id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        /***
         * This function writes all data to the specified file.
         */
        public void save() {
        }
}
