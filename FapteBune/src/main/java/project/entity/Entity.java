package project.entity;

public class Entity {
    private long id;
    private static final long serialVersionUID = -3118771398303557304L;

    public Entity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id = " + id + " ";
    }
}
