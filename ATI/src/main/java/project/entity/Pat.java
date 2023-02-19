package project.entity;

public class Pat {
    private int id;
    private String tip;
    private boolean ventilatie;
    private String pacientCNP;

    public Pat(int id, String tip, boolean ventilatie, String pacientCNP) {
        this.id = id;
        this.tip = tip;
        this.ventilatie = ventilatie;
        this.pacientCNP = pacientCNP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public boolean getVentilatie() {
        return ventilatie;
    }

    public void setVentilatie(boolean ventilatie) {
        this.ventilatie = ventilatie;
    }

    public String getPacientCNP() {
        return pacientCNP;
    }

    public void setPacientCNP(String pacientCNP) {
        this.pacientCNP = pacientCNP;
    }

    @Override
    public String toString() {
        return "Pat{" +
                "id=" + id +
                ", tip='" + tip + '\'' +
                ", ventilatie=" + ventilatie +
                ", pacientCNP='" + pacientCNP + '\'' +
                '}';
    }
}
