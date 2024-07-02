package med.voll.api.domain.consultas.validaciones;

import med.voll.api.domain.consultas.DatosCrearConsulta;
import med.voll.api.domain.pacientes.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteActivo implements ValidadorConsulta {

    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(DatosCrearConsulta datos){

        if(datos.idPaciente() == null){
            return;
        }
        var pacienteActivo = pacienteRepository.findActivoById(datos.idPaciente());

        if (!pacienteActivo){
            throw new ValidacionDeIntegridad("No se puede permitir agendar citas en pacientes inactivos en el sistema");
        }

    }
}
