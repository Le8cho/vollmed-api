package med.voll.api.domain.consultas;

import jakarta.transaction.Transactional;
import med.voll.api.domain.consultas.validaciones.ValidadorCancelacion;
import med.voll.api.domain.consultas.validaciones.ValidadorConsulta;
import med.voll.api.domain.medicos.Medico;
import med.voll.api.domain.medicos.MedicoRepository;
import med.voll.api.domain.pacientes.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorConsulta> validadorConsultaList;


    @Transactional
    public DatosRespuestaConsulta agendar(DatosCrearConsulta datos){

        if(!pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("El Id del paciente no fue encontrado");
        }

        if(datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridad("El id del medico no fue encontrado");
        }

        //Validaciones adicionales
        validadorConsultaList.forEach(
                v -> v.validar(datos)
        );

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();

        var medico = encontrarMedico(datos);

        //Puede que no se encuentre un medico disponible
        if(medico==null){
            throw new ValidacionDeIntegridad("No se encontraron medicos para este horario y especialidad");
        }

        var consulta = new Consulta(medico, paciente,datos.fecha());

        consultaRepository.save(consulta);

        return new DatosRespuestaConsulta(consulta);

    }

    private Medico encontrarMedico(DatosCrearConsulta datos) {

        //Si indico medico, devolver el medico
        if(datos.idMedico() != null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        //Si no indico medico, verificar la especialidad ...
        if(datos.especialidad() == null){
            throw new ValidacionDeIntegridad("No se ha especificado especialidad");
        }

        //Asignarle un medico de especialidad aleatorio que pueda en esa fecha
        return medicoRepository.seleccionarMedicoAleatorio(datos.especialidad(),datos.fecha());

    }

}
