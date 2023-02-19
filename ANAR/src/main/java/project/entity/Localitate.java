package project.entity;

public class Localitate {
    private int id;
    private String nume;
    private int idRau;
    private double cmdr;
    private double cma;

    public Localitate(int id, String nume, int idRau, double cmdr, double cma) {
        this.id = id;
        this.nume = nume;
        this.idRau = idRau;
        this.cmdr = cmdr;
        this.cma = cma;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getIdRau() {
        return idRau;
    }

    public void setIdRau(int idRau) {
        this.idRau = idRau;
    }

    public double getCmdr() {
        return cmdr;
    }

    public void setCmdr(double cmdr) {
        this.cmdr = cmdr;
    }

    public double getCma() {
        return cma;
    }

    public void setCma(double cma) {
        this.cma = cma;
    }
}
