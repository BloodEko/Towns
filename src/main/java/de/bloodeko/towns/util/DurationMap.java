package de.bloodeko.towns.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Allows passing, when a given time is elapsed.
 */
public class DurationMap {
    private static final boolean pass = true;
    private static final boolean fail = false;
    
    private Map<Object, Long> map;
    private long minWait;
    
    /**
     * Returns the minimum time to wait in milliseconds.
     */
    public DurationMap(long minWait) {
        this.map = new HashMap<>();
        this.minWait = minWait;
    }
    
    /**
     * The object can pass if the last pass is longer ago 
     * than the delay.
     */
    public boolean canPass(Object obj) {
        Long past = map.get(obj);
        Long now = System.currentTimeMillis();
        
        if (past == null) {
            passed(now, obj);
            return pass;
        }
        if ((now - past) >= minWait) {
            passed(now, obj);
            return pass;
        }
        return fail;
    }
    
    private void passed(long now, Object obj) {
        map.put(obj, now);
    }
    
    /**
     * Resets the state for the given object.
     */
    public void forget(Object obj) {
        map.remove(obj);
    }
}
