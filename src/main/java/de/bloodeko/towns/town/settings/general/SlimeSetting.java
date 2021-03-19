package de.bloodeko.towns.town.settings.general;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.sk89q.worldedit.world.entity.EntityType;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

import de.bloodeko.towns.cmds.settings.BooleanCmd;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.settings.NameProvider;
import de.bloodeko.towns.town.settings.Setting;
import de.bloodeko.towns.town.settings.Settings;
import de.bloodeko.towns.town.settings.TownSettings;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Util;

public class SlimeSetting extends Setting {
    private static final Flag<?> FLAG = Flags.DENY_SPAWN;
    private static final EntityType MOB = EntityType.REGISTRY.get("slime");

    @Override
    public String getId() {
        return "slime";
    }
    
    @SuppressWarnings("unchecked")
    @Override 
    public Object read(Map<Object, Object> map) {
        Object set = map.get(Flags.DENY_SPAWN);
        if (set == null) {
            return State.ALLOW;
        }
        boolean result = !((Set<EntityType>) set).contains(MOB);
        return Util.toState(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void set(Map<Object, Object> map, Object obj) {
        if (obj == State.ALLOW) {
            Object set = map.get(FLAG);
            if (set == null) {
                return;
            }
            ((Set<EntityType>) set).remove(MOB);
        } else {
            Object set = map.get(FLAG);
            if (set == null) {
                Set<EntityType> mobs = new HashSet<>();
                mobs.add(MOB);
                map.put(FLAG, mobs);
                return;
            }
            ((Set<EntityType>) set).add(MOB);
        }
    }

    @Override
    public void init(Map<Object, Object> map) {
        set(map, State.DENY);
    }
    
    @Override
    public String serialize(Map<Object, Object> map) {
        return serializeState(read(map));
    }

    @Override
    public void deserialize(Map<Object, Object> map, Object obj) {
        set(map, deserializeState(obj));
    }
    
    
    public static class SlimeCmd extends BooleanCmd {

        public SlimeCmd(ChunkMap map, String name) {
            super(map, name, State.ALLOW, State.DENY);
        }
        
        @Override
        public void setValue(TownSettings settings, State obj) {
            Settings.SLIME.set(settings.getFlags(), obj);
        }
    }
    
    
    public static class SlimeDisplay implements NameProvider {
        
        @Override
        public String display(Town town) {
            return Settings.SLIME.read(town.getSettings().getFlags()) + "";
        }

        @Override
        public String getName() {
            return Messages.get("settings.slime");
        }
        
        @Override
        public int getPriority() {
            return 1;
        }
        
        @Override
        public boolean isHidden() {
            return false;
        }
    }
}
