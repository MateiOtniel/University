package project.repository;

import project.entity.Medic;
import project.entity.Sectie;

import java.sql.*;
import java.util.ArrayList;

public class RepositoryMedic implements IRepository<Medic> {

    private ArrayList<Medic> list;

    private final String URL;
    private final String user;
    private final String password;

    public RepositoryMedic(String url, String user, String password) {
        URL = url;
        this.user = user;
        this.password = password;
        this.list = new ArrayList<>();
        read();
    }

    @Override
    public void add(Medic entity) {

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
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM clinica_medici");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int idSectie = resultSet.getInt(2);
                String nume = resultSet.getString(3);
                float vechime = resultSet.getFloat(4);
                boolean rezident = resultSet.getBoolean(5);
                list.add(new Medic(id, idSectie, nume, vechime, rezident));
            }
            System.out.println(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Medic> getAll(){
        return list;
    }

    public Medic getById(int id){
        for(Medic medic : list)
            if(medic.getId() == id)
                return medic;
        return null;
    }
}
