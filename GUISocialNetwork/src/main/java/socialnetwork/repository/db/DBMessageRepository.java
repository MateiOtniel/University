package socialnetwork.repository.db;

import socialnetwork.domain.Message;
import socialnetwork.repository.abstractrepo.AbstractMessageRepository;

import java.sql.*;

public class DBMessageRepository extends AbstractMessageRepository {
    private final String URL;
    private final String user;
    private final String password;


    public DBMessageRepository(String url, String user, String password) {
        super();
        URL = url;
        this.user = user;
        this.password = password;
        read();
    }

    @Override
    public void save() {
        try {
            Connection connection = DriverManager.getConnection(URL, user, password);
            PreparedStatement ps = connection.prepareStatement("TRUNCATE TABLE messages");
            ps.execute();
            addDataDB(connection, ps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addDataDB(Connection connection, PreparedStatement ps) {
        try {
            ps = connection.prepareStatement("INSERT INTO messages (id1, id2,message,date) VALUES (?,?,?,?)");
            for (Message message : messageList) {
                ps.setInt(1, message.getFrom());
                ps.setInt(2, message.getTo());
                ps.setString(3, message.getMessage());
                ps.setString(4, message.getDate());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void read() {
        try {
            messageList.clear();
            Connection connection = DriverManager.getConnection(URL, user, password);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM messages");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id1 = resultSet.getInt("id1");
                int id2 = resultSet.getInt("id2");
                String message = resultSet.getString("message");
                String date = resultSet.getString("date");
                messageList.add(new Message(id1, id2, message, date));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
