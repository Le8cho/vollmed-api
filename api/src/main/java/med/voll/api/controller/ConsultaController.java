package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consultas.*;
import med.voll.api.domain.consultas.validaciones.CancelaConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    AgendaConsultaService service;
    @Autowired
    CancelaConsultaService cancelaService;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaConsulta> agendarConsulta (@RequestBody @Valid DatosCrearConsulta datos)
    {
        System.out.println(datos);
        var response = service.agendar(datos);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity cancelarConsulta(
            @PathVariable Long id,
            @RequestBody @Valid DatosCancelacionConsulta datos
    ){
        System.out.println(id);
        System.out.println(datos);

        cancelaService.cancelar(id,datos);

        return ResponseEntity.noContent().build();
    }

}
