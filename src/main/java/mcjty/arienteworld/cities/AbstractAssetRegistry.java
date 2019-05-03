package mcjty.arienteworld.cities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbstractAssetRegistry<T extends IAsset> {

    private final Map<String, T> assets = new HashMap<>();
    private final List<String> assetNames = new ArrayList<>();

    public void register(T building) {
        assets.put(building.getName(), building);
        assetNames.add(building.getName());
    }

    public void put(String name, T newversion) {
        assets.put(name, newversion);
    }

    public T get(String name) {
        if (name == null) {
            return null;
        }
        return assets.get(name);
    }

    public T get(int i) {
        return assets.get(assetNames.get(i));
    }

    public Iterable<T> getIterable() {
        return assets.values();
    }

    public int getCount() {
        return assets.size();
    }

    public String getName(int i) {
        return assetNames.get(i);
    }

    public void reset() {
        assets.clear();
    }
}
