package de.bloodeko.towns.session1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
}
