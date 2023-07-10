package project.repository.interfaces;

import project.model.Prezenta;

public interface IPrezentaRepository extends IRepository<Prezenta>{
    void save(Prezenta prezenta);

    Iterable<Prezenta> findAll();
}
