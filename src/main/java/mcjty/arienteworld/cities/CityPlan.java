package mcjty.arienteworld.cities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import mcjty.lib.varia.WeightedRandom;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityPlan implements IAsset {

    private String name;
    private String palette;
    private boolean isCity = true;
    private Map<Character, PartPalette> partPalette = new HashMap<>();
    private List<String> cellar = new ArrayList<>();
    private List<String> plan = new ArrayList<>();
    private List<String> layer2 = new ArrayList<>();
    private List<String> top = new ArrayList<>();
    private boolean underground = false;
    private boolean floating = false;

    private final Map<String, Integer> variants = new HashMap<>();

    private List<Loot> loot = new ArrayList<>();
    private WeightedRandom<Loot> randomLoot = null;

    private int minSentinels = 0;
    private int maxSentinels = 0;
    private int sentinelDistance = 20;
    private int sentinelRelHeight = 14;

    private int dronesMinimum1 = 1;
    private int dronesMinimum2 = 1;
    private int dronesMinimumN = 2;
    private int dronesWaveMax1 = 2;
    private int dronesWaveMax2 = 3;
    private int dronesWaveMaxN = 5;
    private int droneHeightOffset = 50;

    private double masterChance = 0.0;
    private double powerArmorChance = 0.0;
    private double forcefieldChance = 0.0;
    private int soldiersMinimum1 = 1;
    private int soldiersMinimum2 = 1;
    private int soldiersMinimumN = 2;
    private int soldiersWaveMax1 = 2;
    private int soldiersWaveMax2 = 3;
    private int soldiersWaveMaxN = 5;

    public CityPlan(JsonObject object) {
        readFromJSon(object);
    }

    public CityPlan(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public Map<Character, PartPalette> getPartPalette() {
        return partPalette;
    }

    public boolean isUnderground() {
        return underground;
    }

    public boolean isFloating() {
        return floating;
    }

    public boolean isCity() {
        return isCity;
    }

    public List<String> getPlan() {
        return plan;
    }

    public List<String> getCellar() {
        return cellar;
    }

    public List<String> getLayer2() {
        return layer2;
    }

    public List<String> getTop() {
        return top;
    }

    public String getPalette() {
        return palette;
    }

    public int getMinSentinels() {
        return minSentinels;
    }

    public int getMaxSentinels() {
        return maxSentinels;
    }

    public int getSentinelDistance() {
        return sentinelDistance;
    }

    public int getSentinelRelHeight() {
        return sentinelRelHeight;
    }

    public int getDronesMinimum1() {
        return dronesMinimum1;
    }

    public int getDronesMinimum2() {
        return dronesMinimum2;
    }

    public int getDronesMinimumN() {
        return dronesMinimumN;
    }

    public int getDronesWaveMax1() {
        return dronesWaveMax1;
    }

    public int getDronesWaveMax2() {
        return dronesWaveMax2;
    }

    public int getDronesWaveMaxN() {
        return dronesWaveMaxN;
    }

    public int getDroneHeightOffset() {
        return droneHeightOffset;
    }

    public int getSoldiersMinimum1() {
        return soldiersMinimum1;
    }

    public int getSoldiersMinimum2() {
        return soldiersMinimum2;
    }

    public int getSoldiersMinimumN() {
        return soldiersMinimumN;
    }

    public int getSoldiersWaveMax1() {
        return soldiersWaveMax1;
    }

    public int getSoldiersWaveMax2() {
        return soldiersWaveMax2;
    }

    public int getSoldiersWaveMaxN() {
        return soldiersWaveMaxN;
    }

    public double getMasterChance() {
        return masterChance;
    }

    public double getPowerArmorChance() {
        return powerArmorChance;
    }

    public double getForcefieldChance() {
        return forcefieldChance;
    }

    public Map<String, Integer> getVariants() {
        return variants;
    }

    public List<Loot> getLoot() {
        return loot;
    }

    public WeightedRandom<Loot> getRandomLoot() {
        if (randomLoot == null) {
            randomLoot = new WeightedRandom<>();
            for (Loot l : loot) {
                randomLoot.add(l, l.getChance());
            }
        }
        return randomLoot;
    }

    @Override
    public void readFromJSon(JsonObject object) {
        name = object.get("name").getAsString();
        isCity = object.has("city") ? object.get("city").getAsBoolean() : true;
        palette = object.get("palette").getAsString();
        minSentinels = getMin(object, "sentinels", 0);
        maxSentinels = getMax(object, "sentinels", 0);
        sentinelDistance = object.get("sentinelDistance").getAsInt();
        sentinelRelHeight = object.get("sentinelRelHeight").getAsInt();
        dronesMinimum1 = object.get("dronesMinimum1").getAsInt();
        dronesMinimum2 = object.get("dronesMinimum2").getAsInt();
        dronesMinimumN = object.get("dronesMinimumN").getAsInt();
        dronesWaveMax1 = object.get("dronesWaveMax1").getAsInt();
        dronesWaveMax2 = object.get("dronesWaveMax2").getAsInt();
        dronesWaveMaxN = object.get("dronesWaveMaxN").getAsInt();
        if (object.has("droneHeightOffset")) {
            droneHeightOffset = object.get("droneHeightOffset").getAsInt();
        } else {
            droneHeightOffset = 50;
        }
        soldiersMinimum1 = object.get("soldiersMinimum1").getAsInt();
        soldiersMinimum2 = object.get("soldiersMinimum2").getAsInt();
        soldiersMinimumN = object.get("soldiersMinimumN").getAsInt();
        soldiersWaveMax1 = object.get("soldiersWaveMax1").getAsInt();
        soldiersWaveMax2 = object.get("soldiersWaveMax2").getAsInt();
        soldiersWaveMaxN = object.get("soldiersWaveMaxN").getAsInt();
        masterChance = object.get("masterChance").getAsDouble();
        powerArmorChance = object.get("powerArmorChance").getAsDouble();
        forcefieldChance = object.get("forcefieldChance").getAsDouble();
        if (object.has("underground")) {
            underground = object.get("underground").getAsBoolean();
        } else {
            underground = false;
        }
        if (object.has("floating")) {
            floating = object.get("floating").getAsBoolean();
        } else {
            floating = false;
        }

        parseLoot(object.get("loot").getAsJsonArray());
        parseVariants(object);
        parsePaletteArray(object.get("partpalette").getAsJsonArray());

        parsePlan(object, "plan", plan);
        parsePlan(object, "cellar", cellar);
        parsePlan(object, "layer2", layer2);
        parsePlan(object, "top", top);
    }

    private void parseVariants(JsonObject object) {
        variants.clear();
        if (object.has("variants")) {
            JsonArray array = object.get("variants").getAsJsonArray();
            for (JsonElement element : array) {
                JsonObject o = element.getAsJsonObject();
                String name = o.get("name").getAsString();
                Integer count = o.get("count").getAsInt();
                variants.put(name, count);
            }
        }
    }

    private int getMin(JsonObject object, String tag, int def) {
        if (object.has(tag)) {
            String value = object.get(tag).getAsString();
            String[] split = StringUtils.split(value, '-');
            return Integer.parseInt(split[0]);
        }
        return def;
    }

    private int getMax(JsonObject object, String tag, int def) {
        if (object.has(tag)) {
            String value = object.get(tag).getAsString();
            String[] split = StringUtils.split(value, '-');
            if (split.length > 1) {
                return Integer.parseInt(split[1]);
            } else {
                return Integer.parseInt(split[0]);
            }
        }
        return def;
    }

    private void parsePlan(JsonObject object, String name, List<String> plan) {
        plan.clear();
        if (object.has(name)) {
            JsonArray planArray = object.get(name).getAsJsonArray();
            for (JsonElement element : planArray) {
                String slice = element.getAsString();
                plan.add(slice);
            }
        }
    }

    public void parseLoot(JsonArray lootArray) {
        loot.clear();
        for (JsonElement element : lootArray) {
            JsonObject o = element.getAsJsonObject();
            String id;
            if (o.has("id")) {
                id = o.get("id").getAsString();
            } else {
                id = null;  // Random blueprint
            }
            int meta = 0;
            if (o.has("meta")) {
                meta = o.get("meta").getAsInt();
            }
            boolean blueprint = false;
            if (o.has("blueprint")) {
                blueprint = o.get("blueprint").getAsBoolean();
            }
            float chance = o.get("chance").getAsFloat();
            int maxAmount = o.get("max").getAsInt();
            if (id == null) {
                loot.add(new Loot(null, meta, blueprint, maxAmount, chance));
            } else {
                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
                if (item != null) {
                    loot.add(new Loot(new ResourceLocation(id), meta, blueprint, maxAmount, chance));
                }
            }
        }
    }

    public void parsePaletteArray(JsonArray paletteArray) {
        for (JsonElement element : paletteArray) {
            JsonObject o = element.getAsJsonObject();
            Object value = null;
            Character c = o.get("char").getAsCharacter();
            if (o.has("parts")) {
                JsonArray array = o.get("parts").getAsJsonArray();

                PartPalette parts = new PartPalette();
                for (JsonElement el : array) {
                    String part = el.getAsString();
                    parts.getPalette().add(part);
                }

                if (o.has("variant")) {
                    parts.setVariant(o.get("variant").getAsString());
                }

                partPalette.put(c, parts);
            } else {
                throw new RuntimeException("Illegal palette!");
            }
        }
    }

    public JsonObject writeToJSon() {
        JsonObject object = new JsonObject();
        object.add("type", new JsonPrimitive("plan"));
        object.add("name", new JsonPrimitive(name));
        object.add("city", new JsonPrimitive(isCity));
        object.add("palette", new JsonPrimitive(palette));
        object.add("sentinels", new JsonPrimitive(minSentinels + "-" + maxSentinels));
        object.add("sentinelDistance", new JsonPrimitive(sentinelDistance));
        object.add("sentinelRelHeight", new JsonPrimitive(sentinelRelHeight));
        object.add("dronesMinimum1", new JsonPrimitive(dronesMinimum1));
        object.add("dronesMinimum2", new JsonPrimitive(dronesMinimum2));
        object.add("dronesMinimumN", new JsonPrimitive(dronesMinimumN));
        object.add("dronesWaveMax1", new JsonPrimitive(dronesWaveMax1));
        object.add("dronesWaveMax2", new JsonPrimitive(dronesWaveMax2));
        object.add("dronesWaveMaxN", new JsonPrimitive(dronesWaveMaxN));
        object.add("droneHeightOffset", new JsonPrimitive(droneHeightOffset));
        object.add("soldiersMinimum1", new JsonPrimitive(soldiersMinimum1));
        object.add("soldiersMinimum2", new JsonPrimitive(soldiersMinimum2));
        object.add("soldiersMinimumN", new JsonPrimitive(soldiersMinimumN));
        object.add("soldiersWaveMax1", new JsonPrimitive(soldiersWaveMax1));
        object.add("soldiersWaveMax2", new JsonPrimitive(soldiersWaveMax2));
        object.add("soldiersWaveMaxN", new JsonPrimitive(soldiersWaveMaxN));
        object.add("masterChance", new JsonPrimitive(masterChance));
        object.add("powerArmorChance", new JsonPrimitive(powerArmorChance));
        object.add("forcefieldChance", new JsonPrimitive(forcefieldChance));
        object.add("underground", new JsonPrimitive(underground));
        object.add("floating", new JsonPrimitive(floating));

        writeLootArray(object);
        writeVariants(object);
        writePartPalette(object);

        writePlan(object, "plan", plan);
        writePlan(object, "cellar", cellar);
        writePlan(object, "layer2", layer2);
        writePlan(object, "top", top);

        return object;
    }

    private void writeVariants(JsonObject object) {
        JsonArray array = new JsonArray();
        for (Map.Entry<String, Integer> entry : variants.entrySet()) {
            JsonObject o = new JsonObject();
            o.add("name", new JsonPrimitive(entry.getKey()));
            o.add("count", new JsonPrimitive(entry.getValue()));
            array.add(o);
        }
        object.add("variants", array);
    }

    private void writeLootArray(JsonObject object) {
        JsonArray lootArray = new JsonArray();
        for (Loot l : loot) {
            JsonObject o = new JsonObject();
            if (l.getId() != null) {
                o.add("id", new JsonPrimitive(l.getId().toString()));
            }
            o.add("meta", new JsonPrimitive(l.getMeta()));
            o.add("max", new JsonPrimitive(l.getMaxAmount()));
            o.add("chance", new JsonPrimitive(l.getChance()));
            o.add("blueprint", new JsonPrimitive(l.isBlueprint()));
            lootArray.add(o);
        }
        object.add("loot", lootArray);
    }

    private void writePartPalette(JsonObject object) {
        JsonArray array = new JsonArray();
        for (Map.Entry<Character, PartPalette> entry : partPalette.entrySet()) {
            JsonObject o = new JsonObject();
            o.add("char", new JsonPrimitive(entry.getKey()));

            JsonArray partArray = new JsonArray();
            for (String part : entry.getValue().getPalette()) {
                partArray.add(new JsonPrimitive(part));
            }
            o.add("parts", partArray);
            String variant = entry.getValue().getVariant();
            if (variant != null && !variant.isEmpty()) {
                o.add("variant", new JsonPrimitive(variant));
            }
            array.add(o);
        }
        object.add("partpalette", array);
    }

    private void writePlan(JsonObject object, String name, List<String> plan) {
        if (!plan.isEmpty()) {
            JsonArray planArray = new JsonArray();
            for (String p : plan) {
                planArray.add(new JsonPrimitive(p));
            }
            object.add(name, planArray);
        }
    }
}
