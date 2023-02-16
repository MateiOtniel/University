package project.validator;

import project.exception.InputException;

public class ValidatePhoneNumber implements Validator<String> {

    public void validate(String str) throws InputException {
        if (str.length() < 6)
            throw new InputException("Phone number invalid!");
        for (int i = 0; i < str.length(); i++)
            if (str.charAt(i) < '0' || str.charAt(i) > '9')
                throw new InputException("Phone number invalid!");
    }
}
