package socialnetwork.test;

import socialnetwork.exceptions.InputException;
import socialnetwork.validators.MailValidator;
import socialnetwork.validators.NameValidator;

import java.util.Objects;

public class Test {
    public void runAllTests() throws InputException {
        System.out.println("Starting tests");
        testValidator();
        System.out.println("---Tests passed---");
    }

    private void testValidator() throws InputException {
        NameValidator validator = new NameValidator();
        validator.validate("John");
        validator.validate("Cena");
        try {
            validator.validate("lalala");
        } catch (InputException e) {
            assert (Objects.equals(e.getMessage(), "Input Invalid!\n"));
        }

        try {
            validator.validate("");
        } catch (InputException e) {
            assert (Objects.equals(e.getMessage(), "Input Invalid!\n"));
        }

        try {
            validator.validate("6John");
        } catch (InputException e) {
            assert (Objects.equals(e.getMessage(), "Input Invalid!\n"));
        }

        try {
            validator.validate("John.");
        } catch (InputException e) {
            assert (Objects.equals(e.getMessage(), "Input Invalid!\n"));
        }

        MailValidator validator1 = new MailValidator();

        validator1.validate("matei.otniel20@gmail.com");
        validator1.validate("matei.otniel20@yahoo.com");
        validator1.validate("matei.otniel20@domain.com");

        try {
            validator1.validate("@gmail.com");
        } catch (InputException e) {
            assert (Objects.equals(e.getMessage(), "Input Invalid!\n"));
        }
        try {
            validator1.validate("dsa@gmail.com2");
        } catch (InputException e) {
            assert (Objects.equals(e.getMessage(), "Input Invalid!\n"));
        }
        try {
            validator1.validate("dsa@gmailcom");
        } catch (InputException e) {
            assert (Objects.equals(e.getMessage(), "Input Invalid!\n"));
        }
        try {
            validator1.validate("dsa@.com");
        } catch (InputException e) {
            assert (Objects.equals(e.getMessage(), "Input Invalid!\n"));
        }
    }
}
