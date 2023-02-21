package validators;

import exceptions.InputException;

public class NameValidator implements Validator<String> {

    @Override
    public void validate(String Obj) throws InputException {
        if(! Obj.matches("[A-Z][a-z]*"))
            throw new InputException();
    }
}
