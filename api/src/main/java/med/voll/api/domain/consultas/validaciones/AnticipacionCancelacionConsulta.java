package med.voll.api.domain.consultas.validaciones;

import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AnticipacionCancelacionConsulta implements ValidadorCancelacion{
    @Autowired
    private ConsultaRepository consultaRepository;
    @Override
    public void validar(Long id) {
        var fechaConsulta = consultaRepository.obtenerFechaConsulta(id);
        var fechaActual = LocalDateTime.now();

        if(Duration.between(fechaActual,fechaConsulta).toHours() < 24){
            System.out.println(fechaConsulta);
            System.out.println(fechaActual);
            System.out.println(Duration.between(fechaConsulta,fechaActual).toHours());
            throw new ValidacionDeIntegridad("Cancelacion de consulta es como minimo 24 horas de anticipacion");
        }
    }
}
