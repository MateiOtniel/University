package project.model;

import javax.persistence.*;

@Entity
@Table(name = "Prezente")
public class Prezenta implements Entitate<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idAngajat")
    private Angajat angajat;

    private String dataPrezenta;

    public Prezenta(Angajat angajat, String dataPrezenta){
        this.angajat = angajat;
        this.dataPrezenta = dataPrezenta;
    }

    public Prezenta(){
    }

    @Override
    public Long getId(){
        return id;
    }

    @Override
    public void setId(Long id){
        this.id = id;
    }

    public Angajat getAngajat(){
        return angajat;
    }

    public void setAngajat(Angajat angajat){
        this.angajat = angajat;
    }

    public String getDataPrezenta(){
        return dataPrezenta;
    }

    public void setDataPrezenta(String dataPrezenta){
        this.dataPrezenta = dataPrezenta;
    }
}
