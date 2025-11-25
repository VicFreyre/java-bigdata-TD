package br.com.sistema.matriculas.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.mappings.MappingsEndpoint;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actuator")
public class ActuatorMappingViewController {

    private final MappingsEndpoint mappingsEndpoint;

    @Autowired
    public ActuatorMappingViewController(MappingsEndpoint mappingsEndpoint) {
        this.mappingsEndpoint = mappingsEndpoint;
    }

    @GetMapping(value = "/mappings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> mappingsJson() {
        return ResponseEntity.ok(this.mappingsEndpoint.mappings());
    }
}
