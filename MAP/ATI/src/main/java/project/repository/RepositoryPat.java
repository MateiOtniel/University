package project.repository;

import project.entity.Pat;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class RepositoryPat implements IRepository<Pat>{
    private ArrayList<Pat> list;
    private final String URL;
    private final String user;
    private final String password;

    public RepositoryPat(String url, String user, String password) {
        URL = url;
        this.user = user;
        this.password = password;
        this.list = new ArrayList<>();
        read();
    }

    @Override
    public void add(Pat entity) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void save() {
        try {
            Connection connection = DriverManager.getConnection(URL, user, password);
            PreparedStatement ps = connection.prepareStatement("TRUNCATE TABLE ati_paturi");
            ps.execute();
            ps = connection.prepareStatement("INSERT INTO ati_paturi (id, tip,ventilatie,pacient) VALUES (?,?,?,?)");
            for (Pat pat : list) {
                ps.setInt(1, pat.getId());
                ps.setString(2,pat.getTip());
                ps.setBoolean(3, pat.getVentilatie());
                ps.setString(4, pat.getPacientCNP());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void read() {
        try {
            Connection connection = DriverManager.getConnection(URL, user, password);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM ati_paturi");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String tip = resultSet.getString("tip");
                boolean ventilatie = resultSet.getBoolean("ventilatie");
                String CNP = resultSet.getString("pacient");
                Pat pat = new Pat(id, tip, ventilatie, CNP);
                System.out.println(pat);
                list.add(pat);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Pat> getAll() {
        return list;
    }

    public boolean suntPaturiDisponibile(String tip) {
        return list.stream().filter(pat -> Objects.equals(pat.getTip(), tip)).count() > 0;
    }

    public void adaugaPacient(String cnp, String tip) {
        for(Pat pat : list)
            if((pat.getPacientCNP().equals("") || pat.getPacientCNP().length() == 0)
                    && Objects.equals(pat.getTip(), tip)){
                pat.setPacientCNP(cnp);
                break;
            }
        save();
    }
}
