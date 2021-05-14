package org.dorsmedia.exceptions;

import org.dorsmedia.validations.model.ErrorValidation;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends Exception {

    List<ErrorValidation> errors;

    public List<ErrorValidation> getErrors() {
        return errors;
    }

    public ValidationException(List<ErrorValidation> errors) {
        super(" Error de validaciones :: ");
        this.errors = errors;
    }
    public ValidationException(ErrorValidation error) {
        super(" Error de validaciones :: ");
        this.errors = new ArrayList<ErrorValidation>();
        this.errors.add(error);
    }

}
