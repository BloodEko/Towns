package de.bloodeko.towns.cmds.core;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.TownRegistry;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Messages;
import net.milkbowl.vault.economy.Economy;

public class FoundCmd extends CmdBase {
    private static int price = 1000;
    private TownRegistry registry;
    private Economy economy;
    
    public FoundCmd(ChunkMap map, TownRegistry registry, Economy economy) {
        super(map);
        this.registry = registry;
        this.economy = economy;
    }

    @Override
    public void execute(Player player, String[] args) {
        checkMoney(economy, player, price);
        String name = getArg(0, args, "cmds.found.needName");
        registry.foundTown(Chunk.fromEntity(player), name, player.getUniqueId());
        economy.withdrawPlayer(player, price);
        Messages.say(player, "cmds.found.created", name);
    }
}
