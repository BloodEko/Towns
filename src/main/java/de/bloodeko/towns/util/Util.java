package de.bloodeko.towns.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import com.sk89q.worldguard.protection.flags.StateFlag.State;

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
     * Ignores case sensitivity.
     */
    public static List<String> filterLowerList(Collection<String> filter, String arg) {
        List<String> list = new ArrayList<>();
        arg = arg.toLowerCase();
        for (String key : filter) {
            if (key.toLowerCase().startsWith(arg)) {
                list.add(key);
            }
        }
        return list;
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
    
    /**
     * Trims the input double to 2 places.
     * Works best for values up to 1 Million.
     */
    public static double trimDouble(double value) {
        long temp = (long) (value * 100.0);
        return ((double) temp) / 100.0;
    }
    
    /**
     * Parses all lines in a properties file and writes the results 
     * to the map.
     */
    public static void parseProperties(Map<String, String> map, List<String> lines) {
        for (String line : lines) {
            parseLine(map, line);
        }
    }
    
    /**
     * Parses the line and adds the result to the map. Ignores lines 
     * beginning with a '#' and splits on the '=' symbol.
     */
    public static void parseLine(Map<String, String> map, String line) {
        if (line.startsWith("#")) {
            return;
        }
        int delimeter = line.indexOf('=');
        if (delimeter == -1) {
            return;
        }
        String key = line.substring(0, delimeter);
        String value = line.substring(delimeter+1, line.length());
        map.put(key, value);
    }
    
    /**
     * Reads the input stream in UTF8 and writes the lines
     * to an result List.
     */
    public static List<String> readLines(InputStream in) {
        List<String> list = new ArrayList<>();
        Charset charset = StandardCharsets.UTF_8;
        try (InputStreamReader inReader = new InputStreamReader(in, charset);
          BufferedReader reader = new BufferedReader(inReader)
        ) {
            String line;
            while((line = reader.readLine()) != null) {
                list.add(line);
            }
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
    public static boolean toBoolean(State state) {
        return state == State.ALLOW ? true : false;
    }
    
    public static State toState(boolean bool) {
        return bool ? State.ALLOW : State.DENY;
    }
}
