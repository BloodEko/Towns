package de.bloodeko.towns.core.townsettings.legacy;

import de.bloodeko.towns.core.towns.Town;

public interface BuyCondition {
    
    /**
     * Returns true if the town fulfills the 
     * requirements for a purchase of something.
     */
    public boolean canBuy(Town town);
}
