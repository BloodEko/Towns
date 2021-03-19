package de.bloodeko.towns.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

public class Util {
    
    /**
     * If the length is 0 returns the input. Returns
     * a new array with the first entry cut off.
     */
    public static String[] strip0(String[] args) {
        if (args.length == 0) return args;
        return Arrays.copyOfRange(args, 1, args.length);
    }
    
    /**
     * Returns a list of entries that start with the argument.
     */
    public static List<String> filterList(Collection<String> filter, String arg) {
        List<String> list = new ArrayList<>();
        for (String key : filter) {
            if (key.startsWith(arg)) {
                list.add(key);
            }
        }
        return list;
    }

    /**
     * Filters the list of entries for the first argument.
     */
    public static List<String> filterList(List<String> list, String[] args) {
        if (args.length == 1) {
            return filterList(list, args[0]);
        }
        return list;
    }
    
    /**
     * The input is checked against alphabetic letters,
     * numbers and basic symbols.
     */
    public static boolean isValidName(String name) {
        for (char c : name.toCharArray()) {
            switch (Character.toLowerCase(c)) {
                case 'a': case 'b': case 'c':  case 'd':  case 'e':
                case 'f': case 'g': case 'h':  case 'i':  case 'j':
                case 'k': case 'l': case 'm':  case 'n':  case 'o':
                case 'p': case 'q': case 'r':  case 's':  case 't':
                case 'u': case 'v': case 'w':  case 'x':  case 'y':
                case 'z': case 'ß': case 'ä':  case 'ö':  case 'ü':
                case '0': case '1': case '2':  case '3':  case '4':
                case '5': case '6': case '7':  case '8':  case '9':
                case ' ': case '-': case '_':
                    continue;
                default:
                    return false;
            }
        }
        return true;
    }
    
    /**
     * Returns the entries as separated String.
     */
    public static String join(Collection<?> list, String separator) {
        StringJoiner joiner = new StringJoiner(separator);
        for (Object value : list) {
            joiner.add(value.toString());
        }
        return joiner.toString();
    }
}
