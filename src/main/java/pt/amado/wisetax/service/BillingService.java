package pt.amado.wisetax.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pt.amado.wisetax.exception.GlobalException;
import pt.amado.wisetax.exception.RequestNotEligibleException;
import pt.amado.wisetax.interfaces.ServiceProcessor;
import pt.amado.wisetax.model.entities.BillingAccount;
import pt.amado.wisetax.model.entities.ChargingRequest;
import pt.amado.wisetax.model.enums.Tariff;
import pt.amado.wisetax.repository.BillingAccountRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class BillingService {

    private final BillingAccountRepository billingAccountRepository;
    private final EligibilityService eligibilityService;
    private final ClientDataRecordService clientDataRecordService;
    private final List<ServiceProcessor> serviceProcessors;
    private Map<Tariff, ServiceProcessor> tariffProcessorMap;

    /**
     * There is a brief related to this method in the documentation, the main idea here was creating something reusable and generic enough
     * to guarantee the maintainability. It is a mapping of supported tariffs to their corresponding ServiceProcessor instances.
     * This method is typically called during the application startup, populating the map for tariff-based processing.
     */
    @PostConstruct
    public void initializeProcessorMap() {
        tariffProcessorMap = new HashMap<>();
        for (ServiceProcessor processor : serviceProcessors) {
            tariffProcessorMap.put(processor.getSupportedTariff(), processor);
        }
    }

    /**
     * Processes a Service A charging request, which includes determining the appropriate tariff, executing
     * the request processing, updating counters, and saving transaction records.
     *
     * @param account The BillingAccount associated with the request.
     * @param request The ChargingRequest to be processed.
     * @return The updated BillingAccount after processing.
     * @throws RequestNotEligibleException If the request is not eligible for processing.
     * @throws IllegalArgumentException If no tariff or processor is found for the request.
     */
    public BillingAccount processServiceARequest(BillingAccount account, ChargingRequest request) throws RequestNotEligibleException {

        Tariff tariff = retrieveTariffBasedOnRequest(request, account);
        if(Objects.isNull(tariff))
            throw new IllegalArgumentException("Was not possible to find a tariff based on the given parameters");

        ServiceProcessor processor = tariffProcessorMap.get(tariff);
        if(Objects.isNull(processor))
            throw new IllegalArgumentException("No processor found for the selected tariff.");

        processor.processRequest(account, request);
        updateCounters(account, request, tariff);
        clientDataRecordService.saveTransaction(account, request, tariff);

        return updateBillingAccount(account);
    }

    /**
     * Updates the counters of the BillingAccount based on the processed request and the selected tariff.
     * This method was created based on the requirements provided to increase the counters.
     *
     * @param account The BillingAccount to update.
     * @param request The ChargingRequest that was processed.
     * @param tariff The tariff associated with the request.
     */
    private void updateCounters(BillingAccount account, ChargingRequest request, Tariff tariff) {
        if(Tariff.isServiceA(tariff))
            account.setCounterA(account.getCounterA() + request.getRsu());

        if(Tariff.BETA_1.equals(tariff))
            account.setCounterB(account.getCounterB() + request.getRsu());

        if(request.isRoaming())
            account.setCounterC(account.getCounterC() + request.getRsu());
    }

    private BillingAccount updateBillingAccount(BillingAccount account) {
        return billingAccountRepository.save(account);
    }

    /**
     * Retrieves the appropriate tariff based on the ChargingRequest and associated BillingAccount.
     *
     * @param request The ChargingRequest to be evaluated for tariff selection.
     * @param account The BillingAccount associated with the request.
     * @return The selected tariff based on the request and eligibility criteria.
     * @throws RequestNotEligibleException If the request is not eligible for any tariff.
     */
    private Tariff retrieveTariffBasedOnRequest(ChargingRequest request, BillingAccount account) throws RequestNotEligibleException {
        try {
            return switch (request.getService()) {
                case A -> eligibilityService.checkEligibilityOfServiceA(request, account);
                case B -> eligibilityService.checkEligibilityOfServiceB(request, account);
            };
        } catch (GlobalException e){
            log.error("Something went wrong trying to retrieve the correct tariff based on the eligibility", e);
            throw e;
        }
    }

    /**
     * Retrieves a BillingAccount based on the provided phone number.
     *
     * @param phoneNumber The phone number associated with the BillingAccount to retrieve.
     * @return The BillingAccount with the specified phone number, or null if not found.
     */
    public BillingAccount getBillingAccountByPhoneNumber(String phoneNumber) {
        return billingAccountRepository.findByPhoneNumber(phoneNumber);
    }

    /**
     * Creates a new BillingAccount based on the provided ChargingRequest and saves it.
     *
     * @param request The ChargingRequest containing the phone number for the new account.
     * @return The newly created BillingAccount after saving.
     */
    public BillingAccount saveNewAccount(ChargingRequest request) {
        BillingAccount billingAccount = new BillingAccount();
        billingAccount.setPhoneNumber(request.getPhoneNumber());
        return billingAccountRepository.save(billingAccount);
    }
}
