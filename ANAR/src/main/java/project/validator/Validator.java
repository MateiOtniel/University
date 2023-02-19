package project.validator;

import project.exception.InputException;

public interface Validator<T> {
    void validate(T obj) throws InputException;
}
