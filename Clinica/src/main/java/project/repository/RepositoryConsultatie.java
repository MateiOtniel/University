package project.repository;

import project.entity.Consultatie;
import project.entity.Sectie;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class RepositoryConsultatie implements IRepository<Consultatie> {

    private ArrayList<Consultatie> list;

    private final String URL;
    private final String user;
    private final String password;

    public RepositoryConsultatie(String url, String user, String password) {
        URL = url;
        this.user = user;
        this.password = password;
        this.list = new ArrayList<>();
        read();
    }

    @Override
    public void add(Consultatie entity) {
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
            PreparedStatement ps = connection.prepareStatement("TRUNCATE TABLE clinica_consultatii");
            ps.execute();
            ps = connection.prepareStatement("INSERT INTO clinica_consultatii (id, idMedic,cnppacient,numepacient, data, ora)" +
                    " VALUES (?,?,?,?,?,?)");
            for (Consultatie consultatie : list) {
                ps.setInt(1, consultatie.getId());
                ps.setInt(2, consultatie.getIdMedic());
                ps.setString(3, consultatie.getCNPPacient());
                ps.setString(4, consultatie.getNumePacient());
                ps.setDate(5, Date.valueOf(consultatie.getData()));
                ps.setInt(6, consultatie.getOra());
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
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM clinica_consultatii");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int idMedic = resultSet.getInt(2);
                String CNP = resultSet.getString(3);
                String nume = resultSet.getString(4);
                LocalDate data = resultSet.getDate(5).toLocalDate();
                int ora = resultSet.getInt(6);
                list.add(new Consultatie(id, idMedic, CNP, nume, data, ora));
            }
            System.out.println(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Consultatie> getAll(){
        return list;
    }

    public int getMaxId() {
        int id = 1;
        for(Consultatie consultatie : list)
            if(consultatie.getId() > id)
                id = consultatie.getId();
        return id;
    }
}
