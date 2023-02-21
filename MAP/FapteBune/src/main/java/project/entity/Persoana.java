package project.entity;

import project.constant.Oras;

public class Persoana extends Entity {
    private String nume;

    private String prenume;

    private String username;

    private String parola;

    private Oras oras;

    private String strada;

    private String numarStrada;

    private String telefon;

    public Persoana(long id, String nume, String prenume, String username, String parola, String oras, String strada, String numarStrada, String telefon) {
        super(id);
        this.nume = nume;
        this.prenume = prenume;
        this.username = username;
        this.parola = parola;
        if (oras.equals("Bucuresti"))
            this.oras = Oras.Bucuresti;
        else if (oras.equals("Constanta"))
            this.oras = Oras.Constanta;
        else
            this.oras = Oras.Cluj_Napoca;
        this.strada = strada;
        this.numarStrada = numarStrada;
        this.telefon = telefon;
    }

    public String getNume() {
        return nume;
    }


    public String getPrenume() {
        return prenume;
    }

    public String getUsername() {
        return username;
    }


    public String getParola() {
        return parola;
    }


    public String getOras() {
        if (oras.equals(Oras.Constanta))
            return "Constanta";
        else if (oras.equals(Oras.Bucuresti))
            return "Bucuresti";
        else
            return "Cluj-Napoca";
    }

    public String getStrada() {
        return strada;
    }

    public String getNumarStrada() {
        return numarStrada;
    }

    public String getTelefon() {
        return telefon;
    }

    @Override
    public String toString() {
        return "nume: " + nume + ' ' + prenume + ' ' +
                ", oras: '" + oras + '\'' +
                ", telefon: '" + telefon + '\'' +
                '}';
    }
}
