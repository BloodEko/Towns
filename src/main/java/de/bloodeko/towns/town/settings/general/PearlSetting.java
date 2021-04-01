package de.bloodeko.towns.town.settings.general;

import java.util.Map;

import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

import de.bloodeko.towns.town.settings.Setting;
import de.bloodeko.towns.town.settings.Settings;
import de.bloodeko.towns.town.settings.TownSettings;
import de.bloodeko.towns.town.settings.cmds.BooleanCmd;

public class PearlSetting extends Setting {

    @Override
    public String getId() {
        return "pearl";
    }

    @Override
    public Object read(Map<Object, Object> map) {
        return map.get(Flags.ENDERPEARL);
    }


    @Override
    public void set(Map<Object, Object> map, Object obj) {
        map.put(Flags.ENDERPEARL, obj);
    }


    @Override
    public void init(Map<Object, Object> map) {
        map.put(Flags.ENDERPEARL, State.DENY);
    }


    @Override
    public Object serialize(Map<Object, Object> map) {
        return serializeState(read(map));
    }

    @Override
    public void deserialize(Map<Object, Object> map, Object obj) {
        map.put(Flags.ENDERPEARL, deserializeState(obj));
    }
    
    
    public static class PearlCmd extends BooleanCmd {

        public PearlCmd(String name) {
            super(name, State.ALLOW, State.DENY);
        }
        
        @Override 
        public void setValue(TownSettings settings, State obj) {
            Settings.PEARL.set(settings.getFlags(), obj);
        }
    }
}
