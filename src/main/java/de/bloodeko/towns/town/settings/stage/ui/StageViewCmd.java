package de.bloodeko.towns.town.settings.stage.ui;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.settings.stage.StageFactory;
import de.bloodeko.towns.town.settings.stage.domain.Stage;
import de.bloodeko.towns.util.ModifyException;

/**
 * Opens a GUI and shows all items that are required
 * for reaching the next stage.
 */
public class StageViewCmd extends CmdBase {
    private InventoyListener listener;
    
    public StageViewCmd(InventoyListener listener) {
        this.listener = listener;
    }
    
    @Override
    public void execute(Player player, String[] args) {
        Town town = getTown(player);
        Stage stage = town.getSettings().getStage();
        
        if (stage.getStage() >= Stage.MAX_STAGE) {
            throw new ModifyException("settings.stage.townAlreadyMaxStage");
        }
        
        StageView view = StageFactory.newView(stage);
        listener.add(player.getUniqueId(), view);
        view.open(player, StageFactory.newMapInventory());
    }
}
