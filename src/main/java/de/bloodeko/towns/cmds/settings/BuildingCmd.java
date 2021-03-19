package de.bloodeko.towns.cmds.settings;

import de.bloodeko.towns.cmds.BooleanSetting;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.TownSettings;

public class BuildingCmd extends BooleanSetting {
    
    public BuildingCmd(ChunkMap map, String name) {
        super(map, name);
    }
    
    @Override
    public void setValue(TownSettings settings, boolean value) {
        settings.setBuilding(value);
    }
    
    @Override
    public boolean readValue(TownSettings settings) {
        return settings.canBuild();
    }
}
