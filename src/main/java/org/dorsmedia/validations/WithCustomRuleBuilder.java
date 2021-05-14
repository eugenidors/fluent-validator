package org.dorsmedia.validations;

import org.dorsmedia.validations.model.CustomValidation;

import java.util.function.Supplier;

public class WithCustomRuleBuilder {
    Validator validator;
    Supplier<Boolean> predicate;
    String errorMessage;

    protected WithCustomRuleBuilder(Validator validator, Supplier<Boolean> predicate) {
        this.validator = validator;
        this.predicate = predicate;
        this.errorMessage = "Error de validación";
    }

    public Validator withErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        if(validator==null){
            validator = new Validator();
        }
        validator.addValidation(new CustomValidation(predicate, errorMessage));
        return validator;
    }

}
