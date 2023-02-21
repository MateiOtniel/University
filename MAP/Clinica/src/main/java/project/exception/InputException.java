package project.exception;

public class InputException extends Exception{
    private String message;

    public InputException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
