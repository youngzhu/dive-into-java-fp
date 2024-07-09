package com.youngzy.function.datetime;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * 日期/时间工具类
 * @author youngzy
 * @since 2024-07-09
 */
public class DateTimes {

    public static Date newDate(int year, int month, int dayOfMonth) {
        LocalDate localDate = LocalDate.of(year, month, dayOfMonth);

        return localDateToDate(localDate);
    }

    public static Date localDateToDate(final LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }
}
