package med.voll.api.domain.consultas.validaciones;

import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.domain.consultas.DatosCrearConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteSinConsulta implements ValidadorConsulta {

    @Autowired
    private ConsultaRepository repository;

    //No permitir mas de una consulta en un mismo dia
    public void validar(DatosCrearConsulta datos){

        if(datos.idPaciente() == null){
            return;
        }
            var horaInicio = datos.fecha().withHour(7);
            var horaFin = datos.fecha().withHour(18); //no 19 porque se asume que 18 es la ultima cita posible

            //no me convence la solucion pero puede que yo me equivoque y descubra algo nuevo
            var pacienteConConsulta =  repository.existsByPacienteIdAndFechaBetween(datos.idPaciente(), horaInicio,horaFin);

            if(pacienteConConsulta){
                throw new ValidacionDeIntegridad("No se puede agendar mas de una cita al dia por paciente");
            }

    }
}
