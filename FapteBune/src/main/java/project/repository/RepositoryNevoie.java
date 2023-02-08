package project.repository;

import project.entity.Nevoie;
import project.entity.Persoana;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RepositoryNevoie implements IRepository<Nevoie>{

    private ArrayList<Nevoie> list;
    private final String URL;
    private final String user;
    private final String password;

    public RepositoryNevoie(String url, String user, String password) {
        URL = url;
        this.user = user;
        this.password = password;
        this.list = new ArrayList<>();
        read();
    }

    @Override
    public void add(Nevoie entity) {
        list.add(entity);
        save();
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void save() {
        try {
            Connection connection = DriverManager.getConnection(URL, user, password);
            PreparedStatement ps = connection.prepareStatement("TRUNCATE TABLE fapte_nevoi");
            ps.execute();
            ps = connection.prepareStatement("INSERT INTO fapte_nevoi" +
                    "(id, titlu, descriere, deadline, ominnevoie, omsalvator, status) VALUES (?,?,?,?,?,?,?)");
            for (Nevoie nevoie : list) {
                ps.setLong(1, nevoie.getId());
                ps.setString(2, nevoie.getTitlu());
                ps.setString(3, nevoie.getDescriere());
                ps.setString(4, nevoie.getDeadline().toString());
                ps.setLong(5, nevoie.getOmInNevoie());
                ps.setLong(6, nevoie.getOmSalvator());
                ps.setString(7, nevoie.getStatus());
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
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM fapte_nevoi");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String titlu = resultSet.getString(2);
                String descriere = resultSet.getString(3);
                LocalDateTime deadline = LocalDateTime.parse(resultSet.getString(4));
                long ominnevoie = resultSet.getLong(5);
                long omsalvator = resultSet.getLong(6);
                String status = resultSet.getString(7);
                list.add(new Nevoie(id, titlu, descriere, deadline, ominnevoie, omsalvator, status));
            }
            System.out.println(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Nevoie> getAll() {
        return list;
    }

    public long getMaxId() {
        long id = 0;
        for(Nevoie nevoie : list)
            if(nevoie.getId() > id)
                id = nevoie.getId();
        return id;
    }

    public Nevoie getById(long id) {
        for(Nevoie nevoie : list)
            if(nevoie.getOmInNevoie() == id)
                return nevoie;
        return null;
    }

    public void ajuta(Nevoie nevoie, Persoana persoana) {
        for(Nevoie n : list)
            if(n.getId() == nevoie.getId()) {
                n.setOmSalvator(persoana.getId());
                n.setStatus("Erou gasit!");
            }
        save();
    }
}
