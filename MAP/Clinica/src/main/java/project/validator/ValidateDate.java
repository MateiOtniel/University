package project.validator;

import project.exception.InputException;

import java.time.LocalDate;

public class ValidateDate {
    public void validate(LocalDate date) throws InputException {
        if(date.isBefore(LocalDate.now()))
            throw new InputException("Invalid input!");
    }
}
