package com.restsecure;

import java.util.List;
import java.util.Random;
import java.util.Vector;

public class GenerateDataHelper {

    public static String getRandomEmail(int size) {
        return getRandomString(size) + "@example.net";
    }

    public static String getRandomString(int size) {
        Random random = new Random();
        final int initialCapacity = 62;
        final List<Character> chars = new Vector<>(initialCapacity);

        for (char c = '0'; c <= '9'; c++) {
            chars.add(c);
        }

        for (char c = 'A'; c <= 'Z'; c++) {
            chars.add(c);
        }

        for (char c = 'a'; c <= 'z'; c++) {
            chars.add(c);
        }

        final char[] charArray = new char[size - 1];

        for (int i = 0; i < size - 1; i++) {
            charArray[i] = chars.get(random.nextInt(initialCapacity));
        }

        return new String(charArray) + random.nextInt(9);
    }

    public static String getRandomPhone() {
        return "799980" + getRandomNumber(0, 10) + getRandomNumber(10, 100) + getRandomNumber(10, 100);
    }

    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        if (max == min)
            return min;
        else
            return random.nextInt(max - min) + min;
    }
}
