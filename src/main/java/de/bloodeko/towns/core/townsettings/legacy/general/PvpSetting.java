package de.bloodeko.towns.core.townsettings.legacy.general;

import java.util.Map;

import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

import de.bloodeko.towns.core.townsettings.TownSettings;
import de.bloodeko.towns.core.townsettings.legacy.Setting;
import de.bloodeko.towns.core.townsettings.legacy.Settings;
import de.bloodeko.towns.core.townsettings.legacy.cmds.BooleanCmd;

public class PvpSetting extends Setting {

    @Override
    public String getId() {
        return "pvp";
    }

    @Override
    public Object read(Map<Object, Object> map) {
        return map.get(Flags.PVP);
    }

    @Override
    public void set(Map<Object, Object> map, Object obj) {
        map.put(Flags.PVP, obj);
    }

    @Override
    public void init(Map<Object, Object> map, Integer id) {
        map.put(Flags.PVP, State.DENY);
    }

    @Override
    public String serialize(Map<Object, Object> map) {
        return serializeState(read(map));
    }

    @Override
    public void deserialize(Map<Object, Object> map, Object obj) {
        map.put(Flags.PVP, deserializeState(obj));
    }
    
    
    public static class PvpProtectCmd extends BooleanCmd {

        public PvpProtectCmd(String name) {
            super(name, State.DENY, State.ALLOW);
        }
        
        @Override 
        public void setValue(TownSettings settings, State obj) {
            checkHasBought(settings, Settings.PVP);
            Settings.PVP.set(settings.getFlags(), obj);
        }
    }
}
