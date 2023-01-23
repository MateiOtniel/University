package socialnetwork.exceptions;

public final class NetworkException extends Exception {
    private final String message;

    /***
     * Default constructor.
     */
    public NetworkException() {
        this.message = "Invalid input!";
    }

    /***
     *  Constructor with a special message.
     * @param message
     */
    public NetworkException(String message) {
        this.message = message;
    }

    /***
     * Implements the function from interface.
     * @return
     */
    @Override
    public String getMessage() {
        return message;
    }
}
