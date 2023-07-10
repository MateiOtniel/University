package project.repository.interfaces;

import project.exception.MyException;
import project.model.Angajat;

public interface IAngajatRepository extends IRepository<Angajat>{
    void save(Angajat angajat) throws MyException;

    Object findByUsernameAndPassword(String username, String password) throws MyException;

    Iterable<Angajat> findAll();

    void modify(Long id, Angajat angajat) throws MyException;

    void delete(Long id);
}
