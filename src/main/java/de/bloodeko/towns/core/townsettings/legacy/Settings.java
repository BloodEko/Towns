package de.bloodeko.towns.core.townsettings.legacy;

import de.bloodeko.towns.core.townchat.ChatSetting;
import de.bloodeko.towns.core.townchat.ui.ChatDisplay;
import de.bloodeko.towns.core.townplots.PlotSetting;
import de.bloodeko.towns.core.townplots.PlotSetting.PlotDisplay;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.core.townsettings.legacy.general.AnimalSetting;
import de.bloodeko.towns.core.townsettings.legacy.general.BankSetting;
import de.bloodeko.towns.core.townsettings.legacy.general.PearlSetting;
import de.bloodeko.towns.core.townsettings.legacy.general.PvpSetting;
import de.bloodeko.towns.core.townsettings.legacy.general.SlimeSetting;
import de.bloodeko.towns.core.townsettings.legacy.general.SlimeSetting.SlimeDisplay;
import de.bloodeko.towns.core.townsettings.legacy.general.WarpSetting;
import de.bloodeko.towns.core.townsettings.legacy.general.WarpSetting.WarpDisplay;
import de.bloodeko.towns.core.townsettings.legacy.general.ZombieSetting;
import de.bloodeko.towns.core.townsettings.legacy.general.ZombieSetting.ZombieDisplay;
import de.bloodeko.towns.util.Messages;

public class Settings {
    public static final AnimalSetting DAMAGE_ANIMALS = new AnimalSetting();
    public static final PvpSetting PVP = new PvpSetting();
    public static final WarpSetting WARP = new WarpSetting();
    public static final PlotSetting PLOTS = new PlotSetting();
    public static final PearlSetting PEARL = new PearlSetting();
    public static final SlimeSetting SLIME = new SlimeSetting();
    public static final ZombieSetting ZOMBIE = new ZombieSetting();
    
    /**
     * Returns a new SettingsRegistry with all known settings included.
     */
    public static SettingsRegistry newRegistry() {
        SettingsRegistry registry = new SettingsRegistry();
        
        AdvancedSetting animals = newSetting(DAMAGE_ANIMALS, 1, 3000, "damageAnimals");
        AdvancedSetting pvp = newSetting(PVP, 1, 1000, "pvp");
        AdvancedSetting warp = newSetting(WARP, 2, 4000, new WarpDisplay());
        AdvancedSetting plots = newSetting(PLOTS, 2, 5000, new PlotDisplay());
        AdvancedSetting pearl = newSetting(PEARL, 2, 4000, "pearl");
        AdvancedSetting slime = newSetting(SLIME, 2, 5000, new SlimeDisplay());
        AdvancedSetting zombie = newSetting(ZOMBIE, 2, 5000, new ZombieDisplay());
        AdvancedSetting bank = newSetting(BankSetting.VALUE, 2, 6000, "bank");
        AdvancedSetting chat = newSetting(ChatSetting.VALUE, 2, 5000, new ChatDisplay());
        
        registry.register(animals);
        registry.register(pvp);
        registry.register(warp);
        registry.register(plots);
        registry.register(pearl);
        registry.register(slime);
        registry.register(zombie);
        registry.register(bank);
        registry.register(chat);
        
        return registry;
    }
    
    private static AdvancedSetting newSetting(Setting setting, int minStage, int price, NameProvider names) {
        BuyCondition stages = new MinStage(minStage);
        PriceProvider prices = new DefaultPrice(price);
        return new AdvancedSetting(setting, prices, stages, names);
    }
    
    private static AdvancedSetting newSetting(Setting setting, int minStage, int price, String name) {
        BuyCondition stages = new MinStage(minStage);
        NameProvider names = new DefaultDisplay(setting, Messages.get("settings." + name), false);
        PriceProvider prices = new DefaultPrice(price);
        return new AdvancedSetting(setting, prices, stages, names);
    }
    
    /**
     * Default implementation to define a minimum required
     * stage a setting would require to be purchased.
     */
    private static class MinStage implements BuyCondition {
        private int minStage;
        
        public MinStage(int minStage) {
            this.minStage = minStage;
        }
        
        /**
         * Returns true if the towns stage is greater or equal 
         * to the minimum required stage.
         */
        @Override
        public boolean canBuy(Town town) {
            return town.getStage().getValue() >= minStage;
        }
    }
    
    /**
     * Default implementation to define a price 
     * for a setting. Uses the given integer.
     */
    private static class DefaultPrice implements PriceProvider {
        private int price;
        
        public DefaultPrice(int price) {
            this.price = price;
        }
        
        @Override
        public int getPrice() {
            return price;
        }
    }
    
    /**
     * Default implementation to help displaying a setting.
     * Uses a name given and lets the Setting display values.
     */
    private static class DefaultDisplay implements NameProvider {
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
