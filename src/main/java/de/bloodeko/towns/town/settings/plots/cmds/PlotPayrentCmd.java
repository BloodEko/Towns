package de.bloodeko.towns.town.settings.plots.cmds;

import org.bukkit.entity.Player;

import de.bloodeko.towns.town.settings.plots.RentService;

public class PlotPayrentCmd extends PlotBaseCmd {
    private RentService service;
    
    public PlotPayrentCmd(RentService service) {
        this.service = service;
    }
    
    @Override
    public void execute(Player player, String[] args) {
        service.payRents();
        player.sendMessage("Trigged rent paying.");
    }
}
