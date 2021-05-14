package de.bloodeko.towns.core.townsettings.legacy;

import de.bloodeko.towns.core.townplots.PlotSetting;
import de.bloodeko.towns.core.townplots.PlotSetting.PlotDisplay;
import de.bloodeko.towns.core.towns.legacy.Town;
import de.bloodeko.towns.core.townsettings.legacy.general.DamageAnimalsSetting;
import de.bloodeko.towns.core.townsettings.legacy.general.NameSetting;
import de.bloodeko.towns.core.townsettings.legacy.general.PearlSetting;
import de.bloodeko.towns.core.townsettings.legacy.general.PvpSetting;
import de.bloodeko.towns.core.townsettings.legacy.general.SlimeSetting;
import de.bloodeko.towns.core.townsettings.legacy.general.WarpSetting;
import de.bloodeko.towns.core.townsettings.legacy.general.ZombieSetting;
import de.bloodeko.towns.core.townsettings.legacy.general.SlimeSetting.SlimeDisplay;
import de.bloodeko.towns.core.townsettings.legacy.general.WarpSetting.WarpDisplay;
import de.bloodeko.towns.core.townsettings.legacy.general.ZombieSetting.ZombieDisplay;
import de.bloodeko.towns.util.Messages;

public class Settings {
    public static final NameSetting NAME = new NameSetting();
    public static final DamageAnimalsSetting DAMAGE_ANIMALS = new DamageAnimalsSetting();
    public static final PvpSetting PVP = new PvpSetting();
    public static final WarpSetting WARP = new WarpSetting();
    public static final PlotSetting PLOTS = new PlotSetting();
    public static final PearlSetting PEARL = new PearlSetting();
    public static final SlimeSetting SLIME = new SlimeSetting();
    public static final ZombieSetting ZOMBIE = new ZombieSetting();
    
    public static SettingsRegistry newRegistry() {
        SettingsRegistry registry = new SettingsRegistry();
        
        AdvancedSetting name = newSetting(NAME, 0, 0, newHiddenDisplay());
        AdvancedSetting animals = newSetting(DAMAGE_ANIMALS, 1, 3000, "damageAnimals");
        AdvancedSetting pvp = newSetting(PVP, 1, 1000, "pvp");
        AdvancedSetting warp = newSetting(WARP, 2, 4000, new WarpDisplay());
        AdvancedSetting plots = newSetting(PLOTS, 2, 5000, new PlotDisplay());
        AdvancedSetting pearl = newSetting(PEARL, 2, 4000, "pearl");
        AdvancedSetting slime = newSetting(SLIME, 2, 5000, new SlimeDisplay());
        AdvancedSetting zombie = newSetting(ZOMBIE, 2, 5000, new ZombieDisplay());
        
        registry.register(name);
        registry.register(animals);
        registry.register(pvp);
        registry.register(warp);
        registry.register(plots);
        registry.register(pearl);
        registry.register(slime);
        registry.register(zombie);
        
        return registry;
    }
    
    public static AdvancedSetting newSetting(Setting setting, int minStage, int price, NameProvider names) {
        BuyCondition stages = new MinStage(minStage);
        PriceProvider prices = new DefaultPrice(price);
        return new AdvancedSetting(setting, prices, stages, names);
    }
    
    public static AdvancedSetting newSetting(Setting setting, int minStage, int price, String name) {
        BuyCondition stages = new MinStage(minStage);
        NameProvider names = new DefaultDisplay(setting, Messages.get("settings." + name), false);
        PriceProvider prices = new DefaultPrice(price);
        return new AdvancedSetting(setting, prices, stages, names);
    }
    
    public static NameProvider newHiddenDisplay() {
        return new DefaultDisplay(null, "hiden", true);
    }
    
    
    public static class MinStage implements BuyCondition {
        private int minStage;
        
        public MinStage(int minStage) {
            this.minStage = minStage;
        }
        
        @Override
        public boolean canBuy(Town town) {
            return town.getSettings().getStage().getStage() >= minStage;
        }
    }
    
    
    public static class DefaultPrice implements PriceProvider {
        private int price;
        
        public DefaultPrice(int price) {
            this.price = price;
        }
        
        @Override
        public int getPrice() {
            return price;
        }
    }
    
    
    public static class DefaultDisplay implements NameProvider {
        private Setting setting;
        private String name;
        private boolean hidden;
        
        public DefaultDisplay(Setting setting, String name, boolean hidden) {
            this.setting = setting;
            this.name = name;
            this.hidden = hidden;
        }
        
        @Override
        public String display(Town town) {
            return setting.read(town.getSettings().getFlags()) + "";
        }

        @Override
        public String getName() {
            return name;
        }
        
        @Override
        public int getPriority() {
            return 1;
        }

        @Override
        public boolean isHidden() {
            return hidden;
        }
    }
}
