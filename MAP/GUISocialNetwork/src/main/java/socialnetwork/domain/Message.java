package socialnetwork.domain;

public class Message {
    private String message;
    private int from;
    private int to;
    private String date;

    /***
     *  Class constructor.
     * @param message
     * @param from
     * @param to
     */
    public Message(int from, int to, String message, String date) {
        this.message = message;
        this.from = from;
        this.to = to;
        this.date = date;
    }

    /***
     * Returns the message String.
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /***
     *  Returns the from String.
     * @return
     */
    public int getFrom() {
        return from;
    }

    /***
     * Returns the to String
     * @return
     */
    public int getTo() {
        return to;
    }

    public String getDate() {
        return date;
    }
}
