package de.bloodeko.towns.town;

public class TownSettings {
    private boolean canClaim;
    private boolean canBuild;
    private boolean canKillAnimals;
    
    public TownSettings() {
    }
    
    public void setClaiming(boolean value) {
        canClaim = value;
    }
    
    public void setBuilding(boolean value) {
        canBuild = value;
    }
    
    public boolean canClaim() {
        return canClaim;
    }
    
    public boolean canBuild() {
        return canBuild;
    }

    public void setAnimalKilling(boolean value) {
        canKillAnimals = value;
    }
    
    public boolean canKillAnimals() {
        return canKillAnimals;
    }
}
