package pt.amado.wisetax.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class DateUtils {

    private DateUtils(){}

    public static boolean isWeekday(Instant requestInstant) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(requestInstant, zoneId);
        int dayOfWeek = zonedDateTime.getDayOfWeek().getValue(); // Monday: 1, Tuesday: 2, ..., Sunday: 7
        return dayOfWeek >= 1 && dayOfWeek <= 5; // Weekdays are Monday to Friday
    }

    public static boolean isNighttime(Instant requestInstant) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(requestInstant, zoneId);
        int hour = zonedDateTime.getHour();
        return hour < 8 || hour >= 20;
    }

    public static boolean isWeekend(Instant requestInstant) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(requestInstant, zoneId);
        int dayOfWeek = zonedDateTime.getDayOfWeek().getValue();
        return dayOfWeek >= 6 && dayOfWeek <= 7;
    }

}
