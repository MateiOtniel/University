package model;

import java.io.Serializable;

public class Participant extends Entity<Integer> implements Serializable{

    private String name;

    private int totalPoints;

    public Participant(String name, int totalPoints) {
        this.name = name;
        this.totalPoints = totalPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + " Points: " + this.totalPoints;
    }
}
