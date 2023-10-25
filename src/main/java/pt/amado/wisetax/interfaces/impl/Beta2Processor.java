package pt.amado.wisetax.interfaces.impl;

import org.springframework.stereotype.Component;
import pt.amado.wisetax.exception.RequestNotEligibleException;
import pt.amado.wisetax.interfaces.ServiceProcessor;
import pt.amado.wisetax.model.enitities.BillingAccount;
import pt.amado.wisetax.model.enitities.ChargingRequest;
import pt.amado.wisetax.model.enums.Tariff;

import static pt.amado.wisetax.utils.DateUtils.isNightTime;

@Component
public class Beta2Processor implements ServiceProcessor {

    private static final int MIN_BALANCE_BUCKET_B = 10;
    private static final double COST_LOCAL = 0.05;
    private static final double COST_NIGHT_LOCAL = 0.025;
    private static final double DISCOUNT_MIN_BALANCE_BUCKET_B = 15;
    private static final double DISCOUNT_AMOUNT = 0.02;
    private static final double DISCOUNT_MIN_BALANCE_COUNTER_B = 10;
    private static final double DISCOUNT_AMOUNT_COUNTER_B = 0.005;

    @Override
    public void processRequest(BillingAccount account, ChargingRequest request) throws RequestNotEligibleException {
        if (!request.isRoaming() && account.getBucket2() >= MIN_BALANCE_BUCKET_B) {
            double cost = (isNightTime(request.getCreatedAt())) ? COST_NIGHT_LOCAL : COST_LOCAL;

            if (account.getBucket2() > DISCOUNT_MIN_BALANCE_BUCKET_B) {
                cost -= DISCOUNT_AMOUNT;
            }

            if (account.getCounterB() > DISCOUNT_MIN_BALANCE_COUNTER_B) {
                cost -= DISCOUNT_AMOUNT_COUNTER_B;
            }

            account.setBucket2(account.getBucket2() + (long) (cost * 100));
        } else {
            throw new RequestNotEligibleException("Request is not eligible for Beta2 tariff.");
        }
    }

    @Override
    public Tariff getSupportedTariff() {
        return Tariff.BETA_2;
    }
}
