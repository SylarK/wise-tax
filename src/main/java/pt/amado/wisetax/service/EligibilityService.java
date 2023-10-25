package pt.amado.wisetax.service;


import org.springframework.stereotype.Service;
import pt.amado.wisetax.exception.RequestNotEligibleException;
import pt.amado.wisetax.model.entities.BillingAccount;
import pt.amado.wisetax.model.entities.ChargingRequest;
import pt.amado.wisetax.model.enums.Tariff;
import pt.amado.wisetax.utils.DateUtils;

import static pt.amado.wisetax.utils.DateUtils.isNightTime;
import static pt.amado.wisetax.utils.DateUtils.isWeekday;

@Service
public class EligibilityService {


    public Tariff checkEligibilityOfServiceA(ChargingRequest request, BillingAccount account) throws RequestNotEligibleException {
        if (isAlpha1Eligible(request, account)) {
            return Tariff.ALPHA_1;
        } else if (isAlpha2Eligible(request, account)) {
            return Tariff.ALPHA_2;
        } else if (isAlpha3Eligible(request, account)) {
            return Tariff.ALPHA_3;
        }
        throw new RequestNotEligibleException("Request is not eligible for Service A tariff.");
    }

    public Tariff checkEligibilityOfServiceB(ChargingRequest request, BillingAccount account) throws RequestNotEligibleException{
        if (isBeta1Eligible(request)) {
            return Tariff.BETA_1;
        } else if (isBeta2Eligible(request, account)) {
            return Tariff.BETA_2;
        } else if (isBeta3Eligible(request, account)) {
            return Tariff.BETA_3;
        }

        throw new RequestNotEligibleException("Request is not eligible for Service B tariff.");
    }

    private boolean isAlpha1Eligible(ChargingRequest request, BillingAccount account) {
        return DateUtils.isWeekday(request.getCreatedAt()) && account.getCounterA() < 100;
    }

    private boolean isAlpha2Eligible(ChargingRequest request, BillingAccount account) {
        return !request.isRoaming() && account.getBucket2() > 1;
    }

    private boolean isAlpha3Eligible(ChargingRequest request, BillingAccount account) {
        return request.isRoaming() && account.getBucket3() > 10;
    }

    private boolean isBeta1Eligible(ChargingRequest request){
        return isWeekday(request.getCreatedAt()) || isNightTime(request.getCreatedAt());
    }

    private boolean isBeta2Eligible(ChargingRequest request, BillingAccount account){
        return !request.isRoaming() && account.getBucket2() >= 10;
    }

    private boolean isBeta3Eligible(ChargingRequest request, BillingAccount account){
        return request.isRoaming() && account.getBucket2() >= 10;
    }
}
