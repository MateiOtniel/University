package exceptions;

public final class RepositoryException extends Exception{
    private final String message;

    /***
     * Default constructor
     */
    public RepositoryException(){
        this.message = "Input invalid!";
    }

    /***
     * Constructor with specific message.
     * @param message
     */
    public RepositoryException(String message){
        this.message = message;
    }

    /***
     * Implements the function from interface.
     * @return
     */
    @Override
    public String getMessage(){
        return message;
    }
}