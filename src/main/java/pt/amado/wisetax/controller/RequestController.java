package pt.amado.wisetax.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.amado.wisetax.dto.ChargingRequestDTO;

@Controller
@RequestMapping("api/v1")
public class RequestController {

    @PostMapping
    ResponseEntity<String> processRequest(@RequestBody @Validated ChargingRequestDTO dto) {
        return ResponseEntity.ok("OK");
    }

}
