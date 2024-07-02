package med.voll.api.domain.consultas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta,Long> {

    //Nota importante: Los query methods tambien funcionan con el join column. Ejemplo: paciente_id
    Boolean existsByPacienteIdAndFechaBetween(Long idPaciente, LocalDateTime primerHorario, LocalDateTime segundoHorario);

    Boolean existsByMedicoIdAndFecha(Long medicoId, LocalDateTime fecha);
}
