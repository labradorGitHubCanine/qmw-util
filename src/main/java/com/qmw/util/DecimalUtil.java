package com.qmw.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;

public class DecimalUtil {

    public static BigDecimal add(Object... objects) {
        return Arrays.stream(objects).filter(Objects::nonNull).map(o -> new BigDecimal(o.toString())).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal subtract(Object o1, Object... o2) {
        BigDecimal minuend = new BigDecimal(o1.toString());
        BigDecimal subtraction = add(o2);
        return minuend.subtract(subtraction);
    }

    public static BigDecimal multiply(Object... objects) {
        BigDecimal sum = BigDecimal.ONE;
        for (Object o : objects)
            if (o != null)
                sum = sum.multiply(new BigDecimal(o.toString()));
        return sum;
    }

    public static BigDecimal divide(int scale, Object o1, Object... o2) {
        BigDecimal divisor = new BigDecimal(o1.toString());
        BigDecimal dividend = multiply(o2);
        return divisor.divide(dividend, scale, RoundingMode.HALF_UP);
    }

}
