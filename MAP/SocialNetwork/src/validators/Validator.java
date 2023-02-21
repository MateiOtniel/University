package validators;

import exceptions.InputException;

public interface Validator<T> {
    void validate(T Obj) throws InputException;
}
