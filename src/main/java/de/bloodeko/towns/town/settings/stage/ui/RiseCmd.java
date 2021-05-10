package de.bloodeko.towns.town.settings.stage.ui;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.settings.stage.domain.Stage;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;

/**
 * Let the town rise to the next stage if all
 * requirements for that stage are matched.
 */
public class RiseCmd extends CmdBase {

    @Override
    public void execute(Player player, String[] args) {
        Town town = getTown(player);
        Stage stage = town.getSettings().getStage();
        
        if (stage.getStage() >= Stage.MAX_STAGE) {
            throw new ModifyException("settings.stage.townAlreadyMaxStage");
        }
        
        stage.riseStage();
        Messages.say(player, "cmds.stage.risedToStage", town.getSettings().getName(), stage.getStage());
        spawnFireWork(player.getLocation());
    }
    
    private void spawnFireWork(Location loc) {
        loc = loc.clone().add(0, 5, 0);
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        fwm.setPower(2);
        fwm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());
        fw.setFireworkMeta(fwm);
        fw.detonate();
    }
}
