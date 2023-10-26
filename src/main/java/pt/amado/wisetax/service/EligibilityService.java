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


    /**
     * Checks the eligibility of a charging request for Service A and determines the corresponding
     * tariff. The method evaluates criteria for Alpha tariffs (ALPHA_1, ALPHA_2, ALPHA_3) and throws a
     * RequestNotEligibleException if none of these tariffs apply.
     *
     * @param request The ChargingRequest to be evaluated for Service A eligibility.
     * @param account The BillingAccount associated with the request.
     * @return The eligible tariff (ALPHA_1, ALPHA_2, ALPHA_3) for Service A.
     * @throws RequestNotEligibleException If the request is not eligible for any of the Service A tariffs.
     */
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

    /**
     * Checks the eligibility of a charging request for Service B and determines the corresponding tariff.
     * The method evaluates criteria for Beta tariffs (BETA_1, BETA_2, BETA_3) and throws a
     * RequestNotEligibleException if none of these tariffs apply.
     *
     * @param request The ChargingRequest to be evaluated for Service B eligibility.
     * @param account The BillingAccount associated with the request.
     * @return The eligible tariff (BETA_1, BETA_2, BETA_3) for Service B.
     * @throws RequestNotEligibleException If the request is not eligible for any of the Service B tariffs.
     */
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

    /**
     * Checks if a charging request is eligible for ALPHA_1 tariff.
     *
     * To be eligible for ALPHA_1 tariff, the request must:
     * - Occur on a weekday (Monday to Friday).
     * - Have a BillingAccount counterA value less than 100.
     *
     * @param request The ChargingRequest to be evaluated.
     * @param account The BillingAccount associated with the request.
     * @return true if eligible; false otherwise.
     */
    private boolean isAlpha1Eligible(ChargingRequest request, BillingAccount account) {
        return DateUtils.isWeekday(request.getCreatedAt()) && account.getCounterA() < 100;
    }

    /**
     * Checks if a charging request is eligible for ALPHA_2 tariff.
     *
     * To be eligible for ALPHA_2 tariff, the request must:
     * - Not involve roaming.
     * - Have a BillingAccount bucket2 value greater than 1.
     *
     * @param request The ChargingRequest to be evaluated.
     * @param account The BillingAccount associated with the request.
     * @return true if eligible; false otherwise.
     */
    private boolean isAlpha2Eligible(ChargingRequest request, BillingAccount account) {
        return !request.isRoaming() && account.getBucket2() > 1;
    }

    /**
     * Checks if a charging request is eligible for ALPHA_3 tariff.
     *
     * To be eligible for ALPHA_3 tariff, the request must:
     * - Involve roaming.
     * - Have a BillingAccount bucket3 value greater than 10.
     *
     * @param request The ChargingRequest to be evaluated.
     * @param account The BillingAccount associated with the request.
     * @return true if eligible; false otherwise.
     */
    private boolean isAlpha3Eligible(ChargingRequest request, BillingAccount account) {
        return request.isRoaming() && account.getBucket3() > 10;
    }

    /**
     * Checks if a charging request is eligible for BETA_1 tariff.
     *
     * To be eligible for BETA_1 tariff, the request must:
     * - Occur on a weekday (Monday to Friday) or during night time hours.
     *
     * @param request The ChargingRequest to be evaluated.
     * @return true if eligible; false otherwise.
     */
    private boolean isBeta1Eligible(ChargingRequest request){
        return isWeekday(request.getCreatedAt()) || isNightTime(request.getCreatedAt());
    }

    /**
     * Checks if a charging request is eligible for BETA_2 tariff.
     *
     * To be eligible for BETA_2 tariff, the request must:
     * - Not involve roaming.
     * - Have a BillingAccount bucket2 value greater than or equal to 10.
     *
     * @param request The ChargingRequest to be evaluated.
     * @param account The BillingAccount associated with the request.
     * @return true if eligible; false otherwise.
     */
    private boolean isBeta2Eligible(ChargingRequest request, BillingAccount account){
        return !request.isRoaming() && account.getBucket2() >= 10;
    }

    /**
     * Checks if a charging request is eligible for BETA_3 tariff.
     *
     * To be eligible for BETA_3 tariff, the request must:
     * - Involve roaming.
     * - Have a BillingAccount bucket2 value greater than or equal to 10.
     *
     * @param request The ChargingRequest to be evaluated.
     * @param account The BillingAccount associated with the request.
     * @return true if eligible; false otherwise.
     */
    private boolean isBeta3Eligible(ChargingRequest request, BillingAccount account){
        return request.isRoaming() && account.getBucket2() >= 10;
    }
}
