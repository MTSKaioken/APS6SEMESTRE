package utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtils {

    public static String toPascalCase(String str) {
        return Arrays.stream(str.toLowerCase().split("[\\s-]+"))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining());
    }
}
