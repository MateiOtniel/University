package project.repository.interfaces;

import project.exception.MyException;
import project.model.Sef;

public interface ISefRepository extends IRepository<Sef>{
    Object findByUsernameAndPassword(String username, String password) throws MyException;
}
