package de.bloodeko.towns.core.townsettings.legacy.general;

import java.util.Map;

import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

import de.bloodeko.towns.core.townsettings.TownSettings;
import de.bloodeko.towns.core.townsettings.legacy.Setting;
import de.bloodeko.towns.core.townsettings.legacy.Settings;
import de.bloodeko.towns.core.townsettings.legacy.cmds.BooleanCmd;

public class DamageAnimalsSetting extends Setting {

    @Override
    public String getId() {
        return "damageAnimals";
    }

    @Override
    public Object read(Map<Object, Object> map) {
        return map.get(Flags.DAMAGE_ANIMALS);
    }


    @Override
    public void set(Map<Object, Object> map, Object obj) {
        map.put(Flags.DAMAGE_ANIMALS, obj);
    }


    @Override
    public void init(Map<Object, Object> map, Integer id) {
        map.put(Flags.DAMAGE_ANIMALS, State.DENY);
    }


    @Override
    public Object serialize(Map<Object, Object> map) {
        return serializeState(read(map));
    }

    @Override
    public void deserialize(Map<Object, Object> map, Object obj) {
        map.put(Flags.DAMAGE_ANIMALS, deserializeState(obj));
    }
    
    
    public static class AnimalProtectCmd extends BooleanCmd {

        public AnimalProtectCmd(String name) {
            super(name, State.DENY, State.ALLOW);
        }
        
        @Override 
        public void setValue(TownSettings settings, State obj) {
            Settings.DAMAGE_ANIMALS.set(settings.getFlags(), obj);
        }
    }
}
