package pt.amado.wisetax.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.amado.wisetax.model.entities.mdb.ClientDataRecord;
import pt.amado.wisetax.service.ClientDataRecordService;

import java.util.List;

@Controller
@RequestMapping("api/v1/report")
@RequiredArgsConstructor
public class ReportController {

    private final ClientDataRecordService clientDataRecordService;

    @GetMapping
    public ResponseEntity<List<ClientDataRecord>> findAllRecordsByPhoneNumber(@RequestParam String phoneNumber,
                                                                              @RequestParam(required = false, defaultValue = "DESC") String order) {
        return ResponseEntity.ok(clientDataRecordService.findAllRecordsByPhoneNumber(phoneNumber, order));
    }

}
