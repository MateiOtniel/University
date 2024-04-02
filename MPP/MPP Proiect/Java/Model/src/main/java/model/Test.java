package model;

import java.io.Serializable;

public class Test extends Entity<Integer> implements Serializable{

    private int idParticipant;

    private int idReferee;

    private int points;

    private String date;

    public Test(){}

    public Test(int idParticipant, int idReferee, int points, String date) {
        this.idParticipant = idParticipant;
        this.idReferee = idReferee;
        this.points = points;
        this.date = date;
    }

    public int getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(int idParticipant) {
        this.idParticipant = idParticipant;
    }

    public int getIdReferee() {
        return idReferee;
    }

    public void setIdReferee(int idReferee) {
        this.idReferee = idReferee;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "idParticipant= " + idParticipant +
                ", idReferee= " + idReferee +
                ", points= " + points +
                ", date= '" + date + '\'';
    }
}
