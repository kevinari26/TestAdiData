package com.test.adidata.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import org.slf4j.Logger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.UUID;

public class CommonUtil {
    // generate
    public static String generateUuid() {
        StringBuilder uuid = new StringBuilder();
        uuid.append(UUID.randomUUID());
        uuid.replace(23, 24, "");
        uuid.replace(18, 19, "");
        uuid.replace(13, 14, "");
        uuid.replace(8, 9, "");
        uuid.insert(0, System.currentTimeMillis());
        return uuid.toString();
    }
    public static Date currentTime() {
        return new Date(System.currentTimeMillis());
    }
    public static String generateOtp() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }


    // jsonNode
    public static String convertJsonNodePreserveNullAndTrim(JsonNode input) {
        if (input==null || input.asText().equals("")) {
            return null;
        }
        else {
            return input.asText().trim();
        }
    }
    public static Date parseJsonNodeStringToDate(JsonNode jsonNode, String parseFormat) {
        try {
            if (jsonNode==null || "".equals(jsonNode.asText().trim())) return null;
            return new SimpleDateFormat(parseFormat).parse(jsonNode.asText());
        } catch (Exception e) {
            return null;
        }
    }
    public static Date jsonNodeLongToDate(JsonNode jsonNode) {
        try {
            if (jsonNode==null) return null;
            return new Date(jsonNode.asLong());
        } catch (Exception e) {
            return null;
        }
    }
    public static BigDecimal jsonNodeStringToBigDecimal(JsonNode jsonNode) {
        try {
            if (jsonNode==null || "".equals(jsonNode.asText().trim())) return null;
            return new BigDecimal(jsonNode.asText());
        } catch (Exception e) {
            return null;
        }
    }
    public static BigDecimal jsonNodeStringToBigDecimal(JsonNode jsonNode, BigDecimal defaultValue) {
        try {
            if (jsonNode==null || "".equals(jsonNode.asText().trim())) return defaultValue;
            return new BigDecimal(jsonNode.asText());
        } catch (Exception e) {
            return defaultValue;
        }
    }
    public static Long jsonNodeStringToLong(JsonNode jsonNode) {
        try {
            if (jsonNode==null || "".equals(jsonNode.asText().trim())) return null;
            return new Long(jsonNode.asText());
        } catch (Exception e) {
            return null;
        }
    }
    public static Long jsonNodeDoubleToLong(JsonNode jsonNode) {
        try {
            if (jsonNode==null) return null;
            return Math.round(jsonNode.asDouble());
        } catch (Exception e) {
            return null;
        }
    }
    public static boolean isJsonNodeNull(JsonNode jsonNode) {
        if (jsonNode == null || jsonNode instanceof NullNode) return true;
        else return false;
    }


    // logging
    public static void infoLog(Logger log, String title, String request) {
        log.info(String.format("\n=== %s ===", title));
        log.info(log.getName());
        log.info("request: {}", request);
    }

    public static void errorLog(Logger log, Exception e) {
        log.error("\n=== Error Log ===");
        log.error("Cause: {}", e.toString());
        for (StackTraceElement stack : e.getStackTrace()) {
            log.error("Stack: {}", stack);
        }
        Throwable rootCause = e.getCause();
        while (rootCause!=null) {
            log.error("RootCause: {}", rootCause.toString());
            for (StackTraceElement stack : rootCause.getStackTrace()) {
                log.error("Stack: {}", stack);
            }
            rootCause = rootCause.getCause();
        }
    }

}
