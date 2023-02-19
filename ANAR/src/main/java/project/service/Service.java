package project.service;

import project.entity.Localitate;
import project.entity.Rau;
import project.repository.RepositoryLocalitate;
import project.repository.RepositoryRau;

import java.util.ArrayList;
import java.util.Optional;

public class Service {

    private RepositoryRau repositoryRau;
    private RepositoryLocalitate repositoryLocalitate;

    private Service(RepositoryRau repositoryRau, RepositoryLocalitate repositoryLocalitate) {
        this.repositoryRau = repositoryRau;
        this.repositoryLocalitate = repositoryLocalitate;
    }

    public static Service getInstance() {
        return new Service(new RepositoryRau("jdbc:postgresql://localhost:5432/postgres",
                "postgres", "Respectu20."),
                new RepositoryLocalitate("jdbc:postgresql://localhost:5432/postgres",
                        "postgres", "Respectu20."));
    }

    public ArrayList<Rau> getAllRauri() {
        return repositoryRau.getAll();
    }

    public ArrayList<Localitate> getAllLocalitati() {
        return repositoryLocalitate.getAll();
    }

    public Rau getRauById(int id){
        return repositoryRau.getById(id);
    }

    public void modificareCota(Rau rauSelectat, String numar) {
        repositoryRau.modifcareCota(rauSelectat, numar);
    }
}
