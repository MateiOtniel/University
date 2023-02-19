package project.repository;

import project.entity.Sectie;

import java.sql.*;
import java.util.ArrayList;

public class RepositorySectie implements IRepository<Sectie> {

    private ArrayList<Sectie> list;

    private final String URL;
    private final String user;
    private final String password;

    public RepositorySectie(String url, String user, String password) {
        URL = url;
        this.user = user;
        this.password = password;
        this.list = new ArrayList<>();
        read();
    }

    @Override
    public void add(Sectie entity) {

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
            list.clear();
            Connection connection = DriverManager.getConnection(URL, user, password);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM clinica_sectii");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nume = resultSet.getString("nume");
                int idSefDeSectie = resultSet.getInt("idsefdesectie");
                float pret = resultSet.getFloat("pretperconsultatie");
                float durata = resultSet.getFloat("duratamaximaconsultatie");
                list.add(new Sectie(id, nume, idSefDeSectie, pret, durata));
            }
            System.out.println(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Sectie> getAll(){
        return list;
    }
}
