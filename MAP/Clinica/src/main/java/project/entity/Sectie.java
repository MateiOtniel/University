package project.entity;

public class Sectie {
    private int id;
    private String nume;
    private int idSefDeSectie;
    private float pretPerConsultatie;
    private float durataMaxima;

    public Sectie(int id, String nume, int idSefDeSectie, float pretPerConsultatie, float durataMaxima) {
        this.id = id;
        this.nume = nume;
        this.idSefDeSectie = idSefDeSectie;
        this.pretPerConsultatie = pretPerConsultatie;
        this.durataMaxima = durataMaxima;
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

    public int getIdSefDeSectie() {
        return idSefDeSectie;
    }

    public void setIdSefDeSectie(int idSefDeSectie) {
        this.idSefDeSectie = idSefDeSectie;
    }

    public float getPretPerConsultatie() {
        return pretPerConsultatie;
    }

    public void setPretPerConsultatie(float pretPerConsultatie) {
        this.pretPerConsultatie = pretPerConsultatie;
    }

    public float getDurataMaxima() {
        return durataMaxima;
    }

    public void setDurataMaxima(float durataMaxima) {
        this.durataMaxima = durataMaxima;
    }

    @Override
    public String toString() {
        return "Sectie{" +
                "nume='" + nume + '\'' +
                ", idSefDeSectie=" + idSefDeSectie +
                ", pret=" + pretPerConsultatie +
                ", durata=" + durataMaxima +
                '}';
    }
}
