package de.bloodeko.towns.town.settings.stage.cmds;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.settings.Settings;

/**
 * Let the town rise to the next stage if all
 * requirements for that stage are matched.
 */
public class RiseCmd extends CmdBase {

    @Override
    public void execute(Player player, String[] args) {
        Town town = getTown(player);
        int stage = (int) Settings.STAGE.read(town.getSettings().getFlags());
        Settings.STAGE.set(town.getSettings().getFlags(), stage++);
        player.sendMessage("Rised the stage to: " + stage);
    }
}
