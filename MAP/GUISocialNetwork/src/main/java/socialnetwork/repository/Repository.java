package socialnetwork.repository;

import socialnetwork.exceptions.NetworkException;
import socialnetwork.exceptions.RepositoryException;

public interface Repository<T> {
    void add(T entity) throws NetworkException;

    void remove(int id) throws RepositoryException;

    void save();

    void read();
}