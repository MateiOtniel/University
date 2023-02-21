package socialnetwork.exceptions;

public class ExitException extends Exception {
    /***
     * Implements the function from interface.
     * @return
     */
    @Override
    public String getMessage(){
        return "You have exited the program";
    }
}
