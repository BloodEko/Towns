package de.bloodeko.towns.core.townsettings.legacy.general;

import java.util.Map;

import de.bloodeko.towns.core.townsettings.legacy.Setting;
import de.bloodeko.towns.core.townsettings.legacy.Settings;

public class NameSetting extends Setting {

    @Override
    public String getId() {
        return "name";
    }

    @Override
    public Object read(Map<Object, Object> map) {
        return map.get(Settings.NAME);
    }

    @Override
    public void set(Map<Object, Object> map, Object obj) {
        map.put(Settings.NAME, obj);
    }

    @Override
    public void init(Map<Object, Object> map) {
    }

    @Override
    public Object serialize(Map<Object, Object> map) {
        return read(map).toString();
    }

    @Override
    public void deserialize(Map<Object, Object> map, Object value) {
        map.put(Settings.NAME, value);
    }
}
