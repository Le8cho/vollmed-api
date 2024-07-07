package med.voll.api.domain.consultas;

import jakarta.validation.constraints.NotNull;

public record DatosCancelacionConsulta(
        @NotNull
        MotivoCancelamiento motivoCancelacion
) {
}
