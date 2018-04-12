package com.tmoncorp.api.dealinfo.connector;


import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;

/**
 * Created by dwkang on 2016-03-16.
 */
public class ValidateCheckUtil {
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("[yyyy-MM-dd' ']HH:mm:ss");

    public static boolean isNotZero(Integer target) {
        if (Objects.isNull(target)) {
            return false;
        }

        if (Integer.compare(target, 0) == 0) {
            return false;
        }

        return true;
    }

    public static boolean isNotZero(Long target) {
        if (Objects.isNull(target)) {
            return false;
        }

        if (Long.compare(target, 0L) == 0) {
            return false;
        }

        return true;
    }

    public static boolean isUpperThanZero(Integer target) {
        if (Objects.isNull(target)) {
            return false;
        }

        if (Integer.compare(target, 0) <= 0) {
            return false;
        }

        return true;
    }

    public static boolean isUpperThanZero(Long target) {
        if (Objects.isNull(target)) {
            return false;
        }

        if (Long.compare(target, 0L) <= 0) {
            return false;
        }

        return true;
    }

    public static boolean isValidDateFormatValue(String target) {
        if (StringUtils.isBlank(target)) {
            return false;
        }

        try {
            TemporalAccessor temporalAccessor = DTF.parseBest(target, LocalDateTime::from, LocalTime::from);

            if (temporalAccessor instanceof LocalDateTime || temporalAccessor instanceof LocalTime) {
                return true;
            }

            return false;
        } catch (DateTimeParseException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
