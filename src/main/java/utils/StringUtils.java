package utils;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtils {

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    public static String toPascalCase(String str) {
        return Arrays.stream(str.toLowerCase().split("[\\s-]+"))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining());
    }
}
