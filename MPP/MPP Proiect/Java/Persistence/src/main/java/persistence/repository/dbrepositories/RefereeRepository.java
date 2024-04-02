package persistence.repository.dbrepositories;

import model.Referee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import persistence.repository.interfaces.IRefereeRepository;
import service.MyException;

import java.util.ArrayList;

public class RefereeRepository implements IRefereeRepository {

    //protected JDBCUtils jdbcUtils;
    private static final Logger logger = LogManager.getLogger();

//    public RefereeRepository(Properties properties){
//       this.jdbcUtils = new JDBCUtils(properties);
//        logger.info("Initializing RefereeRepository with properties: {} ", properties);
//    }

    private static SessionFactory sessionFactory;

    public RefereeRepository(){
        initialize();
    }

    private static void initialize(){
        StandardServiceRegistry ssr = null;
        try{
            ssr = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml").build();
            sessionFactory = new MetadataSources(ssr).buildMetadata().buildSessionFactory();
        }catch(Exception e){
            System.err.println("Exceptie: " + e.getCause());
            if(ssr != null){
                StandardServiceRegistryBuilder.destroy(ssr);
            }
        }
    }

    @Override
    public Referee get(String email, String password) throws MyException {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Referee referee = session.createQuery("from Referee where email = :email and password = :password",
                            Referee.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .uniqueResult();
            session.getTransaction().commit();
            if(referee == null){
                throw new MyException("Username sau parola gresita!");
            }
            return referee;
        }
    }

    @Override
    public void add(Referee entity) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public void remove(Referee entity) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeById(int id) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Referee referee = session.createQuery("from Referee where id = :id", Referee.class)
                    .setParameter("id", id)
                    .uniqueResult();
            session.delete(referee);
            session.getTransaction().commit();
        }
    }

    @Override
    public ArrayList<Referee> getAll() {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            ArrayList<Referee> referees = (ArrayList<Referee>) session.createQuery("from Referee", Referee.class)
                    .list();
            session.getTransaction().commit();
            return referees;
        }
    }

    @Override
    public Referee getById(Integer integer) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Referee referee = session.createQuery("from Referee where id = :id", Referee.class)
                    .setParameter("id", integer)
                    .uniqueResult();
            session.getTransaction().commit();
            return referee;
        }
    }

//    @Override
//    public void add(Referee entity) {
//        logger.traceEntry("Saving task {}", entity);
//        Connection con = jdbcUtils.getConnection();
//        try (PreparedStatement preStnt = con
//                .prepareStatement("insert into Referees(name, email, password) values (?, ?, ?)")) {
//            preStnt.setString(1, entity.getName());
//            preStnt.setString(2, entity.getEmail());
//            preStnt.setString(3, entity.getPassword());
//            int result = preStnt.executeUpdate();
//            logger.trace("Saved {} instances", result);
//        } catch (SQLException ex) {
//            logger.error((ex));
//            System.err.println("Error DB" + ex);
//        }
//        logger.traceExit("Successfully saved referee");
//    }
//
//    @Override
//    public void remove(Referee entity) {
//        this.removeById(entity.getId());
//    }
//
//
//    @Override
//    public void removeById(int id) {
//        logger.traceEntry("Saving task {}", id);
//        Connection con = jdbcUtils.getConnection();
//        try (PreparedStatement preStnt = con
//                .prepareStatement("delete from Referees where id_r = ?")) {
//            preStnt.setInt(1, id);
//            int result = preStnt.executeUpdate();
//            logger.trace("Deleted {} instances", result);
//        } catch (SQLException ex) {
//            logger.error((ex));
//            System.err.println("Error DB" + ex);
//        }
//        logger.traceExit("Successfully deleted referee");
//    }
//
//    @Override
//    public ArrayList<Referee> getAll() {
//        logger.traceEntry();
//        Connection con = jdbcUtils.getConnection();
//        ArrayList<Referee> referees = new ArrayList<>();
//        try(PreparedStatement preStmt = con.prepareStatement("select * from Referees")){
//            try(ResultSet result = preStmt.executeQuery()){
//                while(result.next()){
//                    int id = result.getInt("id_r");
//                    String name = result.getString("name");
//                    String email = result.getString("email");
//                    String password = result.getString("password");
//                    Referee Referee = new Referee(name, email, password);
//                    Referee.setId(id);
//                    referees.add(Referee);
//                }
//            }
//        } catch (SQLException ex) {
//            logger.error((ex));
//            System.err.println("Error DB" + ex);
//        }
//        logger.traceExit();
//        return referees;
//    }
//
//    @Override
//    public Referee getById(Integer id) {
//        logger.traceEntry();
//        Connection con = jdbcUtils.getConnection();
//        Referee referee = null;
//        try(PreparedStatement preStmt = con.prepareStatement("select * from Referees where id_r = " + id)){
//            referee = getRefereeFromDatabase(referee, preStmt);
//        } catch (SQLException ex) {
//            logger.error((ex));
//            System.err.println("Error DB" + ex);
//        }
//        logger.traceExit();
//        return referee;
//    }
//
//    @Override
//    public Referee get(String email, String password) throws MyException{
//        logger.traceEntry();
//        Connection con = jdbcUtils.getConnection();
//        Referee referee = null;
//        try(PreparedStatement preStmt =
//                    con.prepareStatement("select * from Referees where email = ? and password = ?")){
//            preStmt.setString(1, email);
//            preStmt.setString(2, password);
//            referee = getRefereeFromDatabase(referee, preStmt);
//        } catch (SQLException ex) {
//            logger.error((ex));
//            System.err.println("Error DB" + ex);
//        }
//        logger.traceExit();
//        if(referee == null)
//            throw new MyException("Referee doesn't exist!");
//        return referee;
//    }
//
//    private Referee getRefereeFromDatabase(Referee referee, PreparedStatement preStmt) throws SQLException {
//        try(ResultSet result = preStmt.executeQuery()){
//            if(result.next()){
//                int id_r = result.getInt("id_r");
//                String name = result.getString("name");
//                String mail = result.getString("email");
//                String psd = result.getString("password");
//                referee = new Referee(name, mail, psd);
//                referee.setId(id_r);
//            }
//        }
//        return referee;
//    }
}
