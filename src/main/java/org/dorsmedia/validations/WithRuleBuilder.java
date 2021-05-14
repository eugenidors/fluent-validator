package org.dorsmedia.validations;

import lombok.AllArgsConstructor;
import org.dorsmedia.validations.model.SimpleValidation;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@AllArgsConstructor
public class WithRuleBuilder<T> {
    List<T> values;
    Predicate<T> predicate;
    Validator validator;
    String fieldName;
    String errorMessage;

    public WithRuleBuilder(String fieldName, List<T> values, Predicate<T> predicate, Validator validator) {
        this.values = values;
        this.predicate = predicate;
        this.validator = validator;
        this.fieldName = fieldName;
        this.errorMessage = "Error de validación";
    }

    public WithRuleBuilder<T> and(Predicate<? super T> andConstraint) {
        this.predicate = this.predicate.and(andConstraint);
        return this;
    }

    public WithRuleBuilder<T> or(Predicate<? super T> andConstraint) {
        this.predicate = this.predicate.or(andConstraint);
        return this;
    }

    public Validator withErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        if(validator==null){
            validator = new Validator();
        }
        for (T v : values) {
            validator.addValidation(new SimpleValidation<>(v, predicate, getErrorDescription()));
        }
        return validator;
    }

    private String getErrorDescription() {
        return Arrays.asList(fieldName, errorMessage).stream().collect(Collectors.joining(" = "));
    }

}