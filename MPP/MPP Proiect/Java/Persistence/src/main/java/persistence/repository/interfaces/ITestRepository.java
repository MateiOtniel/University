package persistence.repository.interfaces;

import model.Test;

public interface ITestRepository extends IRepository<Integer, Test>{
    void update(int id, Test test);
}
