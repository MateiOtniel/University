package project.exception;

public class InputException extends Exception{
    private final String message;

    public InputException(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
