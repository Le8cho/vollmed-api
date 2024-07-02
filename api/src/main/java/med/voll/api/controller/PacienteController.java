package med.voll.api.controller;

import jakarta.transaction.Transactional;

import jakarta.validation.Valid;
import med.voll.api.domain.pacientes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> registrarPaciente(@RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente,
                                                                    UriComponentsBuilder uriComponentsBuilder){
        Paciente paciente = pacienteRepository.save(new Paciente(datosRegistroPaciente));
        URI urlPaciente = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(urlPaciente).body(new DatosRespuestaPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoPaciente>> listadoPacientes(@PageableDefault(page=0, size=10, sort= {"nombre"}) Pageable paginacion){

        var page = pacienteRepository.findByActivoTrue(paginacion).map(DatosListadoPaciente::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> actualizarPaciente(
            @RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente
    ){
        Paciente pac = pacienteRepository.getReferenceById(datosActualizarPaciente.id());
        pac.actualizarDatos(datosActualizarPaciente);
        return ResponseEntity.ok(new DatosRespuestaPaciente(pac));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarPaciente(@PathVariable Long id){
        Paciente pac = pacienteRepository.getReferenceById(id);
        pac.eliminarPaciente();
        return ResponseEntity.noContent().build();
    }

    //Get del recurso paciente
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaPaciente> obtenerPaciente(@PathVariable Long id){
        Paciente pac = pacienteRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosRespuestaPaciente(pac));
    }

}
