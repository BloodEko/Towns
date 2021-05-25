package de.bloodeko.towns.core.towns;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.townarea.ChunkArea;
import de.bloodeko.towns.core.townpeople.TownPeople;
import de.bloodeko.towns.core.townplots.PlotHandler;
import de.bloodeko.towns.core.townsettings.TownSettings;
import de.bloodeko.towns.core.townstages.domain.Stage;

public class Town {
    private final int id;
    
    Town(int id) {
        this.id = id;
    }
    
    /**
     * Returns the id for that town.
     */
    public int getId() {
        return id;
    }
    
    public TownSettings getSettings() {
        return Services.settingsservice().getSettings(id);
    }
    
    public ChunkArea getArea() {
        return Services.chunkservice().getArea(id);
    }
    
    public TownPeople getPeople() {
        return Services.peopleservice().getPeople(id);
    }

    public String getName() {
        return Services.nameservice().getName(id);
    }
    
    public Stage getStage() {
        return Services.stageservice().get(id);
    }
    
    /**
     * Returns the towns plots, or null.
     */
    public PlotHandler getPlots() {
        return Services.plotservice().getPlots(id);
    }
}
