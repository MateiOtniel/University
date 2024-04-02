package persistence.repository.interfaces;

import model.Referee;
import service.MyException;

public interface IRefereeRepository extends IRepository<Integer, Referee>{

    Referee get(String email, String password) throws MyException;
}
