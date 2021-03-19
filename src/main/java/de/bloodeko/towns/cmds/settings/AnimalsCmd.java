package de.bloodeko.towns.cmds.settings;

import de.bloodeko.towns.cmds.BooleanSetting;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.TownSettings;

public class AnimalsCmd extends BooleanSetting {
    
    public AnimalsCmd(ChunkMap map, String name) {
        super(map, name);
    }
    
    @Override
    public void setValue(TownSettings settings, boolean value) {
        //settings.setAnimalKilling(value);
    }
    
    @Override
    public boolean readValue(TownSettings settings) {
        return false;
        //return settings.canKillAnimals();
    }
}
