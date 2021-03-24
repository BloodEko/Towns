package de.bloodeko.towns.town.settings.general;

import java.util.Map;

import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.settings.Setting;
import de.bloodeko.towns.town.settings.Settings;
import de.bloodeko.towns.town.settings.TownSettings;
import de.bloodeko.towns.town.settings.cmds.BooleanCmd;

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
    public void init(Map<Object, Object> map) {
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

        public AnimalProtectCmd(ChunkMap map, String name) {
            super(map, name, State.ALLOW, State.DENY);
        }
        
        @Override 
        public void setValue(TownSettings settings, State obj) {
            Settings.DAMAGE_ANIMALS.set(settings.getFlags(), obj);
        }
    }
}
