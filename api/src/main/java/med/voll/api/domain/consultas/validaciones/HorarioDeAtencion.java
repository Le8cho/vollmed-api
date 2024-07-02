package med.voll.api.domain.consultas.validaciones;

import med.voll.api.domain.consultas.DatosCrearConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class HorarioDeAtencion implements ValidadorConsulta {

    public void validar(DatosCrearConsulta datos){

        //Primero verificamos el dia de la consulta
        var esDomingo = DayOfWeek.SUNDAY.equals(datos.fecha().getDayOfWeek());
        //La consulta es antes o despues del funcionamiento de la clinica
        var esAntesApertura = datos.fecha().getHour() < 7;
        var esDespuesCierre = datos.fecha().getHour() > 19;
        if(esDomingo || esAntesApertura || esDespuesCierre){
            throw new ValidacionDeIntegridad(
                    "Horario de Atencion de la clinica es de lunes a sabado de 7:00 a 19:00"
            );
        }
    }
}
