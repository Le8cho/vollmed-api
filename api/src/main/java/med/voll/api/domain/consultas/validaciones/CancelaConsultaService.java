package med.voll.api.domain.consultas.validaciones;

import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.domain.consultas.DatosCancelacionConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CancelaConsultaService {
    @Autowired
    private ValidadorCancelacion validadorCancelacion;
    @Autowired
    private ConsultaRepository consultaRepository;

    public void cancelar(Long id, DatosCancelacionConsulta datos) {
        //Obtenemos id de cita
        if (!consultaRepository.findById(id).isPresent()){
            throw new ValidacionDeIntegridad("No se encontro la consulta a cancelar");
        }

        validadorCancelacion.validar(id);

        var consulta = consultaRepository.getReferenceById(id);

        consulta.cancelar(datos.motivoCancelacion());
    }
}
