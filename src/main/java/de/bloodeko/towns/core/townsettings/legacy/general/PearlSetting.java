package de.bloodeko.towns.core.townsettings.legacy.general;

import java.util.Map;

import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

import de.bloodeko.towns.core.townsettings.TownSettings;
import de.bloodeko.towns.core.townsettings.legacy.Setting;
import de.bloodeko.towns.core.townsettings.legacy.Settings;
import de.bloodeko.towns.core.townsettings.legacy.cmds.BooleanCmd;

public class PearlSetting extends Setting {

    @Override
    public String getId() {
        return "pearl";
    }

    /**
     * Reads the pearl flag and returns State.ALLOW
     * if it is allowed, or State.DENY or null else. 
     */
    @Override
    public Object read(Map<Object, Object> map) {
        return map.get(Flags.ENDERPEARL);
    }


    /**
     * Set State.ALLOW to allow the use of 
     * pearls or State.DENY otherwise.
     */
    @Override
    public void set(Map<Object, Object> map, Object obj) {
        map.put(Flags.ENDERPEARL, obj);
    }


    /**
     * Initializes the setting with State.DENY to
     * prevent the use of enderpearls.
     */
    @Override
    public void init(Map<Object, Object> map, Integer id) {
        map.put(Flags.ENDERPEARL, State.DENY);
    }


    @Override
    public String serialize(Map<Object, Object> map) {
        return serializeState(read(map));
    }

    @Override
    public void deserialize(Map<Object, Object> map, Object obj) {
        map.put(Flags.ENDERPEARL, deserializeState(obj));
    }
    
    
    public static class PearlCmd extends BooleanCmd {

        public PearlCmd(String name) {
            super(name, State.DENY, State.ALLOW);
        }
        
        @Override 
        public void setValue(TownSettings settings, State obj) {
            checkHasBought(settings, Settings.PEARL);
            Settings.PEARL.set(settings.getFlags(), obj);
        }
    }
}
