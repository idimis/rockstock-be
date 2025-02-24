package com.rockstock.backend.common.utils;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderCodeGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateOrderCode(Long userId, Long orderId, LocalDate orderDate) {
        String formattedDate = orderDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomString = generateRandomAlphanumeric(10);
        return String.format("RS-ORD-%d%d%s-%s", userId, orderId, formattedDate, randomString);
    }

    private static String generateRandomAlphanumeric(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
