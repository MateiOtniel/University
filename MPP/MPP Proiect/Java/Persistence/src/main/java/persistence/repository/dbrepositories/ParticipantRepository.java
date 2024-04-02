package persistence.repository.dbrepositories;

import model.Participant;
import org.apache.logging.log4j.Logger;
import persistence.repository.interfaces.IParticipantRepository;
import persistence.repository.utils.JDBCUtils;
import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class ParticipantRepository implements IParticipantRepository {

    protected JDBCUtils jdbcUtils;

    private static final Logger logger = LogManager.getLogger();

    public ParticipantRepository(Properties properties){
        this.jdbcUtils = new JDBCUtils(properties);
        logger.info("Initializing ParticipantRepository with properties: {} ", properties);
    }

    @Override
    public void add(Participant entity) {
        logger.traceEntry("Saving task {}", entity);
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preStnt = con
                .prepareStatement("insert into Participants(name, totalPoints) values (?, ?)")) {
            preStnt.setString(1, entity.getName());
            preStnt.setInt(2, entity.getTotalPoints());
            int result = preStnt.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException ex) {
            logger.error((ex));
            System.err.println("Error DB" + ex);
        }
        logger.traceExit("Successfully saved participant");
    }

    @Override
    public void update(Participant entity) {
        logger.traceEntry("Saving task {}", entity);
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preStnt = con
                .prepareStatement("update Participants set totalPoints = ? where id_p = ?")) {
            preStnt.setInt(1, entity.getTotalPoints());
            preStnt.setInt(2, entity.getId());
            int result = preStnt.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException ex) {
            logger.error((ex));
            System.err.println("Error DB" + ex);
        }
        logger.traceExit("Successfully saved participant");
    }

    @Override
    public void remove(Participant entity) {
        this.removeById(entity.getId());
    }


    @Override
    public void removeById(int id) {
        logger.traceEntry("Saving task {}", id);
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preStnt = con
                .prepareStatement("delete from Participants where id_p = ?")) {
            preStnt.setInt(1, id);
            int result = preStnt.executeUpdate();
            logger.trace("Deleted {} instances", result);
        } catch (SQLException ex) {
            logger.error((ex));
            System.err.println("Error DB" + ex);
        }
        logger.traceExit("Successfully deleted participant");
    }

    @Override
    public ArrayList<Participant> getAll() {
        logger.traceEntry();
        Connection con = jdbcUtils.getConnection();
        ArrayList<Participant> participants = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Participants")){
            try(ResultSet result = preStmt.executeQuery()){
                while(result.next()){
                    int id = result.getInt("id_p");
                    String name = result.getString("name");
                    int totalPoints = result.getInt("totalPoints");
                    Participant participant = new Participant(name, totalPoints);
                    participant.setId(id);
                    participants.add(participant);
                }
            }
        } catch (SQLException ex) {
            logger.error((ex));
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
        return participants;
    }

    @Override
    public Participant getById(Integer id) {
        logger.traceEntry();
        Connection con = jdbcUtils.getConnection();
        Participant participant = null;
        try(PreparedStatement preStmt = con.prepareStatement("select * from Participants where id_p = " + id)){
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()){
                    int id_p = result.getInt("id_p");
                    String name = result.getString("name");
                    int totalPoints = result.getInt("totalPoints");
                    participant = new Participant(name, totalPoints);
                    participant.setId(id_p);
                }
            }
        } catch (SQLException ex) {
            logger.error((ex));
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
        return participant;
    }

}
