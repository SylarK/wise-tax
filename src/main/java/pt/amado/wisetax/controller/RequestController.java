package pt.amado.wisetax.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.amado.wisetax.dto.ChargingRequestDTO;
import pt.amado.wisetax.model.enitities.ChargingRequest;
import pt.amado.wisetax.service.RequestService;

import java.util.List;

@Controller
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    ResponseEntity<ChargingRequest> processRequest(@RequestBody @Validated ChargingRequestDTO dto) {
        return ResponseEntity.ok(requestService.saving(dto));
    }

    @GetMapping
    ResponseEntity<List<ChargingRequest>> listAll() {
        return ResponseEntity.ok(requestService.listAll());
    }

}
