package de.bloodeko.towns.core.towns.ui;

import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.townpeople.TownPeople;
import de.bloodeko.towns.core.towns.legacy.Town;
import de.bloodeko.towns.core.townsettings.legacy.AdvancedSetting;
import de.bloodeko.towns.core.townsettings.ui.SettingsCmd;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.cmds.CmdBase;

public class InfoCmd extends CmdBase {
    private static final String separator = Messages.get("cmds.info.separator");
    private static final String boldColor = Messages.get("cmds.info.boldColor");
    private static final String onlineColor = Messages.get("cmds.info.onlineColor");
    
    @Override
    public void execute(Player player, String[] args) {
        Town town = getTownAsPlayer(player);
        showCore(player, town);
        showPeople(player, town.getPeople());
        showSettings(player, town);
        showFooter(player);
    }
    
    private void showCore(Player player, Town town) {
        Messages.say(player, "cmds.info.townHeader");
        Messages.say(player, "cmds.info.id", town.getId());
        Messages.say(player, "cmds.info.name", town.getSettings().getName());
        Messages.say(player, "cmds.info.stage", town.getSettings().getStage().getStage());
        Messages.say(player, "cmds.info.size", town.getArea().getSize());
    }
    
    
    private void showPeople(Player player, TownPeople people) {
        Messages.say(player, "cmds.info.peopleHeader");
        Messages.say(player, "cmds.info.governors", prefixed(people.getGovernors(), people.getOwner()));
        Messages.say(player, "cmds.info.builders", prefixed(people.getBuilders()));
    }
    
    
    private void showSettings(Player player, Town town) {
        List<AdvancedSetting> list = SettingsCmd.getSettings(Services.settings(), town);
        if (list.isEmpty()) {
            return;
        }
        Messages.say(player, "cmds.info.settingsHeader");
        StringJoiner joiner = new StringJoiner(separator);
        for (AdvancedSetting setting : list) {
            joiner.add(setting.names.getName());
        }
        Messages.say(player, "cmds.info.settings", joiner.toString());
    }
    
    
    private void showFooter(Player player) {
        Messages.say(player, "cmds.info.footer");
    }
    
    /**
     * Modifies the set to remove the owner. Returns the collection
     * formatted with bold/online/offline color.
     */
    public static String prefixed(Set<UUID> set, UUID owner) {
        if (set.isEmpty()) {
            return Messages.get("cmds.info.emptyValue");
        }
        StringJoiner joiner = new StringJoiner(separator);
        if (owner != null) {
            joiner.add(getBoldName(Bukkit.getOfflinePlayer(owner)));
            set.remove(owner);
        }
        return prefixed(joiner, set);
    }
    
    /**
     * Returns the set players formatted with online/offline
     * colors, separated by the default joiner.
     */
    public static String prefixed(Set<UUID> set) {
        return prefixed(set, null);
    }
    
    /**
     * Returns the set players formatted with online/offline
     * colors, separated by the joiner.
     */
    public static String prefixed(StringJoiner joiner, Set<UUID> set) {
        for (UUID uuid : set) {
            joiner.add(getOnlineName(Bukkit.getOfflinePlayer(uuid)));
        }
        return joiner.toString();
    }
    
    /**
     * Returns the name for this player.
     */
    public static String getName(OfflinePlayer player) {
        String name = player.getName();
        if (name == null) {
            return player.getUniqueId().toString();
        }
        return name;
    }
    
    /**
     * Returns the name for this player with online
     * color applied, when online.
     */
    public static String getOnlineName(OfflinePlayer player) {
        String name = getName(player);
        if (player.isOnline()) {
            return onlineColor + name;
        }
        return name;
    }
    
    /**
     * Returns the name for this player with bold color.
     * And online color applied, when online.
     */
    public static String getBoldName(OfflinePlayer player) {
        String name = getName(player);
        if (player.isOnline()) {
            return onlineColor + boldColor + name;
        }
        return boldColor + name;
    }
}
