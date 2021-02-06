package com.qmw.util;

import java.math.BigDecimal;
import java.util.function.Function;

public class FunctionUtil {

    public static final Function<Object, BigDecimal> TO_BIG_DECIMAL = e -> new BigDecimal(e.toString());

}
