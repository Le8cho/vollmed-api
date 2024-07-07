package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.medicos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;

@RestController //Indicacion de que se trata de un REST controller
@RequestMapping("/medicos") //Este controlador responde a la ruta asignada localhost:8080/medicos
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    //Autowired = Inyeccion de dependencias. Spring busca una clase que implemente la interfaz
    //No nos encargamos de instanciarlo, Spring lo hace por nosotros
    private MedicoRepository medicoRepository;

    //Indicamos que es un metodo para procesar solicitudes o request del cliente
    @PostMapping
    //@RequestBody es para indicar que datosRegistroMedico es el cuerpo de la solicitud
    //@Valid para indicar que Spring debe validar o verificar ese objeto
    //UriComponentsBuilder para crear el URL del recurso medico
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                                                UriComponentsBuilder uriComponentsBuilder){
        Medico med = medicoRepository.save(new Medico(datosRegistroMedico));
        //una buena practica es devolver el URL del medico creado
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(med.getId()).toUri();
        return ResponseEntity.created(url).body(new DatosRespuestaMedico(med));
    }

    //El Get sirve para que el servidor devuelva datos al cliente
    @GetMapping
    //Page sirve para paginar nuestros resultados
    //indicamos que recibiremos un parametro Pageable que nos ayudara a retornar un Page
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault(size = 2) Pageable paginacion){

        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));

    }
    //@PageableDefault establece valores predeterminados personalizados
    //De todas formas la solicitud del cliente puede sobreescribir este pageableDefault

    @PutMapping
    @Transactional
    //notacion de Spring que abre una 'transaccion' en una base de datos
    // Spring va a encargarse de detectar los cambios relacionados la base de datos (commit)
    // Pero verifica que no haya errores, sube los cambios referidos o de lo contrario retrae la transacccion
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico med = medicoRepository.getReferenceById(datosActualizarMedico.id());
        med.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(med));
    }
    //Una vez terminado el metodo sin errores, Spring cierra la transaccion

    //Delete Logico
     @DeleteMapping("/{id}")
     @Transactional
     //ResponseEntity para enviar codigos HTTP personalizados al cliente
     public ResponseEntity eliminarMedico(@PathVariable Long id){
         Medico med = medicoRepository.getReferenceById(id);
         med.desactivarMedico();
         return ResponseEntity.noContent().build();
     }

    //Get del recurso medico
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornarDatosMedico(@PathVariable Long id){
        Medico med = medicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosRespuestaMedico(med));
    }

}
