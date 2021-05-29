package de.bloodeko.towns.core.townsettings.legacy;

import de.bloodeko.towns.core.towns.Town;

public interface NameProvider {
    
    /**
     * Reads the setting from the town and displays its value.
     */
    public String display(Town town);
    
    /**
     * Returns the display name of the setting.
     */
    public String getName();
    
    /**
     * Returns the display priority.
     * Higher priorities are listed first.
     */
    public int getPriority();
    
    /**
     * Returns true for settings which are not meant to be displayed.
     */
    public boolean isHidden();
}
