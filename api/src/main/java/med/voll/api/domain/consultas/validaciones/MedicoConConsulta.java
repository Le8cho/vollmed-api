package med.voll.api.domain.consultas.validaciones;

import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.domain.consultas.DatosCrearConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorConsulta {

    //No permitir programar una cita con un médico que ya tiene otra cita programada en la misma fecha/hora;}
    @Autowired
    private ConsultaRepository repository;

    public void validar(DatosCrearConsulta datos){
        if(datos.idMedico() == null){
            return;
        }

        //Buscamos si existe el medico y la fecha de consulta ya está establecida
        var medicoConConsulta = repository.existsByMedicoIdAndFecha(datos.idMedico(), datos.fecha());

        if(medicoConConsulta){
            throw new ValidacionDeIntegridad("Este medico ya tiene una consulta a esta hora");
        }

    }

}
