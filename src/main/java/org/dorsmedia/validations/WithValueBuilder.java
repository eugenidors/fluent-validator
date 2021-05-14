package org.dorsmedia.validations;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class WithValueBuilder<T> {
    String fieldName;
    List<T> values = new ArrayList<>();
    Validator validator;

    protected WithValueBuilder(Validator validator, T value) {
        this.validator = validator;
        this.andFor(value);
        this.fieldName = "";
    }

    protected WithValueBuilder(Validator validator, T value, String fieldName) {
        this.validator = validator;
        this.andFor(value);
        this.fieldName = fieldName;
    }

    public WithValueBuilder<T> andFor(T value) {
        values.add(value);
        return this;
    }
    public WithValueBuilder<T> withFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public WithRuleBuilder<T> must(Predicate<? super T> predicate) {
        return new WithRuleBuilder(fieldName, values, predicate, validator);
    }

    public WithRuleBuilder<T> notNull() {
        return new WithRuleBuilder(fieldName, values, Constraint.NOT_NULL, validator);
    }

    public WithRuleBuilder<T> mustBeNull() {
        return new WithRuleBuilder(fieldName, values, Constraint.MUST_BE_NULL, validator);
    }


}
