package project.entity;

public class Medic {
    private int id;
    private int idSectie;
    private String nume;

    private float vechime;
    private boolean rezident;

    public Medic(int id, int idSectie, String nume, float vechime, boolean rezident) {
        this.id = id;
        this.idSectie = idSectie;
        this.nume = nume;
        this.vechime = vechime;
        this.rezident = rezident;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSectie() {
        return idSectie;
    }

    public void setIdSectie(int idSectie) {
        this.idSectie = idSectie;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public float getVechime() {
        return vechime;
    }

    public void setVechime(float vechime) {
        this.vechime = vechime;
    }

    public boolean isRezident() {
        return rezident;
    }

    public void setRezident(boolean rezident) {
        this.rezident = rezident;
    }

    @Override
    public String toString() {
        return "Medic{" +
                "nume='" + nume + '\'' +
                '}';
    }
}
