package exceptions;

public final class InputException extends Exception {
    private final String message;

    /***
     * Default constructor.
     */
    public InputException() {
        this.message = "Invalid input!";
    }

    /***
     * Another constructor.
     * @param message
     */
    public InputException(String message) {
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
