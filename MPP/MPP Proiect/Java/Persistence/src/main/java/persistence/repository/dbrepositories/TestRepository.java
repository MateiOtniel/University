package persistence.repository.dbrepositories;

import model.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import persistence.repository.interfaces.ITestRepository;
import persistence.repository.utils.JDBCUtils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

@Component
public class TestRepository implements ITestRepository, Serializable {

    protected JDBCUtils jdbcUtils;

    private static final Logger logger = LogManager.getLogger();

    public TestRepository() {
        logger.info("Initializing TestRepository with properties: {} ",
                "default");
    }

    public TestRepository(Properties properties) {
        jdbcUtils = new JDBCUtils(properties);
        logger.info("Initializing TestRepository with properties: {} ", properties);
    }

    @Override
    public void add(Test entity) {
        //int idParticipant, int idReferee, int points, String date
        logger.traceEntry("Saving task {}", entity);
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preStnt = con
                .prepareStatement("insert into Tests(idParticipant, idReferee, points, date_t)" +
                        "values (?, ?, ?, ?)")) {
            preStnt.setInt(1, entity.getIdParticipant());
            preStnt.setInt(2, entity.getIdReferee());
            preStnt.setInt(3, entity.getPoints());
            preStnt.setString(4, entity.getDate());
            int result = preStnt.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException ex) {
            logger.error((ex));
            System.err.println("Error DB" + ex);
        }
        logger.traceExit("Successfully saved Test");
    }

    @Override
    public void remove(Test entity) {
        this.removeById(entity.getId());
    }


    @Override
    public void removeById(int id) {
        logger.traceEntry("Saving task {}", id);
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preStnt = con
                .prepareStatement("delete from Tests where id = ?")) {
            preStnt.setInt(1, id);
            int result = preStnt.executeUpdate();
            logger.trace("Deleted {} instances", result);
        } catch (SQLException ex) {
            logger.error((ex));
            System.err.println("Error DB" + ex);
        }
        logger.traceExit("Successfully deleted Test");
    }

    @Override
    public ArrayList<Test> getAll() {
        logger.traceEntry();
        Connection con = jdbcUtils.getConnection();
        ArrayList<Test> tests = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Tests")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt(1);
                    int idParticipant = result.getInt(2);
                    int idReferee = result.getInt(3);
                    int points = result.getInt(4);
                    String date = result.getString(5);
                    Test test = new Test(idParticipant, idReferee, points, date);
                    test.setId(id);
                    tests.add(test);
                }
            }
        } catch (SQLException ex) {
            logger.error((ex));
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
        return tests;
    }

    @Override
    public Test getById(Integer id) {
        logger.traceEntry();
        Connection con = jdbcUtils.getConnection();
        Test test = null;
        try (PreparedStatement preStmt = con.prepareStatement("select * from Tests where id = " + id)) {
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    int idParticipant = result.getInt(2);
                    int idReferee = result.getInt(3);
                    int points = result.getInt(4);
                    String date = result.getString(5);
                    test = new Test(idParticipant, idReferee, points, date);
                    test.setId(id);
                }
            }
        } catch (SQLException ex) {
            logger.error((ex));
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
        return test;
    }

    @Override
    public void update(int id, Test test) {
        logger.traceEntry("Updating test with id {} with {}", id, test);
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preStnt = con
                .prepareStatement("update Tests set idParticipant = ?, idReferee = ?, points = ?, date_t = ? where id = ?")) {
            preStnt.setInt(1, test.getIdParticipant());
            preStnt.setInt(2, test.getIdReferee());
            preStnt.setInt(3, test.getPoints());
            preStnt.setString(4, test.getDate());
            preStnt.setInt(5, id);
            int result = preStnt.executeUpdate();
            logger.trace("Updated {} instances", result);
        } catch (SQLException ex) {
            logger.error((ex));
            System.err.println("Error DB" + ex);
        }
    }
}
