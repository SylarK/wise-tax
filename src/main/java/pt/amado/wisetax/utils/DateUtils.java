package pt.amado.wisetax.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class DateUtils {

    private DateUtils(){}

    public static boolean isWeekday(Instant requestInstant) {
        // Implement logic to check if it's a weekday
        ZoneId zoneId = ZoneId.of("YourTimeZoneHere"); // Replace with your desired time zone
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(requestInstant, zoneId);
        int dayOfWeek = zonedDateTime.getDayOfWeek().getValue(); // Monday: 1, Tuesday: 2, ..., Sunday: 7
        return dayOfWeek >= 1 && dayOfWeek <= 5; // Weekdays are Monday to Friday
    }

    public static boolean isNighttime(Instant requestInstant) {
        // Implement logic to check if it's nighttime
        ZoneId zoneId = ZoneId.of("YourTimeZoneHere"); // Replace with your desired time zone
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(requestInstant, zoneId);
        int hour = zonedDateTime.getHour();
        return hour < 8 || hour >= 20; // Nighttime considered between 8 PM and 8 AM
    }

    public static boolean isWeekend(Instant requestInstant) {
        // Implement logic to check if it's the weekend
        ZoneId zoneId = ZoneId.of("YourTimeZoneHere"); // Replace with your desired time zone
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(requestInstant, zoneId);
        int dayOfWeek = zonedDateTime.getDayOfWeek().getValue(); // Monday: 1, Tuesday: 2, ..., Sunday: 7
        return dayOfWeek >= 6 && dayOfWeek <= 7; // Weekends are Saturday and Sunday
    }

}
