package repository.db;

import domain.Friendship;
import repository.abstractrepo.AbstractFriendshipRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DBFriendshipRepository extends AbstractFriendshipRepository {
    private final String URL;
    private final String user;
    private final String password;

    /***
     * Constructor with the specified filepath to remember the network
     * between users.
     */
    public DBFriendshipRepository(String URL, String user, String password) {
        super();
        this.URL = URL;
        this.user = user;
        this.password = password;
        read();
    }

    /***
     * Reads all data from database.
     */
    @Override
    public void read() {
        try {
            Connection connection = DriverManager.getConnection(URL, user, password);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM network");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int from = resultSet.getInt("id1");
                int to = resultSet.getInt("id2");
                String date = resultSet.getString("time");
                System.out.println(from + " " + to + " " + date);
                if (!network.containsKey(from))
                    network.put(from, new LinkedList<>());
                network.get(from).add(new Friendship(from, to, LocalDateTime.parse(date)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Saves data in database.
     */
    @Override
    public void save() {
        try {
            Connection connection = DriverManager.getConnection(URL, user, password);
            PreparedStatement ps = connection.prepareStatement("TRUNCATE TABLE network");
            ps.execute();
            addDataDB(connection, ps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Adds all data in database.
     * @param connection
     * @param ps
     */
    private void addDataDB(Connection connection, PreparedStatement ps) {
        try {
            ps = connection.prepareStatement("INSERT INTO network (id1, id2,time) VALUES (?,?,?)");
            for (Map.Entry<Integer, List<Friendship>> set : network.entrySet()){
                for(Friendship fr : set.getValue()){
                    ps.setInt(1, fr.getFrom());
                    ps.setInt(2, fr.getTo());
                    ps.setString(3, fr.getTime().toString());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
