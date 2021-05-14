package de.bloodeko.towns.core.townsettings.legacy;

import de.bloodeko.towns.core.towns.legacy.Town;

//events for create,load,delete.
public class AdvancedSetting {
    public final Setting settingKey;
    public final PriceProvider prices;
    public final BuyCondition stages;
    public final NameProvider names;
    
    /*
    public final Setting value;
    public final int minStage;
    public final int price;
    public final String display;
    public final boolean hidden;
    public DisplayHandler displayHandler;
    */
    
    public AdvancedSetting(Setting setting, PriceProvider prices, BuyCondition stages, NameProvider names) {
        this.settingKey = setting;
        this.prices = prices;
        this.stages = stages;
        this.names = names;
    }
    
    public boolean matches(Town town) {
        return stages.canBuy(town);
    }
    
    
    /*
    public String display(Town town, Object obj) {
        if (obj == null)  {
            return Messages.get("cmds.info.nullValue");
        }
        if (displayHandler == null) {
            Object result = value.serialize(obj);
            if (result == null) {
                return Messages.get("cmds.info.nullValue");
            }
            return result.toString();
        }
        return displayHandler.display(town, obj);
    }
    
    public void addDisplayHandler(DisplayHandler displayHandler) {
        this.displayHandler = displayHandler;
    }
    */
}
