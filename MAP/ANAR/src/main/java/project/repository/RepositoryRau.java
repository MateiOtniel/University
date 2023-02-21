package project.repository;

import project.entity.Rau;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class RepositoryRau implements IRepository<Rau>{
    private ArrayList<Rau> lista;

    private final String URL;
    private final String user;
    private final String password;

    public RepositoryRau(String url, String user, String password) {
        URL = url;
        this.user = user;
        this.password = password;
        this.lista = new ArrayList<>();
        read();
    }

    @Override
    public void add(Rau entity) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void save() {
        try {
            Connection connection = DriverManager.getConnection(URL, user, password);
            PreparedStatement ps = connection.prepareStatement("TRUNCATE TABLE anar_rauri");
            ps.execute();
            ps = connection.prepareStatement("INSERT INTO anar_rauri (id, nume, cotamedie) VALUES (?,?,?)");
            for (Rau rau : lista) {
                ps.setInt(1, rau.getId());
                ps.setString(2, rau.getNume());
                ps.setFloat(3, (float) rau.getCotaMedie());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void read() {
        try {
            lista.clear();
            Connection connection = DriverManager.getConnection(URL, user, password);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM anar_rauri");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nume = resultSet.getString("nume");
                double cotaMedie = resultSet.getFloat("cotaMedie");
                lista.add(new Rau(id, nume, cotaMedie));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Rau> getAll(){
        return lista;
    }

    public Rau getById(int id) {
        if(id == -1)
            return null;
        for(Rau rau : lista)
            if(rau.getId() == id)
                return rau;
        return null;
    }

    public void modifcareCota(Rau rauSelectat, String numar) {
        for(Rau rau : lista){
            if(rau.equals(rauSelectat))
                rau.setCotaMedie(Double.parseDouble(numar));
        }
        save();
    }
}
