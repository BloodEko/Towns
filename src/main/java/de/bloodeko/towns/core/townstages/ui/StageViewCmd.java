package de.bloodeko.towns.core.townstages.ui;

import org.bukkit.entity.Player;

import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.core.townstages.StageFactory;
import de.bloodeko.towns.core.townstages.domain.Stage;
import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.cmds.CmdBase;

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
        Stage stage = town.getStage();
        
        if (stage.getValue() >= Stage.MAX_STAGE) {
            throw new ModifyException("settings.stage.townAlreadyMaxStage");
        }
        
        StageView view = StageFactory.newView(stage);
        listener.add(player.getUniqueId(), view);
        view.open(player, StageFactory.newMapInventory());
    }
}
