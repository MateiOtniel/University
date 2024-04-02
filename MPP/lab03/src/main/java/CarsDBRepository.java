import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class CarsDBRepository implements CarRepository {

    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public CarsDBRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public List<Car> findByManufacturer(String manufacturerN) {
        //to do
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Masini")){
            try(ResultSet result = preStmt.executeQuery()){
                while(result.next()){
                    int id = result.getInt("id");
                    String manufacturer = result.getString("manufacturer");
                    String model = result.getString("model");
                    int year = result.getInt("year");
                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);

                }
            }
        } catch (SQLException ex) {
            logger.error((ex));
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
        return cars.stream().filter(x -> x.getManufacturer()
                .equals(manufacturerN)).collect(Collectors.toList());
    }

    @Override
    public List<Car> findBetweenYears(int min, int max) {
        //to do
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Masini")){
            try(ResultSet result = preStmt.executeQuery()){
                while(result.next()){
                    int id = result.getInt("id");
                    String manufacturer = result.getString("manufacturer");
                    String model = result.getString("model");
                    int year = result.getInt("year");
                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);

                }
            }
        } catch (SQLException ex) {
            logger.error((ex));
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
        return cars
                .stream()
                .filter(x -> x.getYear() >= min && x.getYear() <= max)
                .collect(Collectors.toList());
    }

    @Override
    public void add(Car elem) {
        //to do
        logger.traceEntry("saving task {}", elem);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStnt = con.prepareStatement("insert into Masini (manufacturer, model, year) values (?, ?, ?)")) {
            preStnt.setString(1, elem.getManufacturer());
            preStnt.setString(2, elem.getModel());
            preStnt.setInt(3, elem.getYear());
            int result = preStnt.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException ex) {
            logger.error((ex));
            System.err.println("Error DB" + ex);
        }
        logger.traceExit("inserted");
    }

    @Override
    public void update(Integer integer, Car elem) {
        //to do
        logger.traceEntry("saving task {}", elem);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStnt = con
                .prepareStatement("update Cars manufacturer = ?, model = ?, year = ?" +
                        "where id = ?")) {
            preStnt.setString(1, elem.getManufacturer());
            preStnt.setString(2, elem.getModel());
            preStnt.setInt(3, elem.getYear());
            preStnt.setInt(4, elem.getId());
            int result = preStnt.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException ex) {
            logger.error((ex));
            System.err.println("Error DB" + ex);
        }
        logger.traceExit("updated");
    }

    @Override
    public Iterable<Car> findAll() {
        //to do
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Masini")){
            try(ResultSet result = preStmt.executeQuery()){
                while(result.next()){
                    int id = result.getInt("id");
                    String manufacturer = result.getString("manufacturer");
                    String model = result.getString("model");
                    int year = result.getInt("year");
                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);

                }
            }
        } catch (SQLException ex) {
            logger.error((ex));
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
        return cars;
    }
}
