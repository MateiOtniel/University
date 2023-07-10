package project.repository.dbrepository;

import org.hibernate.SessionFactory;
import project.model.Sarcina;
import project.repository.interfaces.ISarcinaRepository;

public class SarcinaRepository implements ISarcinaRepository{
    private final SessionFactory sessionFactory;

    public SarcinaRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Sarcina sarcina) {
        try(var session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(sarcina);
            session.getTransaction().commit();
        }
    }

    @Override
    public Iterable<Sarcina> findByAngajatId(Long id) {
        try(var session = sessionFactory.openSession()){
            var query = session.createQuery("from Sarcina where angajat.id = :idAngajat");
            query.setParameter("idAngajat", id);
            return query.list();
        }
    }
}
