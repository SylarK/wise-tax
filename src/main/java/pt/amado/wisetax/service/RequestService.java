package pt.amado.wisetax.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.amado.wisetax.dto.ChargingRequestDTO;
import pt.amado.wisetax.model.enitities.ChargingRequest;
import pt.amado.wisetax.repository.ChargingRequestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final ChargingRequestRepository chargingRequestRepository;
    private final ObjectMapper mapper;

    public ChargingRequest processRequest(ChargingRequestDTO chargingRequestDTO){
        return chargingRequestRepository.save(mapper.convertValue(chargingRequestDTO, ChargingRequest.class));
    }

}
