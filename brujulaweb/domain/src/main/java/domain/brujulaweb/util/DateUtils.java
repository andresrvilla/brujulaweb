package domain.brujulaweb.util;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateUtils {
    private static final ZoneId ZONE_ID = ZoneId.of("GMT-3");

    public static ZonedDateTime now() {
        return ZonedDateTime.now().withZoneSameInstant(ZONE_ID);
    }

    public static ZonedDateTime fromTimestamp(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return ZonedDateTime.ofInstant(timestamp.toInstant(), ZONE_ID);
    }
}
