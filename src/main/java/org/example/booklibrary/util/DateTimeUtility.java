package org.example.booklibrary.util;

import java.time.Instant;
import java.util.Date;

public class DateTimeUtility {
    public static Date getCurrentUTCDateTime() {
        return Date.from(Instant.now());
    }
}
