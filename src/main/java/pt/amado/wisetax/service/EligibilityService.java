package pt.amado.wisetax.service;


import org.springframework.stereotype.Service;
import pt.amado.wisetax.model.enitities.BillingAccount;
import pt.amado.wisetax.model.enitities.ChargingRequest;
import pt.amado.wisetax.model.enums.RequestedService;
import pt.amado.wisetax.model.enums.Tariff;
import pt.amado.wisetax.utils.DateUtils;

@Service
public class EligibilityService {


    public Tariff checkEligibilityOfServiceA(ChargingRequest request, BillingAccount account) {
        if (request.getService() == RequestedService.A) {
            if (isAlpha1Eligible(request, account)) {
                return Tariff.ALPHA_1;
            } else if (isAlpha2Eligible(request, account)) {
                return Tariff.ALPHA_2;
            } else if (isAlpha3Eligible(request, account)) {
                return Tariff.ALPHA_3;
            }
        }
        return null;
    }

    public Tariff checkElegibilityOfServiceB(ChargingRequest request, BillingAccount account) {
        return null;
    }

    private boolean isAlpha1Eligible(ChargingRequest request, BillingAccount account) {
        if (DateUtils.isWeekday(request.getCreatedAt()) && account.getCounterA() < 100) {
            return true;
        }
        return false;
    }

    private boolean isAlpha2Eligible(ChargingRequest request, BillingAccount account) {
        if (!request.isRoaming() && account.getBucket2() > 1) {
            return true;
        }
        return false;
    }

    private boolean isAlpha3Eligible(ChargingRequest request, BillingAccount account) {
        if (request.isRoaming() && account.getBucket3() > 10) {
            return true;
        }
        return false;
    }
}
