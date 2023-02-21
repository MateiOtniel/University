package domain;

public class Message{
    private String message;
    private String from;
    private String to;

    /***
     *  Class constructor.
     * @param message
     * @param from
     * @param to
     */
    public Message(String message, String from, String to) {
        this.message = message;
        this.from = from;
        this.to = to;
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
    public String getFrom() {
        return from;
    }

    /***
     * Returns the to String
     * @return
     */
    public String getTo() {
        return to;
    }
}
