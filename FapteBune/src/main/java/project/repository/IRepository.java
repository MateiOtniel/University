package project.repository;

public interface IRepository<T> {
    void add(T entity);

    void save();

    void read();
}
