package de.bloodeko.towns;

import java.util.HashMap;
import java.util.Map;

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
     * Returns true if the last access is within the timeout frame.
     * Forgets about the access, if it was successful.
     * Otherwise sets the last access to now.
     */
    public boolean passForgetting(Object key) {
        if (canPass(key)) {
            data.remove(key);
            return true;
        }
        data.put(key, System.currentTimeMillis());
        return false;
    }
    
    private boolean canPass(Object key) {
        Long last = data.get(key);
        if (last == null) {
            return false;
        }
        long waited = System.currentTimeMillis() - last;
        return waited < timeout;
    }
    
    public static void main(String[] args) throws InterruptedException {
        DoubleCheck check = new DoubleCheck(1000);
        
        System.out.println(check.passForgetting("abc"));
        Thread.sleep(500);
        System.out.println(check.passForgetting("abc"));
        System.out.println(check.passForgetting("abc"));
        Thread.sleep(100);
        System.out.println(check.passForgetting("abc"));
    }
}
