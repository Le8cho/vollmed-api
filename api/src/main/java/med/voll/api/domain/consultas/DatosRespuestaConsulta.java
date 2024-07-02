package med.voll.api.domain.consultas;

import java.time.LocalDateTime;

public record DatosRespuestaConsulta(Long id, Long idPaciente, Long idMedico, LocalDateTime fecha) {
    public DatosRespuestaConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getPaciente().getId(), consulta.getMedico().getId(),consulta.getFecha());
    }
}
