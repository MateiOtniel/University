package project.validator;

import project.exception.InputException;

public class ValidareCota implements Validator<String>{
    @Override
    public void validate(String obj) throws InputException {
        try{
            double cota = Double.parseDouble(obj);
        }catch (NullPointerException | NumberFormatException e){
            throw new InputException("Input invalid!\n");
        }
    }
}
