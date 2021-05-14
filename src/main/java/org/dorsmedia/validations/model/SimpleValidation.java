package org.dorsmedia.validations.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.dorsmedia.utils.StringUtils;

import java.util.function.Predicate;

@AllArgsConstructor
@Data
public class SimpleValidation<T>
        implements Validation
{
    T value;
    Predicate<T> constraint;
    String errorMessage;

    public SimpleValidation(T value, Predicate<T> constraint) {
        this.constraint = constraint;
        this.value = value;
        this.errorMessage = "Error de validación: " + getValueName();
    }


    @Override
    public boolean isValid(){
        return constraint.test(value);
    } //is valid if constraint is valid.

    @Override
    public String getValueName() {
        return StringUtils.blankOnNullString(StringUtils.parseNullSafe(value) ) ;
    }

}
