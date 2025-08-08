package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.dto.DatosAgendarConsulta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HorarioDeFuncionamientoClinicaTest {

    private final HorarioDeFuncionamientoClinica validador = new HorarioDeFuncionamientoClinica();

    @Test
    @DisplayName("❌ No debería permitir consultas en domingo")
    void noDebePermitirConsultasEnDomingo() {
        var fechaDomingo = LocalDateTime.of(2025, Month.AUGUST, 10, 10, 0); // Domingo
        var datosMock = mock(DatosAgendarConsulta.class);
        when(datosMock.fecha()).thenReturn(fechaDomingo);

        assertThrows(ValidationException.class, () -> validador.validar(datosMock));
    }

    @Test
    @DisplayName("No debe permitir consultas después de cierre")
    void noDebePermitirConsultasDespuesDeCierre() {
        var validador = new HorarioDeFuncionamientoClinica();

        // Crear datos con hora después de 19 (ej: 20:00) en un día que no sea domingo
        var datos = new DatosAgendarConsulta(
                1L,
                1L,
                // fecha con hora 20:00
                LocalDateTime.of(2025, 8, 8, 20, 0),
                null
        );

        assertThrows(ValidationException.class, () -> {
            validador.validar(datos);
        });
    }


    @Test
    @DisplayName("✅ Debería permitir consultas dentro del horario y días válidos")
    void debePermitirConsultasEnHorarioValido() {
        var fechaValida = LocalDateTime.of(2025, Month.AUGUST, 9, 10, 0); // Sábado
        var datosMock = mock(DatosAgendarConsulta.class);
        when(datosMock.fecha()).thenReturn(fechaValida);

        assertDoesNotThrow(() -> validador.validar(datosMock));
    }
}
