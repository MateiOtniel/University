package socialnetwork.validators;

import socialnetwork.exceptions.InputException;

public interface Validator<T> {
    void validate(T Obj) throws InputException;
}
