package project.model;

import javax.persistence.*;

@Entity
@Table(name = "Sefi")
public class Sef implements Entitate<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nume;

    private String prenume;

    private String username;

    private String parola;

    public Sef(){
    }

    public Sef(String nume, String prenume, String username, String parola){
        this.nume = nume;
        this.prenume = prenume;
        this.username = username;
        this.parola = parola;
    }

    @Override
    public Long getId(){
        return id;
    }

    @Override
    public void setId(Long id){
        this.id = id;
    }

    public String getNume(){
        return nume;
    }

    public void setNume(String nume){
        this.nume = nume;
    }

    public String getPrenume(){
        return prenume;
    }

    public void setPrenume(String prenume){
        this.prenume = prenume;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getParola(){
        return parola;
    }

    public void setParola(String parola){
        this.parola = parola;
    }

    @Override
    public String toString(){
        return "Sef: " + nume + " " + prenume;
    }
}
