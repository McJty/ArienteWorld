package mcjty.arienteworld.biomes;

import mcjty.arienteworld.setup.Registration;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public enum ArienteBiomeLayer implements IAreaTransformer0 {

    INSTANCE;

    private static PerlinNoiseGenerator perlinGen;

    private static final int CITY_ID = Registry.BIOME.getId(Registration.ARIENTE_CITY.get());
    private static final int FOREST_ID = Registry.BIOME.getId(Registration.ARIENTE_FOREST.get());
    private static final int HILLS_ID = Registry.BIOME.getId(Registration.ARIENTE_HILLS.get());
    private static final int OCEAN_ID = Registry.BIOME.getId(Registration.ARIENTE_OCEAN.get());
    private static final int PLAINS_ID = Registry.BIOME.getId(Registration.ARIENTE_PLAINS.get());
    private static final int ROUGH_ID = Registry.BIOME.getId(Registration.ARIENTE_ROUGH.get());

    @Override
    public int apply(INoiseRandom noise, int x, int z) {
        double perlinNoise = perlinGen.noiseAt(x * 0.1D, z * 0.00001D, false) * 0.5D + 0.5D;

        if (noise.random(9) == 0) {
            return CITY_ID;
        } else if (Math.abs(perlinNoise) < 0.2) {
            return FOREST_ID;
        } else if (Math.abs(perlinNoise) < 0.3) {
            return HILLS_ID;
        } else if (Math.abs(perlinNoise) < 0.5) {
            return OCEAN_ID;
        } else if (Math.abs(perlinNoise) < 0.7) {
            return ROUGH_ID;
        } else {
            return PLAINS_ID;
        }
    }

    public static void setSeed(long seed) {
        if (perlinGen == null) {
            SharedSeedRandom sharedseedrandom = new SharedSeedRandom(seed);
            perlinGen = new PerlinNoiseGenerator(sharedseedrandom, 0, 0);
        }
    }

}
