package de.bloodeko.towns.town.settings;

import de.bloodeko.towns.town.Town;

public interface BuyCondition {
    public boolean canBuy(Town town);
}
