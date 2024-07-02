package med.voll.api.domain.consultas.validaciones;

import med.voll.api.domain.consultas.DatosCrearConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioDeAnticipacion implements ValidadorConsulta {
    public void validar(DatosCrearConsulta datos){
        var fechaActual = LocalDateTime.now();
        var fechaConsulta = datos.fecha();
        var esSinAnticipacion = Duration.between(fechaActual,fechaConsulta).toMinutes() < 30;

        if(esSinAnticipacion){
            throw new ValidacionDeIntegridad("Las citas deben ser con más de 30 minutos de anticipacion");
        }
    }
}
