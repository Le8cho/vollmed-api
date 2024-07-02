package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consultas.AgendaConsultaService;
import med.voll.api.domain.consultas.DatosCrearConsulta;
import med.voll.api.domain.consultas.DatosRespuestaConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    AgendaConsultaService service;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaConsulta> agendarConsulta (@RequestBody @Valid DatosCrearConsulta datos)
    {
        System.out.println(datos);
        var response = service.agendar(datos);

        return ResponseEntity.ok(response);
    }

}
