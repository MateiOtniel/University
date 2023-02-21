package project.repository;

import project.entity.Pacient;
import project.entity.Pat;

import java.sql.*;
import java.util.ArrayList;

public class RepositoryPacient implements IRepository<Pacient> {
    private ArrayList<Pacient> list;
    private final String URL;
    private final String user;
    private final String password;

    public RepositoryPacient(String url, String user, String password) {
        URL = url;
        this.user = user;
        this.password = password;
        this.list = new ArrayList<>();
        read();
    }

    @Override
    public void add(Pacient entity) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void save() {

    }

    @Override
    public void read() {
        try {
            Connection connection = DriverManager.getConnection(URL, user, password);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM ati_pacienti");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String CNP = resultSet.getString("cnp");
                int varsta = resultSet.getInt("varsta");
                boolean prematur = resultSet.getBoolean("prematur");
                String diagnostic = resultSet.getString("diagnostic");
                int gravitate = resultSet.getInt("gravitate");
                Pacient pacient = new Pacient(id, CNP, varsta, prematur, diagnostic, gravitate);
                System.out.println(pacient);
                list.add(pacient);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Pacient> getAll() {
        return list;
    }
}
