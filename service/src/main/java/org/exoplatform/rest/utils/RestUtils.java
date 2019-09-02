package org.exoplatform.rest.utils;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.util.Arrays;
import java.util.List;

public class RestUtils {

    private RestUtils() {}

    public static boolean isValidBooleanParameter(String value) {
        List<String> booleansValueAsString = Arrays.asList("true", "false");
        return isNotEmpty(value) && booleansValueAsString.contains(value);
    }
}
