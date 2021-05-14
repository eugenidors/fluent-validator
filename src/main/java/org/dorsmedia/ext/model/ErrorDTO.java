package org.dorsmedia.ext.model;

import lombok.Data;
import org.dorsmedia.utils.StringUtils;


@Data
public class ErrorDTO {
    String campo;
    String mensaje;

    public ErrorDTO(String campo, String mensaje) {
        this.campo = StringUtils.blankOnNullString(campo);
        this.mensaje = StringUtils.blankOnNullString(mensaje);
    }
}
