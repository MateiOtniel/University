package project.service;

import project.entity.Pacient;
import project.entity.Pat;
import project.repository.RepositoryPacient;
import project.repository.RepositoryPat;

import java.util.ArrayList;

public class Service {

    private final RepositoryPat repositoryPat;

    private final RepositoryPacient repositoryPacient;

    private Service(RepositoryPat repositoryPat, RepositoryPacient repositoryPacient){
        this.repositoryPat = repositoryPat;
        this.repositoryPacient = repositoryPacient;
    }

    public static Service getInstance(){
        return new Service(new RepositoryPat("jdbc:postgresql://localhost:5432/postgres",
                "postgres", "Respectu20."),
                new RepositoryPacient("jdbc:postgresql://localhost:5432/postgres",
                        "postgres", "Respectu20."));
    }

    public ArrayList<Pat> getAllPaturi() {
        return repositoryPat.getAll();
    }

    public ArrayList<Pacient> getAllPacienti() {
        return repositoryPacient.getAll();
    }

    public boolean suntPaturiDisponibile(String tip) {
        return repositoryPat.suntPaturiDisponibile(tip);
    }

    public void adaugaPacient(String cnp, String tip) {
        repositoryPat.adaugaPacient(cnp, tip);
    }
}
