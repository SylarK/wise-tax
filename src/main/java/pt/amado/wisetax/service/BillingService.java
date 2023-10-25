package pt.amado.wisetax.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.amado.wisetax.interfaces.ServiceAProcessor;
import pt.amado.wisetax.model.enitities.BillingAccount;
import pt.amado.wisetax.model.enitities.ChargingRequest;
import pt.amado.wisetax.model.enums.Tariff;
import pt.amado.wisetax.repository.BillingAccountRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
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
        try {
            switch (request.getRequestedService()) {
                case A -> eligibilityService.checkEligibilityOfServiceA(request, account);
                case B -> eligibilityService.checkElegibilityOfServiceB(request, account);
                default -> new IllegalArgumentException("The provided service is not available");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
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
