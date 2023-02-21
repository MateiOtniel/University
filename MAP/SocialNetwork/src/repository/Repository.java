package repository;

import exceptions.NetworkException;
import exceptions.RepositoryException;

public interface Repository<T> {
    public void add(T entity) throws NetworkException;

    public void remove(int id) throws RepositoryException;

    public void save();

    public void read();
}
