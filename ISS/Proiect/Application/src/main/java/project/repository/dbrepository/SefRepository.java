package project.repository.dbrepository;

import org.hibernate.SessionFactory;
import project.exception.MyException;
import project.repository.interfaces.ISefRepository;

public class SefRepository implements ISefRepository{
    private final SessionFactory sessionFactory;

    public SefRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Object findByUsernameAndPassword(String username, String password) throws MyException{
        try(var session = sessionFactory.openSession()){
            session.beginTransaction();
            var sef = session.createQuery("from Sef where username = :username and parola = :password")
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
            session.getTransaction().commit();
            if(sef == null)
                throw new MyException("Username sau parola gresita!");
            return sef;
        }
    }
}
