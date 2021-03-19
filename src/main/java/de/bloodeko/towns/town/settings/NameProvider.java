package de.bloodeko.towns.town.settings;

import de.bloodeko.towns.town.Town;

public interface NameProvider {
    public String display(Town town);
    public String getName();
    public int getPriority();
    public boolean isHidden();
}
