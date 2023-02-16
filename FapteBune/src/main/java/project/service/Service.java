package project.service;

import project.entity.Nevoie;
import project.entity.Persoana;
import project.repository.RepositoryNevoie;
import project.repository.RepositoryPersoana;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Service {

    private RepositoryPersoana repositoryPersoana;
    private RepositoryNevoie repositoryNevoie;

    private Service(RepositoryPersoana repositoryPersoana, RepositoryNevoie repositoryNevoie) {
        this.repositoryPersoana = repositoryPersoana;
        this.repositoryNevoie = repositoryNevoie;
    }

    public static Service getInstance() {
        return new Service(new RepositoryPersoana("jdbc:postgresql://localhost:5432/postgres",
                "postgres", "Respectu20."),
                new RepositoryNevoie("jdbc:postgresql://localhost:5432/postgres",
                        "postgres", "Respectu20."));
    }

    public ArrayList<Persoana> getAllPersoane() {
        return repositoryPersoana.getAll();
    }

    public void addPersoana(String text, String text1, String text2, String text3, String value, String text4, String text5, String text6) {
        long id = repositoryPersoana.getMaxId();
        repositoryPersoana.add(new Persoana(id + 1, text, text1, text2, text3, value, text4, text5, text6));
    }

    public long getMaxId() {
        return repositoryPersoana.getMaxId();
    }

    public ArrayList<Nevoie> getAllNevoi() {
        return repositoryNevoie.getAll();
    }

    public Persoana getPersoanaById(long id) {
        return repositoryPersoana.getById(id);
    }

    public void ajuta(Nevoie nevoie, Persoana persoana) {
        repositoryNevoie.ajuta(nevoie, persoana);
    }

    public void addNevoie(Persoana persoana, String titluCerere, String descriereCerere, LocalDateTime dataCerere) {
        long id = repositoryNevoie.getMaxId();
        repositoryNevoie.add(new Nevoie(id + 1, titluCerere, descriereCerere, dataCerere, persoana.getId(), 0, "Caut erou!"));
    }
}
