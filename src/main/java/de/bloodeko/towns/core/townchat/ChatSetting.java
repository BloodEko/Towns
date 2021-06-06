package de.bloodeko.towns.core.townchat;

import java.util.Map;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.townsettings.legacy.Setting;

/**
 * Add a chat channel to towns. Members of the town like governors,
 * builders, renters, and sub-renters are automatically added to it.
 * The governors can add and remove people from the channel.
 */
public class ChatSetting extends Setting {
    public static final Setting VALUE = new ChatSetting();
    
    @Override
    public String getId() {
        return "chat";
    }

    @Override
    public Object read(Map<Object, Object> map) {
        throw new IllegalStateException();
    }

    @Override
    public void set(Map<Object, Object> map, Object obj) {
        throw new IllegalStateException();
    }

    @Override
    public void init(Map<Object, Object> map, Integer id) {
        Services.townchat().initTown(id);
    }

    @Override
    public String serialize(Map<Object, Object> map) {
        return "x";
    }

    @Override
    public void deserialize(Map<Object, Object> map, Object obj) {
    }
}
