package pt.amado.wisetax.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.amado.wisetax.model.enitities.BillingAccount;
import pt.amado.wisetax.model.enitities.ChargingRequest;
import pt.amado.wisetax.repository.BillingAccountRepository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final BillingAccountRepository billingAccountRepository;

    public BillingAccount processServiceARequest(BillingAccount account, ServiceARequest request) {
        try {
            switch (account.getTarifarioServicoA()) {
                case ALPHA_1:
                    processAlpha1Request(account, request);
                    break;
                case ALPHA_2:
                    processAlpha2Request(account, request);
                    break;
                case ALPHA_3:
                    processAlpha3Request(account, request);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    private void processAlpha1Request(BillingAccount account, ServiceARequest request) {
        if (isWeekday(request.getRequestInstant()) && account.getCounterA() < 100) {
            double costPerMinute = 1.0;
            if (request.isRoaming()) {
                costPerMinute = 2.0;
                if (account.getCounterA() > 10 && account.getBucketC() > 50) {
                    costPerMinute -= 0.25;
                }
            }
            double callCost = costPerMinute * request.getDuration();
            if (request.isRoaming() && account.getBucketB() >= 5) {
                account.setBucketB(account.getBucketB() - 5);
            }
            if (request.isRoaming()) {
                account.setBucketC(account.getBucketC() - (long) (callCost * 100));
            } else {
                account.setBucketA(account.getBucketA() - (long) (callCost * 100));
            }
        }
    }

    private void processAlpha2Request(BillingAccount account, ServiceARequest request) {
        if (account.getBucket2() > 10) {
            double costPerMinute = 0.5;
            if (isNighttime(request.getRequestInstant())) {
                costPerMinute = 0.25;
            }
            if (account.getBucket2() >= 15) {
                costPerMinute -= 0.05;
            }
            double callCost = costPerMinute * request.getDuration();
            account.setBucketB(account.getBucketB() - (long) (callCost * 100));
        }
    }

    private void processAlpha3Request(BillingAccount account, ServiceARequest request) {
        if (request.isRoaming() && account.getBucketC() > 10) {
            double costPerMinute = 1.0;
            if (isWeekend(request.getRequestInstant())) {
                costPerMinute = 0.25;
            }
            if (account.getCounterC() > 10) {
                costPerMinute -= 0.2;
            }
            if (account.getBucketC() >= 15) {
                costPerMinute -= 0.05;
            }
            double callCost = costPerMinute * request.getDuration();
            account.setBucketC(account.getBucketC() - (long) (callCost * 100));
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
