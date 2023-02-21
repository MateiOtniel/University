package project.validator;

import project.exception.InputException;

public interface Validator<T> {
    public void validate(T entity) throws InputException;
}
