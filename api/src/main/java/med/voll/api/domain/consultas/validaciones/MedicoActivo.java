package med.voll.api.domain.consultas.validaciones;

import med.voll.api.domain.consultas.DatosCrearConsulta;
import med.voll.api.domain.medicos.MedicoRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoActivo implements ValidadorConsulta {

    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DatosCrearConsulta datos){

        if(datos.idMedico() == null){
            return;
        }
        var medicoActivo = medicoRepository.findActivoById(datos.idMedico());

        if (!medicoActivo){
            throw new ValidacionDeIntegridad("No se puede permitir agendar citas en medicos inactivos en el sistema");
        }

    }


}