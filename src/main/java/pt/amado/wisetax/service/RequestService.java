package pt.amado.wisetax.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.amado.wisetax.dto.ChargingRequestDTO;
import pt.amado.wisetax.model.enitities.BillingAccount;
import pt.amado.wisetax.model.enitities.ChargingRequest;
import pt.amado.wisetax.repository.ChargingRequestRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final ChargingRequestRepository chargingRequestRepository;
    private final BillingService billingService;
    private final ObjectMapper mapper;

    public ChargingRequest processRequest(ChargingRequestDTO chargingRequestDTO){

        ChargingRequest request = chargingRequestRepository.save(mapper.convertValue(chargingRequestDTO, ChargingRequest.class));
        BillingAccount billingAccount = retrieveBillingAccount(request);
        billingService.processServiceARequest(billingAccount, request);

        return null;
    }

    private BillingAccount retrieveBillingAccount(ChargingRequest request) {
        BillingAccount billingAccount = billingService.getBillingAccountByPhoneNumber(request.getPhoneNumber());

        return Objects.isNull(billingAccount) ? billingService.saveNewAccount(request) : billingAccount;
    }

}
