package project.entity;

public class Rau {
    private int id;
    private String nume;
    private double cotaMedie;

    public Rau(int id, String nume, double cotaMedie) {
        this.id = id;
        this.nume = nume;
        this.cotaMedie = cotaMedie;
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

    public double getCotaMedie() {
        return cotaMedie;
    }

    public void setCotaMedie(double cotaMedie) {
        this.cotaMedie = cotaMedie;
    }

    @Override
    public String toString() {
        return "Rau{" +
                "nume='" + nume + '\'' +
                ", cotaMedie=" + cotaMedie +
                '}';
    }
}
