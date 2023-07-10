package project.service;

import project.exception.MyException;
import project.model.Angajat;
import org.hibernate.SessionFactory;
import project.model.Prezenta;
import project.model.Sarcina;
import project.model.Sef;
import project.repository.dbrepository.AngajatRepository;
import project.repository.dbrepository.PrezentaRepository;
import project.repository.dbrepository.SarcinaRepository;
import project.repository.dbrepository.SefRepository;
import project.repository.interfaces.IAngajatRepository;
import project.repository.interfaces.IPrezentaRepository;
import project.repository.interfaces.ISarcinaRepository;
import project.repository.interfaces.ISefRepository;
import java.util.List;
import java.util.LinkedList;

public class Service {

    private static Service instance;

    private final IAngajatRepository angajatRepository;

    private final IPrezentaRepository prezentaRepository;

    private final ISarcinaRepository sarcinaRepository;

    private final ISefRepository sefRepository;

    private Service(AngajatRepository angajatRepository, PrezentaRepository prezentaRepository,
                    SarcinaRepository sarcinaRepository, SefRepository sefRepository) {
        this.angajatRepository = angajatRepository;
        this.prezentaRepository = prezentaRepository;
        this.sarcinaRepository = sarcinaRepository;
        this.sefRepository = sefRepository;
    }

    public static Service getInstance(SessionFactory sessionFactory) {
        if(instance == null) {
            instance = new Service(new AngajatRepository(sessionFactory),
                    new PrezentaRepository(sessionFactory),
                    new SarcinaRepository(sessionFactory),
                    new SefRepository(sessionFactory));
        }
        return instance;
    }


    public Object login(String username, String password) throws MyException {
        Object user = null;
        try {
            user = angajatRepository.findByUsernameAndPassword(username, password);
        } catch(MyException e) {
            try {
                user = sefRepository.findByUsernameAndPassword(username, password);
            } catch(MyException ex) {
                throw ex;
            }
        }
        return user;
    }

    public Iterable<Angajat> getAllAngajati() {
       Iterable<Angajat> angajati = angajatRepository.findAll();
       List<Angajat> angajatiPrezenti = new LinkedList<>();
       Iterable<Prezenta> prezente = prezentaRepository.findAll();
       for(Angajat angajat : angajati){
           for(Prezenta prezenta : prezente) {
               if(prezenta.getAngajat().getId().equals(angajat.getId())
                       && prezenta.getDataPrezenta().equals(java.time.LocalDate.now().toString())) {
                   angajatiPrezenti.add(angajat);
                   break;
               }
           }
       }
       return angajatiPrezenti;
    }

    public void addAngajat(String nume, String prenume, String username, String password) throws MyException {
        angajatRepository.save(new Angajat(nume, prenume, username, password));
    }

    public void modifyAngajat(Long id, String nume, String prenume, String username, String password) throws MyException {
        angajatRepository.modify(id, new Angajat(nume, prenume, username, password));
    }

    public void deleteAngajat(Long id) {
        angajatRepository.delete(id);
    }


    public void addSarcina(Long idAngajat, Long idSef, String descriere, String durata) {
        Angajat angajat = new Angajat();
        angajat.setId(idAngajat);
        Sef sef = new Sef();
        sef.setId(idSef);
        sarcinaRepository.save(new Sarcina(angajat, sef, descriere, durata));
    }

    public void addPrezenta(Angajat angajat) {
        //get current date and time in format yyyy-MM-dd as string
        String date = java.time.LocalDate.now().toString();
        prezentaRepository.save(new Prezenta(angajat, date));
    }

    public Iterable<Sarcina> getAllSarciniByAngajatId(Long id) {
        return sarcinaRepository.findByAngajatId(id);
    }
}
