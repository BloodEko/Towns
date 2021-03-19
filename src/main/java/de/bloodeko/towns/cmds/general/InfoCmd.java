package de.bloodeko.towns.cmds.general;

import java.util.Map.Entry;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.people.TownPeople;
import de.bloodeko.towns.town.settings.Setting;
import de.bloodeko.towns.town.settings.SettingsRegistry;
import de.bloodeko.towns.town.settings.SettingsRegistry.RegisteredSetting;
import de.bloodeko.towns.util.Messages;

public class InfoCmd extends CmdBase {
    private static final String separator = Messages.get("cmds.info.separator");
    private static final String resetColor = Messages.get("cmds.info.resetColor");
    private static final String boldColor = Messages.get("cmds.info.boldColor");
    private static final String onlineColor = Messages.get("cmds.info.onlineColor");
    private SettingsRegistry registry;
    
    public InfoCmd(ChunkMap map, SettingsRegistry registry) {
        super(map);
        this.registry = registry;
    }
    
    @Override
    public void execute(Player player, String[] args) {
        printInfo(getTownAsPlayer(player), player);
    }
    
    public void printInfo(Town town, Player player) {
        Messages.say(player, "cmds.info.townHeader");
        Messages.say(player, "cmds.info.name", town.getSettings().getName(), town.getId());
        Messages.say(player, "cmds.info.stage", town.getSettings().getStage());
        Messages.say(player, "cmds.info.size", town.getArea().getSize());

        Messages.say(player, "cmds.info.peopleHeader");
        Messages.say(player, "cmds.info.governors", getGovernors(town.getPeople()));
        Messages.say(player, "cmds.info.builders", getBuilders(town.getPeople().getBuilders()));
        
        Messages.say(player, "cmds.info.areaHeader");
        Messages.say(player, "cmds.info.minX", town.getArea().getSides().minX);
        Messages.say(player, "cmds.info.minZ", town.getArea().getSides().minZ);
        Messages.say(player, "cmds.info.maxX", town.getArea().getSides().maxX);
        Messages.say(player, "cmds.info.maxZ", town.getArea().getSides().maxZ);

        Messages.say(player, "cmds.info.settingsHeader");
        for (Entry<Setting, Object> entry : town.getSettings().getSettings().entrySet()) {
            printFullInfo(player, town, entry.getKey(), entry.getValue());
        }
    }
    
    private void printFullInfo(Player player, Town town, Setting setting, Object obj) {
        RegisteredSetting ui = registry.fromId(setting.getId());
        String name;
        Object val;
        if (ui == null) {
            name = setting.getId();
            val = obj;
        } else {
            name = ui.display;
            val = ui.display(town, obj);
        }
        Messages.say(player, "cmds.info.setting", name, val);
    }
    
    public static String getGovernors(TownPeople people) {
        StringJoiner joiner = new StringJoiner(separator);
        for (UUID uuid : people.getGovernors()) {
            if (people.isOwner(uuid)) {
                joiner.add(withPrefix(uuid, true));
            } else {
                joiner.add(withPrefix(uuid, false));
            }
        }
        return joiner.toString();
    }
    
    public static String getBuilders(Set<UUID> set) {
        StringJoiner joiner = new StringJoiner(separator);
        for (UUID uuid : set) {
            joiner.add(withPrefix(uuid, false));
        }
        return joiner.toString();
    }
    
    public static String withPrefix(UUID uuid, boolean bold) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        String val = player.getName();
        if (val == null) {
            val = player.getUniqueId().toString();
        }
        val += resetColor;
        if (bold) {
            val = boldColor + val;
        }
        if (player.isOnline()) {
            val = onlineColor + val;
        }
        return val;
    }
}
