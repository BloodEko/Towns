package de.bloodeko.towns.town;

import java.util.HashMap;
import java.util.Map;

import com.sk89q.worldguard.protection.flags.Flag;

public class SettingsMap {
    private Map<String, TownSetting> map;
    
    public SettingsMap(Map<String, TownSetting> map) {
        this.map = map;
    }
    
    public static class PvpTownSetting implements TownSetting {

        @Override
        public String getDisplay() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public int getPrice() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public void checkRequirements(Town town) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public Flag<?> getFlag() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String serialize() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Object deserialize() {
            return null;
        }
    }
    
    public static class TownSettingsFactroy {
        
        public static Map<String, TownSetting> newMap() {
            Map<String, TownSetting> map = new HashMap<>();
            return map;
        }
        
    }
    
    public static interface TownSetting {
        public String getDisplay();
        public int getPrice();
        public void checkRequirements(Town town);
        public Flag<?> getFlag();
        public String serialize();
        public Object deserialize();
    }
}
