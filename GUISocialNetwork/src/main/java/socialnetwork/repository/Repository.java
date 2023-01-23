package socialnetwork.repository;

import socialnetwork.exceptions.NetworkException;
import socialnetwork.exceptions.RepositoryException;

public interface Repository<T> {
    public void add(T entity) throws NetworkException;

    public void remove(int id) throws RepositoryException;

    public void save();

    public void read();
}
