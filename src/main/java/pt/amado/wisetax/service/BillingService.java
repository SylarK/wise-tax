package pt.amado.wisetax.service;

import pt.amado.wisetax.model.enitities.BillingAccount;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class BillingService {

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

    private boolean isWeekday(Instant requestInstant) {
        // Implement logic to check if it's a weekday
        ZoneId zoneId = ZoneId.of("YourTimeZoneHere"); // Replace with your desired time zone
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(requestInstant, zoneId);
        int dayOfWeek = zonedDateTime.getDayOfWeek().getValue(); // Monday: 1, Tuesday: 2, ..., Sunday: 7
        return dayOfWeek >= 1 && dayOfWeek <= 5; // Weekdays are Monday to Friday
    }

    private boolean isNighttime(Instant requestInstant) {
        // Implement logic to check if it's nighttime
        ZoneId zoneId = ZoneId.of("YourTimeZoneHere"); // Replace with your desired time zone
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(requestInstant, zoneId);
        int hour = zonedDateTime.getHour();
        return hour < 8 || hour >= 20; // Nighttime considered between 8 PM and 8 AM
    }

    private boolean isWeekend(Instant requestInstant) {
        // Implement logic to check if it's the weekend
        ZoneId zoneId = ZoneId.of("YourTimeZoneHere"); // Replace with your desired time zone
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(requestInstant, zoneId);
        int dayOfWeek = zonedDateTime.getDayOfWeek().getValue(); // Monday: 1, Tuesday: 2, ..., Sunday: 7
        return dayOfWeek >= 6 && dayOfWeek <= 7; // Weekends are Saturday and Sunday
    }

}
