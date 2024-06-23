package med.voll.api.domain.consultas;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medicos.Especialidad;

import java.time.LocalDateTime;

public record DatosCrearConsulta(
        @NotNull
        Long idPaciente,

        Long idMedico,

        @NotNull
        @Future
        LocalDateTime fecha,

        Especialidad especialidad
) {
}
