package project.entity;

public class Pacient {
    private int id;
    private String CNP;
    private int varsta;
    private boolean prematur;
    private String diagnostic;
    private int gravitate;

    public Pacient(int id, String CNP, int varsta, boolean prematur, String diagnostic, int gravitate) {
        this.id = id;
        this.CNP = CNP;
        this.varsta = varsta;
        this.prematur = prematur;
        this.diagnostic = diagnostic;
        this.gravitate = gravitate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public boolean isPrematur() {
        return prematur;
    }

    public void setPrematur(boolean prematur) {
        this.prematur = prematur;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public int getGravitate() {
        return gravitate;
    }

    public void setGravitate(int gravitate) {
        this.gravitate = gravitate;
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "CNP='" + CNP + '\'' +
                ", diagnostic='" + diagnostic + '\'' +
                '}';
    }
}
