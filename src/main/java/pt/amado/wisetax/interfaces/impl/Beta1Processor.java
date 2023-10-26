package pt.amado.wisetax.interfaces.impl;

import org.springframework.stereotype.Component;
import pt.amado.wisetax.interfaces.ServiceProcessor;
import pt.amado.wisetax.model.entities.BillingAccount;
import pt.amado.wisetax.model.entities.ChargingRequest;
import pt.amado.wisetax.model.enums.Tariff;

@Component
public class Beta1Processor implements ServiceProcessor {

    private static final int MAX_CALLS = 10;
    private static final double COST_LOCAL = 0.1;
    private static final double COST_ROAMING = 0.2;
    private static final double BONUS = 0.025;
    private static final double DISCOUNT_MIN_BALANCE = 50;
    private static final double DISCOUNT_AMOUNT = 0.01;
    private static final double MIN_BALANCE_COUNTER_B = 5;

    @Override
    public void processRequest(BillingAccount account, ChargingRequest request) {
        double cost = (request.isRoaming()) ? COST_ROAMING : COST_LOCAL;

        if (account.getCounterA() > MAX_CALLS) {
            cost -= BONUS;
        }

        if ((double) account.getBucket3() / 100 >= DISCOUNT_MIN_BALANCE) {
            cost -= DISCOUNT_AMOUNT;
        }

        double callCost = cost * request.getRsu();
        if (request.isRoaming() && (double) account.getBucket2() / 100 >= MIN_BALANCE_COUNTER_B) {
            account.setBucket2(account.getBucket2() + (long) (callCost * 100));
        } else if (request.isRoaming()) {
            account.setBucket3(account.getBucket3() + (long) (callCost * 100));
        } else {
            account.setBucket1(account.getBucket1() + (long) (callCost * 100));
        }
    }

    @Override
    public Tariff getSupportedTariff() {
        return Tariff.BETA_1;
    }
}
