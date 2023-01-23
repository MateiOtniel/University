package validators;

import exceptions.InputException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailValidator implements Validator<String> {
    @Override
    public void validate(String Obj) throws InputException {
        String regex = "^(.+)@(.+).com$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(Obj);
        if(!matcher.matches())
            throw new InputException();
    }
}
