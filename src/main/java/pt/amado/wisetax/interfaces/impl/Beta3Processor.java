package pt.amado.wisetax.interfaces.impl;

import org.springframework.stereotype.Component;
import pt.amado.wisetax.exception.RequestNotEligibleException;
import pt.amado.wisetax.interfaces.ServiceProcessor;
import pt.amado.wisetax.model.enitities.BillingAccount;
import pt.amado.wisetax.model.enitities.ChargingRequest;
import pt.amado.wisetax.model.enums.Tariff;

import static pt.amado.wisetax.utils.DateUtils.isWeekend;

@Component
public class Beta3Processor implements ServiceProcessor {

    private static final double COST_LOCAL = 0.1;
    private static final double COST_WEEKEND_LOCAL = 0.025;
    private static final double DISCOUNT_MIN_BALANCE_BUCKET_C = 10;
    private static final double DISCOUNT_AMOUNT_BUCKET_C = 0.02;
    private static final double DISCOUNT_MIN_BALANCE_COUNTER_C = 10;
    private static final double DISCOUNT_AMOUNT_COUNTER_C = 0.02;
    private static final double DISCOUNT_MIN_BALANCE_BUCKET_C_WEEKEND = 15;
    private static final double DISCOUNT_AMOUNT_BUCKET_C_WEEKEND = 0.005;

    @Override
    public void processRequest(BillingAccount account, ChargingRequest request) throws RequestNotEligibleException {
        double cost = (isWeekend(request.getCreatedAt())) ? COST_WEEKEND_LOCAL : COST_LOCAL;

        if (account.getBucket3() > DISCOUNT_MIN_BALANCE_BUCKET_C) {
            cost -= DISCOUNT_AMOUNT_BUCKET_C;
        }

        if (account.getCounterC() > DISCOUNT_MIN_BALANCE_COUNTER_C) {
            cost -= DISCOUNT_AMOUNT_COUNTER_C;
        }

        if (isWeekend(request.getCreatedAt()) && account.getBucket3() > DISCOUNT_MIN_BALANCE_BUCKET_C_WEEKEND) {
            cost -= DISCOUNT_AMOUNT_BUCKET_C_WEEKEND;
        }

        account.setBucket3(account.getBucket3() + (long) (cost * 100));
    }

    @Override
    public Tariff getSupportedTariff() {
        return Tariff.BETA_3;
    }

}
