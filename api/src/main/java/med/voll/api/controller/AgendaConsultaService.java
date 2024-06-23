package med.voll.api.controller;

import jakarta.transaction.Transactional;
import med.voll.api.domain.consultas.Consulta;
import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.domain.consultas.DatosCrearConsulta;
import med.voll.api.domain.medicos.Medico;
import med.voll.api.domain.medicos.MedicoRepository;
import med.voll.api.domain.pacientes.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaConsultaService {

    @Autowired
    ConsultaRepository consultaRepository;

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    PacienteRepository pacienteRepository;


    @Transactional
    public void agendar(DatosCrearConsulta datos){

        if(!pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("El Id del paciente no fue encontrado");
        }

        if(datos.idMedico() != null && medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridad("El id del medico no fue encontrado");
        }

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();

        var medico = encontrarMedico(datos);

        consultaRepository.save(new Consulta(null, medico, paciente,datos.fecha()));

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
