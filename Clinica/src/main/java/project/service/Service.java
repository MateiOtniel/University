package project.service;

import project.entity.Consultatie;
import project.entity.Medic;
import project.entity.Sectie;
import project.repository.RepositoryConsultatie;
import project.repository.RepositoryMedic;
import project.repository.RepositorySectie;

import java.time.LocalDate;
import java.util.ArrayList;

public class Service {

    private RepositorySectie repositorySectie;

    private RepositoryMedic repositoryMedic;

    private RepositoryConsultatie repositoryConsultatie;


    public Service(RepositorySectie repositorySectie, RepositoryMedic repositoryMedic, RepositoryConsultatie repositoryConsultatie) {
        this.repositorySectie = repositorySectie;
        this.repositoryMedic = repositoryMedic;
        this.repositoryConsultatie = repositoryConsultatie;
    }

    public static Service getInstance(){
        return new Service(new RepositorySectie("jdbc:postgresql://localhost:5432/postgres",
                "postgres", "Respectu20."),
                new RepositoryMedic("jdbc:postgresql://localhost:5432/postgres",
                        "postgres", "Respectu20."),
                new RepositoryConsultatie("jdbc:postgresql://localhost:5432/postgres",
                        "postgres", "Respectu20."));
    }

    public ArrayList<Sectie> getAllSectii(){
        return repositorySectie.getAll();
    }

    public ArrayList<Medic> getAllMedici(){
        return repositoryMedic.getAll();
    }

    public ArrayList<Consultatie> getAllConstultatii() {
        return repositoryConsultatie.getAll();
    }

    public Medic getMedicById(int id){
        return repositoryMedic.getById(id);
    }

    public void programeaza(Medic selectedMedic, String nume, String CNP, LocalDate date, int hour) {
        int id = repositoryConsultatie.getMaxId();
        Consultatie consultatie = new Consultatie(id + 1, selectedMedic.getId(), CNP, nume, date, hour);
        repositoryConsultatie.add(consultatie);
    }
}
