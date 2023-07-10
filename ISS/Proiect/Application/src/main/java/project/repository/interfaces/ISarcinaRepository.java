package project.repository.interfaces;

import project.model.Sarcina;

public interface ISarcinaRepository extends IRepository<Sarcina>{
    void save(Sarcina sarcina);

    Iterable<Sarcina> findByAngajatId(Long id);
}
