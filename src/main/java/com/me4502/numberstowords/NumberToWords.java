package com.me4502.numberstowords;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class NumberToWords {

    private static final String[] NUMBERS = {
            "ZERO",
            "ONE",
            "TWO",
            "THREE",
            "FOUR",
            "FIVE",
            "SIX",
            "SEVEN",
            "EIGHT",
            "NINE"
    };

    private static final String[] TEENS = {
            "ELEVEN",
            "TWELVE",
            "THIRTEEN",
            "FOURTEEN",
            "FIFTEEN",
            "SIXTEEN",
            "SEVENTEEN",
            "EIGHTEEN",
            "NINETEEN"
    };

    private static final String[] TENS = {
            "TEN",
            "TWENTY",
            "THIRTY",
            "FORTY",
            "FIFTY",
            "SIXTY",
            "SEVENTY",
            "EIGHTY",
            "NINETY"
    };

    private static final String[] MAGNITUDES = {
            "HUNDRED",
            "THOUSAND",
            "MILLION",
            "BILLION",
            "TRILLION",
            "QUADRILLION",
            "QUINTILLION",
            "SEXTILLION",
            "SEPTILLION",
            "OCTILLION",
            "NONILLION",
            "DECILLION",
    };

    private static final Pattern NUMBER_PATTERN = Pattern.compile("(-?)[0-9]+(\\.[0-9][0-9]?)?");
    private static final Pattern DOT_PATTERN = Pattern.compile(".", Pattern.LITERAL);

    private boolean negative;
    private String number;
    private String dollarNumber;
    private String centsNumber;

    public NumberToWords(String number) {
        if (!NUMBER_PATTERN.matcher(number).matches()) {
            throw new IllegalArgumentException("Not a valid number!");
        }
        this.negative = number.startsWith("-");
        if (this.negative) {
            number = number.substring(1);
        }

        this.number = number;
        if (this.number.contains(".")) {
            String[] parts = DOT_PATTERN.split(this.number);
            this.dollarNumber = parts[0];
            this.centsNumber = parts[1];
        } else {
            this.dollarNumber = this.number;
            this.centsNumber = null;
        }
    }

    private String padString(String input, int padSize) {
        StringBuilder inputBuilder = new StringBuilder(input);
        while(inputBuilder.length() < padSize) {
            inputBuilder.insert(0, "0");
        }
        return inputBuilder.toString();
    }

    private String convertThreeDigits(String number, int magnitude) {
        int num = Integer.parseInt(number);
        if (magnitude == 0) {
            if (num <= 99) {
                return convertTwoDigits(number.substring(1));
            } else if (num % 100 == 0) {
                return NUMBERS[num / 100] + " " + MAGNITUDES[0];
            } else {
                return NUMBERS[num / 100] + " " + MAGNITUDES[0]
                        + " AND " + convertTwoDigits(number.substring(1));
            }
        } else {
            if (magnitude > MAGNITUDES.length) {
                throw new IllegalArgumentException("Number too big!");
            }
            return convertThreeDigits(number, 0) + " " + MAGNITUDES[magnitude];
        }
    }

    private String convertTwoDigits(String number) {
        int num = Integer.parseInt(number);
        if (num < 10) {
            return NUMBERS[num];
        } else if (num % 10 == 0) {
            return TENS[(num / 10) - 1];
        } else if (num < 20) {
            return TEENS[num - 11];
        } else {
            return TENS[(num / 10) - 1] + "-" + NUMBERS[num % 10];
        }
    }

    private String getWords(String number) {
        if (number.length() == 2) {
            return convertTwoDigits(number);
        } else if (number.length() == 3) {
            return convertThreeDigits(number, 0);
        } else {
            number = padString(number, (int) ((Math.ceil(number.length() / 3.0)) * 3));
            List<String> words = new ArrayList<>();
            for (int i = 0; i < number.length()/3; i++) {
                words.add(0, convertThreeDigits(number.substring(number.length() - (i+1)*3, number.length() - (i*3)), i));
            }
            return words.stream().collect(Collectors.joining(" "));
        }
    }

    public String convert() {
        List<String> words = new ArrayList<>();
        if (negative) {
            words.add("NEGATIVE");
        }
        words.add(getWords(dollarNumber));
        if (words.get(words.size() - 1).equals("ONE")) {
            words.add("DOLLAR");
        } else {
            words.add("DOLLARS");
        }
        if (centsNumber != null) {
            words.add("AND");
            words.add(getWords(centsNumber));
            if (words.get(words.size() - 1).equals("ONE")) {
                words.add("CENT");
            } else {
                words.add("CENTS");
            }
        }
        return words.stream().collect(Collectors.joining(" "));
    }
}
