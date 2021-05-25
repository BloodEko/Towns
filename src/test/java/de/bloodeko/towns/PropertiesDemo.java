package de.bloodeko.towns;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.bloodeko.towns.util.Util;

/**
 * Scans the source folder, to find keys from the
 * message.properties which are unused.
 */
public class PropertiesDemo {
    private static final File root = new File("");
    
    public static void main(String[] args) throws Exception {
        List<String> lines = getPropertiesLines();
        Set<String> keys = getPropertiesKeys(lines);
        
        scanFile(new File(root.getAbsolutePath() + "/src/main/java"), keys);
        for (String key : keys)
            System.out.println(key);
    }
    
    /**
     * Returns all lines of the properties file.
     */
    static List<String> getPropertiesLines() throws Exception {
        String path = root.getAbsolutePath() + "/src/main/resources/messages.properties";
        return Files.readAllLines(Paths.get(path));
    }
    
    /**
     * Returns the properties File parsed as Map.
     */
    static Set<String> getPropertiesKeys(List<String> lines) {
        Map<String, String> map = new HashMap<>();
        Util.parseProperties(map, lines);
        return map.keySet();
    }
    
    /**
     * Scans the folders and files recursively by their content for keys 
     * of the map, and removes and keys from the map, which are not 
     * found in the content.
     */
    static void scanFile(File file, Set<String> keys) throws Exception {
        if (file.isDirectory()) {
            for (File f : file.listFiles())
                scanFile(f, keys);
        } else {
            String text = readFile(file.toPath());
            for (Iterator<String> it = keys.iterator(); it.hasNext();) {
                String key = it.next();
                if (text.contains(key))
                    it.remove();
            }
        }
    }
    
    /**
     * Reads the file to a single String.
     */
    static String readFile(Path path) throws Exception {
        byte[] encoded = Files.readAllBytes(path);
        return new String(encoded, StandardCharsets.UTF_8);
    }
}
