package project.repository;

import java.util.ArrayList;

public interface IRepository<T> {
    public void add(T entity);

    public void remove(int id);

    public void save();

    public void read();

    public ArrayList<T> getAll();
}
