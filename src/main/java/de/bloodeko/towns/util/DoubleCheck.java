package de.bloodeko.towns.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Requires double passing of the gate within a specific time.
 * The class has no cleanup, so consider memory usage.
 */
public class DoubleCheck {
    private final Map<Object, Long> data = new HashMap<>();
    private final int timeout;
    
    public DoubleCheck(int milliseconds) {
        this.timeout = milliseconds;
    }
    
    /**
     * Returns true if the last access is within the timeout frame 
     * and forgets the object. Otherwise sets the last access to now.
     */
    public boolean passForgetting(Object key) {
        if (canPass(key)) {
            data.remove(key);
            return true;
        }
        data.put(key, System.currentTimeMillis());
        return false;
    }

    /**
     * Returns true if the last access is within the timeout frame.
     * Each call resets the timeout frame to its full time.
     */
    public boolean passExtending(Object key) {
        boolean pass = canPass(key);
        data.put(key, System.currentTimeMillis());
        return pass;
    }
    
    private boolean canPass(Object key) {
        Long last = data.get(key);
        if (last == null) {
            return false;
        }
        long waited = System.currentTimeMillis() - last;
        return waited < timeout;
    }
    
    /**
     * Returns the timeout time in seconds.
     * An example output might be "10s".
     */
    public String toSeconds() {
        return Util.format(timeout / 1000.0) + "s";
    }
    
    /**
     * Combines 2 objects to a single key. Uses their 
     * equals() and hashCode() implementations.
     */
    public static class Combined {
        private final Object one;
        private final Object two;
        
        public Combined(Object one, Object two) {
            this.one = one;
            this.two = two;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Combined) {
                Combined key = (Combined) obj;
                return Objects.equals(one, key.one)
                    && Objects.equals(two, key.two);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(one, two);
        }
    }
}
