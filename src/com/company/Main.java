package com.company;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println(calc(InputExpression()));
    }

    public static String calc(String input) throws Exception {
        String[] operands = input.split("[+*/-]");
        String first = operands[0].trim();
        String second = operands[1].trim();
        boolean isArabic = Pattern.matches("\\d+", first);
        int[] numbers = ParseToArabic(first, second);

        String result = "";
        if ((numbers[0] > 0) && (numbers[0] <= 10) && (numbers[1] > 0) && (numbers[1] <= 10)) {
            if (input.contains("+")) result = Addition(numbers, isArabic);
            if (input.contains("-")) result = Subtraction(numbers, isArabic);
            if (input.contains("*")) result = Multiplication(numbers, isArabic);
            if (input.contains("/")) result = Division(numbers, isArabic);
        }
        else {
            throw new Exception();
        }
        return result;
    }

    public static String Addition(int[] nums, boolean isArabic) {
        String result;
        int sum = nums[0] + nums[1];
        if (isArabic) result = String.valueOf(sum);
        else result = arabicToRoman(sum);

        return result;
    }

    public static String Subtraction(int[] nums, boolean isArabic) throws Exception {
        String result;
        int subs = nums[0] - nums[1];
        if (isArabic) result = String.valueOf(subs);
        else if (subs < 1) throw new Exception();
        else result = arabicToRoman(subs);

        return result;
    }

    public static String Multiplication(int[] nums, boolean isArabic) {
        String result;
        int mult = nums[0] * nums[1];
        if (isArabic) result = String.valueOf(mult);
        else result = arabicToRoman(mult);

        return result;
    }

    public static String Division(int[] nums, boolean isArabic) {
        String result;
        int div = nums[0] / nums[1];
        if (isArabic) result = String.valueOf(div);
        else result = arabicToRoman(div);

        return result;
    }

    public static int[] ParseToArabic(String strOne, String strTwo) throws Exception {
        int[] integerOperands = new int[2];
        if ((Pattern.matches("\\d+", strOne)) && (Pattern.matches("\\d+", strTwo))) {
            integerOperands[0] = Integer.parseInt(strOne);
            integerOperands[1] = Integer.parseInt(strTwo);
        }
        else if (!(Pattern.matches("\\d+", strOne)) && !(Pattern.matches("\\d+", strTwo))) {
            integerOperands[0] = romanToArabic(strOne);
            integerOperands[1] = romanToArabic(strTwo);
        }
        else {
            throw new Exception();
        }
        return integerOperands;
    }

    public static String InputExpression() throws Exception {
        String input;
        Scanner scanner = new Scanner(System.in);
        input = scanner.nextLine();
        if (input.isEmpty()) throw new Exception();
        if (!CheckingMathCondition(input)) throw new Exception();

        return input;
    }

    public static boolean CheckingMathCondition(String input) {
        int countOfOperations = 0;
        char[] symbols = input.toCharArray();
        for (char ch : symbols ) {
            if ((ch == '+') ||( ch == '-') || (ch == '*') || (ch == '/')) countOfOperations++;
        }
        return countOfOperations == 1;
    }

    public static int romanToArabic(String input) {
        String romanNumeral = input.toUpperCase();
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        return result;
    }

    public static String arabicToRoman(int number) {
        if (number < 1)
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }

}
