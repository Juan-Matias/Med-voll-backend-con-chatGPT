package med.voll.api.domain.paciente.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import med.voll.api.domain.direccion.DatosDireccion;


public record DatosRegistroPaciente(
        @NotBlank
        String nombre,
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Pattern(
                regexp = "^\\+569\\d{8}$",
                message = "El formato es :+569XXXXXXXX")
        String telefono,


        @Pattern(
                regexp = "\\d{2}\\.\\d{3}\\.\\d{3}\\-[\\dkK]",
                        message = "El formato es: +123.456.789-1")
        @NotBlank
        String documento,

        @NotNull @Valid DatosDireccion direccion) {
}
