package project.validator;

import project.exception.InputException;

public class ValidateName implements Validator<String> {
    public void validate(String name) throws InputException {
        if (!name.matches("[A-Z][a-z]*"))
            throw new InputException("Input invalid!");
    }
}
