package com.qmw.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class BigDecimalUtil {

    public static BigDecimal add(Object... objects) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Object o : objects)
            sum = sum.add(new BigDecimal(String.valueOf(o)));
        return sum;
    }

    public static BigDecimal divide(int scale, Object... objects) {
        BigDecimal divisor = new BigDecimal(objects[0].toString());

        BigDecimal dividend = multiply(Arrays.stream(objects).s)
        for (int i = 1; i < objects.length; i++)
            dividend = dividend.multiply(new BigDecimal(String.valueOf(o)));


        return bigDecimal.setScale(scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal multiply(Object... objects) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Object o : objects)
            sum = sum.multiply(new BigDecimal(String.valueOf(o)));
        return sum;
    }

}
