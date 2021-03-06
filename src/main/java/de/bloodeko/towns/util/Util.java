package de.bloodeko.towns.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringJoiner;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.sk89q.worldguard.protection.flags.StateFlag.State;

public class Util {
    private static DecimalFormat format = new DecimalFormat("0.##", 
      DecimalFormatSymbols.getInstance(Locale.ENGLISH));
    
    /**
     * Formats the value by 2 places and stripping 
     * trailing zeroes.
     */
    public static String format(double val) {
        return format.format(val);
    }
    
    /**
     * If the length is 0 returns the input. Returns
     * a new array with the first entry cut off.
     */
    public static String[] strip0(String[] args) {
        if (args.length == 0) return args;
        return Arrays.copyOfRange(args, 1, args.length);
    }

    /**
     * Uses the input and creates a new filtered list of strings
     * that start with the given argument. Ignores casing.
     */
    public static List<String> filterLowerList(Collection<String> input, String arg) {
        List<String> list = new ArrayList<>();
        arg = arg.toLowerCase();
        for (String key : input) {
            if (key.toLowerCase().startsWith(arg)) {
                list.add(key);
            }
        }
        return list;
    }
    
    /**
     * Uses the input and creates a new filtered list of strings
     * that start with the given argument.
     */
    public static List<String> filterList(Collection<String> input, String arg) {
        List<String> list = new ArrayList<>();
        for (String key : input) {
            if (key.startsWith(arg)) {
                list.add(key);
            }
        }
        return list;
    }
    
    /**
     * The input is checked against lowercase letters, 
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
    
    /**
     * Creates an Item from the material and sets
     * the display name. Optionally specify a lore.
     */
    public static ItemStack createItem(Material mat, String name, String... lore) {
        ItemStack stack = new ItemStack(mat);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        if (lore.length > 0) {
            meta.setLore(Arrays.asList(lore));
        }
        stack.setItemMeta(meta);
        return stack;
    }

    /**
     * Writes the Exception to the file. In case of an
     * IoException prints it to the console.
     */
    public static void writeException(File path, Exception error) {
        try (FileWriter fw = new FileWriter(path, true);
             PrintWriter pw = new PrintWriter(fw)) {
            error.printStackTrace(pw);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
