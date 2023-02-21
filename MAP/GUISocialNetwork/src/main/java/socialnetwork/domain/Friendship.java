package socialnetwork.domain;

import java.time.LocalDateTime;

public class Friendship {
    private int from;
    private int to;
    LocalDateTime time;

    private int state;

    @Override
    public String toString() {
        if (state == 1)
            return "From user's ID: " + to + "| Status: Accepted" + " | Date: " + time.toString().substring(0, 9);
        if (state == 0)
            return "From user's ID: " + to + "| Status: Pending" + " | Date: " + time.toString().substring(0, 9);
        return "From user's ID: " + to + "| Status: Sent" + " | Date: " + time.toString().substring(0, 9) ;
    }

    public Friendship(int id1, int id2, LocalDateTime time, int state) {
        this.from = id1;
        this.to = id2;
        this.time = time;
        this.state = state;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
