package project.repository.dbrepository;

import org.hibernate.SessionFactory;
import project.model.Prezenta;
import project.repository.interfaces.IPrezentaRepository;

public class PrezentaRepository implements IPrezentaRepository {
    private final SessionFactory sessionFactory;

    public PrezentaRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Prezenta prezenta) {
        if(!angajatAlreadyCheckedIn(prezenta))
            try(var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(prezenta);
            session.getTransaction().commit();
        }
    }

    @Override
    public Iterable<Prezenta> findAll() {
        try(var session = sessionFactory.openSession()) {
            session.beginTransaction();
            Iterable<Prezenta> prezente = session.createQuery("from Prezenta", Prezenta.class).list();
            session.getTransaction().commit();
            return prezente;
        }
    }

    private boolean angajatAlreadyCheckedIn(Prezenta prezenta) {
        try(var session = sessionFactory.openSession()) {
            var query = session.createQuery("from Prezenta where angajat.id = :idAngajat and dataPrezenta = :data");
            query.setParameter("idAngajat", prezenta.getAngajat().getId());
            query.setParameter("data", prezenta.getDataPrezenta());
            return query.list().size() > 0;
        }
    }
}
