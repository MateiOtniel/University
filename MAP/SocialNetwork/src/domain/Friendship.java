package domain;

import java.time.LocalDateTime;

public class Friendship {
    private int from;
    private int to;
    LocalDateTime time;

    @Override
    public String toString() {
        return to + "," +  time.toString();
    }

    public Friendship(int id1, int id2, LocalDateTime time) {
        this.from = id1;
        this.to = id2;
        this.time = time;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
