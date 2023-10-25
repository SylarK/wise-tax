package pt.amado.wisetax.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pt.amado.wisetax.interfaces.ServiceAProcessor;
import pt.amado.wisetax.model.enitities.BillingAccount;
import pt.amado.wisetax.model.enitities.ChargingRequest;
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
    private final List<ServiceAProcessor> serviceAProcessors;
    private Map<Tariff, ServiceAProcessor> tariffProcessorMap;

    @PostConstruct
    public void initializeProcessorMap() {
        tariffProcessorMap = new HashMap<>();
        for (ServiceAProcessor processor : serviceAProcessors) {
            tariffProcessorMap.put(processor.getSupportedTariff(), processor);
        }
    }

    public BillingAccount processServiceARequest(BillingAccount account, ChargingRequest request) {

        Tariff tariff = retrieveTariffBasedOnRequest(request, account);
        if(Objects.isNull(tariff))
            throw new IllegalArgumentException("Was not possible to find a tariff based on the given parameters");

        ServiceAProcessor processor = tariffProcessorMap.get(tariff);
        if(Objects.isNull(processor))
            throw new IllegalArgumentException("No processor found for the selected tariff.");

        processor.processRequest(account, request);
        updateCounters(account, request, tariff);

        return updateBillingAccount(account);
    }

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

    private Tariff retrieveTariffBasedOnRequest(ChargingRequest request, BillingAccount account) {
        try {
            return switch (request.getService()) {
                case A -> eligibilityService.checkEligibilityOfServiceA(request, account);
                case B -> eligibilityService.checkElegibilityOfServiceB(request, account);
            };
        }catch (Exception e){
            log.error("Something went wrong trying to retrieve the correct tariff based on the eligibility", e);
            throw new RuntimeException();
        }
    }

    public BillingAccount getBillingAccountByPhoneNumber(String phoneNumber) {
        return billingAccountRepository.findByPhoneNumber(phoneNumber);
    }

    public BillingAccount saveNewAccount(ChargingRequest request) {
        BillingAccount billingAccount = new BillingAccount();
        billingAccount.setPhoneNumber(request.getPhoneNumber());
        return billingAccountRepository.save(billingAccount);
    }
}
