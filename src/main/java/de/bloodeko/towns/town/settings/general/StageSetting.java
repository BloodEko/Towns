package de.bloodeko.towns.town.settings.general;

import java.util.Map;

import de.bloodeko.towns.town.settings.Setting;
import de.bloodeko.towns.town.settings.Settings;

public class StageSetting extends Setting {

    @Override
    public String getId() {
        return "stage";
    }

    @Override
    public Object read(Map<Object, Object> map) {
        return map.get(Settings.STAGE);
    }

    @Override
    public void set(Map<Object, Object> map, Object obj) {
        map.put(Settings.STAGE, obj);
    }

    @Override
    public void init(Map<Object, Object> map) {
        map.put(Settings.STAGE, 1);
    }

    @Override
    public Object serialize(Map<Object, Object> map) {
        return read(map).toString();
    }

    @Override
    public void deserialize(Map<Object, Object> map, Object obj) {
        map.put(Settings.STAGE, Integer.valueOf(obj.toString()));
    }
}
