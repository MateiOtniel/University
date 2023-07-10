package project.repository.dbrepository;

import com.sun.xml.bind.v2.runtime.reflect.opt.Const;
import project.exception.MyException;
import project.model.Angajat;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import project.repository.interfaces.IAngajatRepository;
import project.utils.Constants;

public class AngajatRepository implements IAngajatRepository{

    private final SessionFactory sessionFactory;

    public AngajatRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public void save(Angajat angajat) throws MyException {
        Iterable<Angajat> angajati = this.findAll();
        for(Angajat currentAngajat : angajati){
            if(currentAngajat.getUsername().equals(angajat.getUsername()))
                throw new MyException(Constants.USERNAME_ALREADY_EXISTS);
        }
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(angajat);
            session.getTransaction().commit();
        }
    }

    @Override
    public Object findByUsernameAndPassword(String username, String password) throws MyException{
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Object angajat = session.createQuery("from Angajat where username = :username and parola = :password")
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
            session.getTransaction().commit();
            if(angajat == null){
                throw new MyException(Constants.USERNAME_OR_PASSWORD_ERROR);
            }
            return angajat;
        }
    }

    @Override
    public Iterable<Angajat> findAll(){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Iterable<Angajat> angajati = session.createQuery("from Angajat", Angajat.class).list();
            session.getTransaction().commit();
            return angajati;
        }
    }

    @Override
    public void modify(Long id, Angajat angajat) throws MyException {
        Iterable<Angajat> angajati = this.findAll();
        for(Angajat currentAngajat : angajati){
            if(currentAngajat.getUsername().equals(angajat.getUsername())
                    && !currentAngajat.getId().equals(id))
                throw new MyException(Constants.USERNAME_ALREADY_EXISTS);
        }

        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.createQuery("update Angajat set nume = :nume, prenume = :prenume, username = :username, parola = :parola where id = :id")
                    .setParameter("nume", angajat.getNume())
                    .setParameter("prenume", angajat.getPrenume())
                    .setParameter("username", angajat.getUsername())
                    .setParameter("parola", angajat.getParola())
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Long id) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.createQuery("delete from Angajat where id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }
}
