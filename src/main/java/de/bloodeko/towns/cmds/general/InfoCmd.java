package de.bloodeko.towns.cmds.general;

import java.util.Map.Entry;
import java.util.StringJoiner;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.cmds.settings.TownSetting;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.TownPeople;
import de.bloodeko.towns.util.Messages;

public class InfoCmd extends CmdBase {
    private final String separator = Messages.get("cmds.info.separator");
    private final String resetColor = Messages.get("cmds.info.resetColor");
    private final String boldColor = Messages.get("cmds.info.boldColor");
    private final String onlineColor = Messages.get("cmds.info.onlineColor");
    
    public InfoCmd(ChunkMap map) {
        super(map);
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
        Messages.say(player, "cmds.info.builders", getBuilders(town.getPeople()));
        
        Messages.say(player, "cmds.info.areaHeader");
        Messages.say(player, "cmds.info.minX", town.getArea().getSides().minX);
        Messages.say(player, "cmds.info.minZ", town.getArea().getSides().minZ);
        Messages.say(player, "cmds.info.maxX", town.getArea().getSides().maxX);
        Messages.say(player, "cmds.info.maxZ", town.getArea().getSides().maxZ);

        Messages.say(player, "cmds.info.settingsHeader");
        for (Entry<TownSetting, Object> entry : town.getSettings().getSettings().entrySet()) {
            TownSetting setting = entry.getKey();
            Messages.say(player, "cmds.info.setting", setting.getDisplay(),
              setting.display(entry.getValue()));
        }
    }
    
    private String getGovernors(TownPeople people) {
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
    
    private String getBuilders(TownPeople people) {
        StringJoiner joiner = new StringJoiner(separator);
        for (UUID uuid : people.getBuilders()) {
            joiner.add(withPrefix(uuid, false));
        }
        return joiner.toString();
    }
    
    private String withPrefix(UUID uuid, boolean bold) {
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
