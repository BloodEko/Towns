package de.bloodeko.towns.core.townplots.ui;

import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;

public class PlotPayrentCmd extends PlotBaseCmd {
    
    @Override
    public void execute(Player player, String[] args) {
        Services.plotservice().payRents();
        player.sendMessage("Trigged rent paying.");
    }
}
