package mcjty.arienteworld.biomes;

import net.minecraft.world.biome.Biome;

public class ModBiomes {

    public static BiomeArienteCity createBiomeCity() {
        return new BiomeArienteCity(
                        new Biome.Builder()
                                .depth(0.125F)
                                .scale(0.05F)
                                .temperature(0.8F)
                                .downfall(0.4F));
    }

    public static BiomeArienteOcean createBiomeOcean() {
        return new BiomeArienteOcean(
                        new Biome.Builder()
                                .depth(-1.0F).scale(0.1F));
    }

    public static BiomeArienteRough createBiomeRough() {
        return new BiomeArienteRough(
                        new Biome.Builder()
                                .depth(0.25F)
                                .scale(0.5F)
                                .temperature(0.8F)
                                .downfall(0.4F));
    }

    public static BiomeArienteHills createBiomeHills() {
        return new BiomeArienteHills(
                        new Biome.Builder()
                                .depth(0.45F)
                                .scale(0.3F)
                                .temperature(0.8F)
                                .downfall(0.4F));
    }

    public static BiomeArienteForest createBiomeForest() {
        return new BiomeArienteForest(
                        new Biome.Builder()
                                .depth(0.125F)
                                .scale(0.05F)
                                .temperature(0.8F)
                                .downfall(0.4F));
    }

    public static BiomeArientePlains createBiomePlains() {
        return new BiomeArientePlains(
                        new Biome.Builder()
                                .depth(0.125F)
                                .scale(0.05F)
                                .temperature(0.8F)
                                .downfall(0.4F));
    }
}
