package project.entity;

public class NevoieDTO extends Nevoie{
    private String nume;

    public NevoieDTO(Nevoie nevoie, String nume){
        super(nevoie);
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
}
