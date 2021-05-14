package org.dorsmedia.validations.model;

import org.dorsmedia.exceptions.ValidationException;

public interface Validation {

    boolean isValid();

    default boolean isNotValid() {
        return !isValid();
    }

    String getErrorMessage();

    String getValueName();


    default void checkAndThrow() throws ValidationException {
        if (this.isNotValid()) {
            throw new ValidationException(new ErrorValidation(this.getValueName(), getErrorMessage()));
        }
    }
    default ErrorValidation checkError() {
        if (this.isNotValid()) {
            return new ErrorValidation(this.getValueName(), this.getErrorMessage());
        }
        return null;
    }

}
