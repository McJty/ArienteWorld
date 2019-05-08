package mcjty.arienteworld.dimension.features;

import mcjty.arienteworld.biomes.IArienteBiome;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FeatureTools {

    public static boolean isFeatureCenter(IFeature feature, double strength, World world, int chunkX, int chunkZ) {
        double factor = feature.getFactor(world, chunkX, chunkZ);
        Random random = feature.getRandom(world, chunkX, chunkZ);
        random.nextFloat();
        return random.nextFloat() * strength > factor;
    }

    public static void generate(IFeature feature) {
    }

    public static Map<String, Double> getActiveFeatures(Biome[] biomes) {
        if (biomes.length == 0) {
            return Collections.emptyMap();
        }
        Map<String, Double> activeFeatures = new HashMap<>();
        for (IFeature feature : FeatureRegistry.getFeatures()) {
            double f = 0.0;
            for (Biome biome : biomes) {
                if (biome instanceof IArienteBiome) {
                    IArienteBiome arienteBiome = (IArienteBiome) biome;
                    f += arienteBiome.getFeatureStrength(feature);
                }
            }
            f /= biomes.length;
            if (f > 0) {
                activeFeatures.put(feature.getId(), f);
            }
        }
        return activeFeatures;
    }
}
