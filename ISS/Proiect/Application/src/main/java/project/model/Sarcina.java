package project.model;

import javax.persistence.*;

@Entity
@Table(name = "Sarcini")
public class Sarcina implements Entitate<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idAngajat")
    private Angajat angajat;

    @ManyToOne
    @JoinColumn(name = "idSef")
    private Sef sef;

    private String descriere;

    private String durata;

    public Sarcina(){
    }

    public Sarcina(Angajat angajat, Sef sef, String descriere, String durata){
        this.angajat = angajat;
        this.sef = sef;
        this.descriere = descriere;
        this.durata = durata;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Angajat getAngajat(){
        return angajat;
    }

    public void setAngajat(Angajat angajat){
        this.angajat = angajat;
    }

    public Sef getSef(){
        return sef;
    }

    public void setSef(Sef sef){
        this.sef = sef;
    }

    public String getDescriere(){
        return descriere;
    }

    public void setDescriere(String descriere){
        this.descriere = descriere;
    }

    public String getDurata(){
        return durata;
    }

    public void setDurata(String durata){
        this.durata = durata;
    }

    @Override
    public String toString(){
        return "Descriere: " + descriere + "\nDurata: " + durata;
    }
}
