package de.bloodeko.towns.core.townsettings.legacy;

import de.bloodeko.towns.core.towns.Town;

/**
 * Bundles all aspects of a setting, including 
 * its value, price, conditions and displaying.
 */
public class AdvancedSetting {
    public final Setting settingKey;
    public final PriceProvider prices;
    public final BuyCondition condition;
    public final NameProvider names;
    
    public AdvancedSetting(Setting setting, PriceProvider prices, BuyCondition condition, NameProvider names) {
        this.settingKey = setting;
        this.prices = prices;
        this.condition = condition;
        this.names = names;
    }
    
    /**
     * Returns true if the town matches the requirements 
     * to buy this setting.
     */
    public boolean matches(Town town) {
        return condition.canBuy(town);
    }
}
