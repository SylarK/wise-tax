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

    /**
     * Processes a charging request using the provided ChargingRequestDTO and performs the necessary
     * actions, including saving the request, retrieving or creating a billing account, and processing
     * a service A request. A ChargingReply is generated as a result and it will be back to the customer.
     *
     * @param chargingRequestDTO The ChargingRequestDTO containing the request details.
     * @return A ChargingReply with the processing result.
     * @throws RequestNotEligibleException If the request is not eligible for processing.
     */
    public ChargingReply processRequest(ChargingRequestDTO chargingRequestDTO) throws RequestNotEligibleException {
        ChargingRequest request = chargingRequestRepository.save(mapper.convertValue(chargingRequestDTO, ChargingRequest.class));
        BillingAccount billingAccount = retrieveBillingAccount(request);
        billingService.processServiceARequest(billingAccount, request);
        return generateChargingReply(request);
    }

    /**
     * Generates a ChargingReply based on the provided ChargingRequest, resulting in a ChargingResult
     * with a status of OK and a description of "OK."
     *
     * @param chargingRequest The ChargingRequest for which a ChargingReply is generated.
     * @return A ChargingReply containing the result and RSU (Resource Subscription Unit).
     */
    private ChargingReply generateChargingReply(ChargingRequest chargingRequest) {
        ChargingResult result = new ChargingResult(Status.OK, Status.OK.getDescription());
        return new ChargingReply(result, chargingRequest.getRsu());
    }

    /**
     * Retrieves the BillingAccount associated with the provided ChargingRequest's phone number. If a
     * matching BillingAccount is not found, a new account is created and saved.
     *
     * @param request The ChargingRequest for which the BillingAccount is retrieved or created.
     * @return The retrieved or newly created BillingAccount.
     */
    private BillingAccount retrieveBillingAccount(ChargingRequest request) {
        BillingAccount billingAccount = billingService.getBillingAccountByPhoneNumber(request.getPhoneNumber());
        return Objects.isNull(billingAccount) ? billingService.saveNewAccount(request) : billingAccount;
    }

}
