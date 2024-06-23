package med.voll.api.domain.medicos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

//Interfaz Repository, solo necesitas pasarle la clase requerida y el tipo de dato del ID
public interface MedicoRepository extends JpaRepository<Medico,Long> {
    Page<Medico> findByActivoTrue(Pageable paginacion);

    //Primer cambio en Intellij
    @Query("""
            SELECT m from Medico m
            WHERE m.especialidad= :especialidad and
                m.activo = 1 and
                m.id not in(
                    SELECT c.medico.id FROM Consulta c
                    WHERE c.fecha = :fecha
                )
            ORDER BY rand()
            LIMIT 1   
            """)
    Medico seleccionarMedicoAleatorio(Especialidad especialidad, LocalDateTime fecha);
}
