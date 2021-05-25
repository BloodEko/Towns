package de.bloodeko.towns.core.townsettings.legacy.general;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.core.townsettings.legacy.NameProvider;
import de.bloodeko.towns.core.townsettings.legacy.Setting;
import de.bloodeko.towns.core.townsettings.legacy.Settings;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Util;
import de.bloodeko.towns.util.cmds.CmdBase;

public class WarpSetting extends Setting {

    @Override
    public String getId() {
        return "warp";
    }

    @Override
    public Object read(Map<Object, Object> map) {
        return map.get(Settings.WARP);
    }

    @Override
    public void set(Map<Object, Object> map, Object obj) {
        map.put(Settings.WARP, obj);
    }

    @Override
    public void init(Map<Object, Object> map, Integer id) {
        map.put(Settings.WARP, "x");
    }
    
    @Override
    public Object serialize(Map<Object, Object> map) {
        Object obj = read(map);
        if (obj instanceof Location) {
            return serializeLocation((Location) obj);
        }
        return "x";
    }

    @Override
    public void deserialize(Map<Object, Object> map, Object obj) {
        if (obj.equals("x")) return;
        map.put(Settings.WARP, deserializeLocation((String) obj));
    }
    
    public static class WarpCmd extends CmdBase {
        private final String off = "!";

        @Override
        public void execute(Player player, String[] args) {
            if (args.length == 0) {
                Settings.WARP.set(getTown(player).getSettings().getFlags(), player.getLocation());
                Messages.say(player, "settings.warp.set");
            } else {
                Settings.WARP.set(getTown(player).getSettings().getFlags(), "x");
                Messages.say(player, "settings.warp.disabled");
            }
        }
        
        @Override
        public List<String> completeTab(String[] args) {
            return Util.filterList(Arrays.asList(off), args[0]);
        }
    }
    
    public static class WarpDisplay implements NameProvider {

        @Override
        public String display(Town town) {
            Object obj = Settings.WARP.read(town.getSettings().getFlags());
            if (obj instanceof Location) {
                Location loc = (Location) obj;
                return "(" + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ")";
            }
            return "--";
        }

        @Override
        public String getName() {
            return Messages.get("settings.warp");
        }
        
        @Override
        public int getPriority() {
            return 3;
        }

        @Override
        public boolean isHidden() {
            return false;
        }
    }
}
