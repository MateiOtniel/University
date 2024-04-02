package persistence.repository.interfaces;

import model.Entity;

import java.io.Serializable;
import java.util.ArrayList;

public interface IRepository<ID extends Serializable, T extends Entity<ID>> {

    void add(T entity);

    void remove(T entity);

    void removeById(int id);

    ArrayList<T> getAll();

    T getById(ID id);
}
