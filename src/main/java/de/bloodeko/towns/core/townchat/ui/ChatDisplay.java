package de.bloodeko.towns.core.townchat.ui;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.core.townsettings.legacy.NameProvider;
import de.bloodeko.towns.util.Messages;

/**
 * Displays the chat setting to governors,
 * to list settings and values.
 */
public class ChatDisplay implements NameProvider {

    @Override
    public String display(Town town) {
        String suffix = Messages.get("settings.chat.display.suffix");
        return Services.townchat().getPlayers(town.getId()).size() + suffix;
    }

    @Override
    public String getName() {
        return Messages.get("settings.chat");
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean isHidden() {
        return false;
    }
}
