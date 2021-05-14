package org.dorsmedia.validations.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorValidation {
    String valor;
    String mensaje;
}
