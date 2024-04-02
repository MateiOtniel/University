package client.validator;

import client.exception.ModelException;

public class ValidateNumber{
    public static void Validate(String entity) throws ModelException{
        try{
            int number = Integer.parseInt(entity);
        }catch(NumberFormatException ex){
            throw new ModelException("Number is not valid!");
        }
    }
}
