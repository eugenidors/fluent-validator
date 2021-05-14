package org.dorsmedia.validations.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.function.Supplier;

@Data
@AllArgsConstructor
public class CustomValidation implements Validation {
    Supplier<Boolean> constraint;
    String errorMessage;

    public CustomValidation(Supplier<Boolean> constraint) {
        this.constraint = constraint;
        this.errorMessage = "Error de validación.";
    }


    public boolean isValid(){
        return constraint.get();
    }

    @Override
    public String getValueName() {
        return " - ";
    }

}
