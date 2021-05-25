package de.bloodeko.towns.core;

import java.util.UUID;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.townarea.TownRegion;
import de.bloodeko.towns.core.townpeople.TownPeople;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.core.townsettings.TownSettings;
import de.bloodeko.towns.core.townstages.StageFactory;
import de.bloodeko.towns.util.Chunk;

public class TownFactory {
    
    /**
     * Creates a town for that name at the location and registers
     * it to all known services, to gain functionality.
     * Throws an exception if the arguments don't allow creation.
     */
    public static Town createTown(String name, Chunk chunk, UUID owner) {
        verfiyCreation(name, chunk);
        
        Town town = Services.townservice().createTown();
        Integer id = town.getId();
        
        Services.chunkservice().set(chunk, id);
        TownRegion region = Services.chunkservice().getRegion(id);
        
        Services.peopleservice().put(id, new TownPeople(owner, region));
        Services.settingsservice().put(id, new TownSettings(region));
        Services.stageservice().put(id, StageFactory.getStartStage());
        Services.nameservice().rename(id, name);
        Services.regionmanager().addRegion(region);
        
        region.setFlagNames(Services.nameservice().getName(id));
        return town;
    }
    
    /**
     * Throws an exception if the creation of a town with 
     * these arguments would be result in inconsistent state.
     */
    private static void verfiyCreation(String name, Chunk chunk) {
        Services.nameservice().verify(name);
        Services.chunkservice().verify(chunk);
    }
    
    /**
     * Removes a town with that id from all known services,
     * to guarantee no memory leaks will be created.
     * Throws an exception if the town doesn't allow deletion.
     */
    public static void destroyTown(Integer id) {
        verfiyDeletion(id);
        Services.townservice().remove(id);
        
        TownRegion region = Services.chunkservice().getRegion(id);
        Services.chunkservice().removeAll(id);
        
        Services.peopleservice().remove(id);
        Services.settingsservice().remove(id);
        Services.stageservice().remove(id);
        Services.nameservice().remove(id);
        Services.regionmanager().removeRegion(region.getId());
        Services.plotservice().remove(id);
    }
    
    /**
     * Throws an exception if the town can't be deleted safely.
     */
    private static void verfiyDeletion(Integer id) {
        Services.plotservice().verfiyDeletion(id);
    }
}
