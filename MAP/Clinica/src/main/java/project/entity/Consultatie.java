package project.entity;

import java.time.LocalDate;

public class Consultatie {
    private int id;
    private int idMedic;
    private String CNPPacient;
    private String numePacient;
    private LocalDate data;
    private int ora;

    public Consultatie(int id, int idMedic, String CNPPacient, String numePacient, LocalDate data, int ora) {
        this.id = id;
        this.idMedic = idMedic;
        this.CNPPacient = CNPPacient;
        this.numePacient = numePacient;
        this.data = data;
        this.ora = ora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMedic() {
        return idMedic;
    }

    public void setIdMedic(int idMedic) {
        this.idMedic = idMedic;
    }

    public String getCNPPacient() {
        return CNPPacient;
    }

    public void setCNPPacient(String CNPPacient) {
        this.CNPPacient = CNPPacient;
    }

    public String getNumePacient() {
        return numePacient;
    }

    public void setNumePacient(String numePacient) {
        this.numePacient = numePacient;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getOra() {
        return ora;
    }

    public void setOra(int ora) {
        this.ora = ora;
    }

    @Override
    public String toString() {
        return "Consultatie{" +
                "numePacient='" + numePacient + '\'' +
                ", data=" + data +
                ", ora=" + ora +
                '}';
    }
}
