package project.repository;

import project.entity.Persoana;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositoryPersoana implements IRepository<Persoana> {

    private List<Persoana> list;
    private final String URL;
    private final String user;
    private final String password;

    public RepositoryPersoana(String url, String user, String password) {
        URL = url;
        this.user = user;
        this.password = password;
        this.list = new ArrayList<>();
        read();
    }

    @Override
    public void add(Persoana entity) {
        list.add(entity);
        save();
    }

    @Override
    public void save() {
        try {
            Connection connection = DriverManager.getConnection(URL, user, password);
            PreparedStatement ps = connection.prepareStatement("TRUNCATE TABLE fapte_persoane");
            ps.execute();
            ps = connection.prepareStatement("INSERT INTO fapte_persoane" +
                    "(id, nume, prenume, parola, oras, strada, numar, telefon, username) VALUES (?,?,?,?,?,?,?,?,?)");
            for (Persoana persoana : list) {
                ps.setLong(1, persoana.getId());
                ps.setString(2, persoana.getNume());
                ps.setString(3, persoana.getPrenume());
                ps.setString(4, persoana.getParola());
                ps.setString(5, persoana.getOras());
                ps.setString(6, persoana.getStrada());
                ps.setString(7, persoana.getNumarStrada());
                ps.setString(8, persoana.getTelefon());
                ps.setString(9, persoana.getUsername());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void read() {
        try {
            list.clear();
            Connection connection = DriverManager.getConnection(URL, user, password);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM fapte_persoane");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String nume = resultSet.getString(2);
                String prenume = resultSet.getString(3);
                String parola = resultSet.getString(4);
                String oras = resultSet.getString(5);
                String strada = resultSet.getString(6);
                String numar = resultSet.getString(7);
                String telefon = resultSet.getString(8);
                String username = resultSet.getString(9);
                list.add(new Persoana(id, nume, prenume, username, parola, oras, strada, numar, telefon));
            }
            System.out.println(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Persoana> getAll() {
        return (ArrayList<Persoana>) list;
    }

    public long getMaxId() {
        long id = 0;
        for (Persoana persoana : list)
            if (persoana.getId() > id)
                id = persoana.getId();
        return id;
    }

    public Persoana getById(long id) {
        for (Persoana persoana : list)
            if (persoana.getId() == id)
                return persoana;
        return null;
    }
}
