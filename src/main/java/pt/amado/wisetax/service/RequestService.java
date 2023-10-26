package pt.amado.wisetax.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.amado.wisetax.dto.ChargingRequestDTO;
import pt.amado.wisetax.exception.RequestNotEligibleException;
import pt.amado.wisetax.model.ChargingResult;
import pt.amado.wisetax.model.entities.BillingAccount;
import pt.amado.wisetax.model.ChargingReply;
import pt.amado.wisetax.model.entities.ChargingRequest;
import pt.amado.wisetax.model.enums.Status;
import pt.amado.wisetax.repository.ChargingRequestRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final ChargingRequestRepository chargingRequestRepository;
    private final BillingService billingService;
    private final ObjectMapper mapper;

    public ChargingReply processRequest(ChargingRequestDTO chargingRequestDTO) throws RequestNotEligibleException {
        ChargingRequest request = chargingRequestRepository.save(mapper.convertValue(chargingRequestDTO, ChargingRequest.class));
        BillingAccount billingAccount = retrieveBillingAccount(request);
        billingService.processServiceARequest(billingAccount, request);
        return generateChargingReply(request);
    }

    private ChargingReply generateChargingReply(ChargingRequest chargingRequest) {
        ChargingResult result = new ChargingResult(Status.OK, Status.OK.getDescription());
        return new ChargingReply(result, chargingRequest.getRsu());
    }

    private BillingAccount retrieveBillingAccount(ChargingRequest request) {
        BillingAccount billingAccount = billingService.getBillingAccountByPhoneNumber(request.getPhoneNumber());
        return Objects.isNull(billingAccount) ? billingService.saveNewAccount(request) : billingAccount;
    }

}
