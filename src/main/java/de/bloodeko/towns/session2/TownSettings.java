package de.bloodeko.towns.session2;

public class TownSettings {
    private boolean canClaim;
    private boolean canBuild;
    
    public TownSettings(boolean canClaim, boolean canBuild) {
        this.canClaim = canClaim;
        this.canBuild = canBuild;
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
}
