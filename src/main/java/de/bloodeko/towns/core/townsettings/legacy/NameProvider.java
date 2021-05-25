package de.bloodeko.towns.core.townsettings.legacy;

import de.bloodeko.towns.core.towns.Town;

public interface NameProvider {
    public String display(Town town);
    public String getName();
    public int getPriority();
    public boolean isHidden();
}
