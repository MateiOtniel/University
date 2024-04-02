package model;

import java.io.Serializable;
import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "Referees")
@AttributeOverride(name = "id", column = @Column(name = "id_r"))
public class Referee extends Entity<Integer> implements Serializable{

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public Referee(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Referee(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
