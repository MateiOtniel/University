package project.repository;

import project.entity.Localitate;
import project.entity.Rau;

import java.sql.*;
import java.util.ArrayList;

public class RepositoryLocalitate implements IRepository<Localitate>{
    private ArrayList<Localitate> lista;

    private final String URL;
    private final String user;
    private final String password;

    public RepositoryLocalitate(String url, String user, String password) {
        URL = url;
        this.user = user;
        this.password = password;
        this.lista = new ArrayList<>();
        read();
    }

    @Override
    public void add(Localitate entity) {

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
            lista.clear();
            Connection connection = DriverManager.getConnection(URL, user, password);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM anar_localitati");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nume = resultSet.getString("nume");
                int rau = resultSet.getInt("rau");
                double cmdr = resultSet.getFloat("cmdr");
                double cma = resultSet.getFloat("cma");
                lista.add(new Localitate(id, nume, rau, cmdr, cma));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Localitate> getAll(){
        return lista;
    }
}
