package project.validator;

import project.exception.InputException;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateName {
    public void validate(String name) throws InputException {
        String namRegExpVar = "[A-Z][A-Za-z ]{2,}";

        Pattern pVar = Pattern.compile(namRegExpVar);
        Matcher mVar = pVar.matcher(name);
        if(!mVar.matches())
            throw new InputException("Input invalid!");
    }
}
