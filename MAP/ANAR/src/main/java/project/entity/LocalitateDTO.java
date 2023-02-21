package project.entity;

public class LocalitateDTO {
    private String nume;
    private String risc;

    public LocalitateDTO(String nume, String risc) {
        this.nume = nume;
        this.risc = risc;
    }

    @Override
    public String toString() {
        return "nume='" + nume + '\'' + ", risc='" + risc.substring(1) + '\'';
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getRisc() {
        return risc;
    }

    public void setRisc(String risc) {
        this.risc = risc;
    }
}
