package client.exception;

public class ModelException extends Exception{

    private final String message;

    public ModelException(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
