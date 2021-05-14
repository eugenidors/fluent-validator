package org.dorsmedia.validations;

import org.dorsmedia.exceptions.ValidationException;
import org.dorsmedia.validations.model.ErrorValidation;
import org.dorsmedia.validations.model.Validation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static org.dorsmedia.validations.Constraint.*;

public class Validator {


    List<Validation> validations = new ArrayList<>();

    public static <T> WithValueBuilder<T> createRuleFor(String fieldName, T value) {
        return new WithValueBuilder<T>(new Validator(), value, fieldName);
    }

    public static <T> WithValueBuilder<T> createRuleFor(T value) {
        return createRuleFor("", value);
    }

    public static WithCustomRuleBuilder createCustomRule(Supplier<Boolean> predicate) {
        return new WithCustomRuleBuilder(new Validator(), predicate);
    }

    public <T> Validator addValidation(Validation validation) {
        validations.add(validation);
        return this;
    }

    public <T> Validator addValidation(Validator validator) {
        validations.addAll(validator.validations);
        return this;
    }

    public List<ErrorValidation> checkErrors() {
        List<ErrorValidation> errors = new ArrayList<>();
        validations.forEach(validation -> {
            if (validation.isNotValid()) {
                errors.add(new ErrorValidation(validation.getValueName(), validation.getErrorMessage()));
            }
        });
        return errors;
    }

    public boolean hasError() {
        return checkFirstError() != null;
    }

    public void checkErrorsAndThrow() throws ValidationException {
        List<ErrorValidation> errors = checkErrors();
        if (errors.size() > 0) {
            throw new ValidationException(errors);
        }
    }

    public ErrorValidation checkFirstError() {
        for (Validation validation : validations) {
            if (validation.isNotValid()) {
                return new ErrorValidation(validation.getValueName(), validation.getErrorMessage());
            }
        }
        return null;
    }

    public void checkFirstErrorAndThrow() throws ValidationException {
        ErrorValidation error = checkFirstError();
        if (error != null) {
            throw new ValidationException(error);
        }
    }

    public <T> WithValueBuilder<T> addRuleFor(String fieldName, T value) {
        return new WithValueBuilder<T>(this, value, fieldName);
    }

    public <T> WithValueBuilder<T> addRuleFor(T value) {
        return createRuleFor("", value);
    }

    @SafeVarargs
    public final <T> WithValueBuilder<T> addRuleForValues(T value, T... values) {
        WithValueBuilder<T> withMultipleValuesBuilder = new WithValueBuilder(this, value);
        for (T t : values) {
            withMultipleValuesBuilder.andFor(t);
        }
        return withMultipleValuesBuilder;
    }

    public WithCustomRuleBuilder addCustomRule(Supplier<Boolean> predicate) {
        return new WithCustomRuleBuilder(this, predicate);
    }

    public <T> WithRuleBuilder<T> notNull(T value) {
        return addRuleFor(value).notNull();
    }

    public <T> WithRuleBuilder<T> mustBeNull(T value) {
        return addRuleFor(value).mustBeNull();
    }

    public <T> WithRuleBuilder<T> mustEquals(T value, T value2) {
        return addRuleFor(value).must(EQUALS(value2));
    }

    public <T> WithRuleBuilder<T> notEquals(T value, T value2) {
        return addRuleFor(value).must(NOT_EQUAL(value2));
    }

    public <T extends CharSequence> WithRuleBuilder<T> notEmptyText(T value) {
        return addRuleFor(value).must(NOT_EMPTY_TEXT);
    }

    public <T extends List> WithRuleBuilder<T> notEmptyList(T value) {
        return addRuleFor(value).must(NOT_EMPTY_LIST);
    }
}
