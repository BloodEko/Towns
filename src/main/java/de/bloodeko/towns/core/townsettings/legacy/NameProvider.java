package de.bloodeko.towns.core.townsettings.legacy;

import de.bloodeko.towns.core.towns.Town;

public interface NameProvider {
    
    /**
     * Reads the setting from the town and displays its value.
     */
    String display(Town town);
    
    /**
     * Returns the display name of the setting.
     */
    String getName();
    
    /**
     * Returns the display priority, higher priorities 
     * are listed first. Returns 0 by default.
     */
    default int getPriority() {
        return 0;
    }
}
