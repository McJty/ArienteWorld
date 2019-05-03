package mcjty.arienteworld.biomes;

import mcjty.ariente.Ariente;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBiomes {

    @GameRegistry.ObjectHolder(Ariente.MODID + ":ariente_plains")
    public static BiomeArientePlains arientePlains;

    @GameRegistry.ObjectHolder(Ariente.MODID + ":ariente_hills")
    public static BiomeArienteHills arienteHills;

    @GameRegistry.ObjectHolder(Ariente.MODID + ":ariente_ocean")
    public static BiomeArienteOcean arienteOcean;

    @GameRegistry.ObjectHolder(Ariente.MODID + ":ariente_forest")
    public static BiomeArienteForest arienteForest;

    @GameRegistry.ObjectHolder(Ariente.MODID + ":ariente_rough")
    public static BiomeArienteRough arienteRough;


    public static void registerBiomes(IForgeRegistry<Biome> registry) {
        setupBiome(registry, "ariente_plains", new BiomeArientePlains(
                        new Biome.BiomeProperties("Ariente Plains")
                                .setBaseHeight(0.125F)
                                .setHeightVariation(0.05F)
                                .setTemperature(0.8F)
                                .setRainfall(0.4F)),
                BiomeDictionary.Type.SPARSE);
        setupBiome(registry, "ariente_forest", new BiomeArienteForest(
                        new Biome.BiomeProperties("Ariente Forest")
                                .setBaseHeight(0.125F)
                                .setHeightVariation(0.05F)
                                .setTemperature(0.8F)
                                .setRainfall(0.4F)),
                BiomeDictionary.Type.FOREST);
        setupBiome(registry, "ariente_hills", new BiomeArienteHills(
                        new Biome.BiomeProperties("Ariente Hills")
                                .setBaseHeight(0.45F)
                                .setHeightVariation(0.3F)
                                .setTemperature(0.8F)
                                .setRainfall(0.4F)),
                BiomeDictionary.Type.HILLS);
        setupBiome(registry, "ariente_rough", new BiomeArienteRough(
                        new Biome.BiomeProperties("Ariente Rough")
                                .setBaseHeight(0.25F)
                                .setHeightVariation(0.5F)
                                .setTemperature(0.8F)
                                .setRainfall(0.4F)),
                BiomeDictionary.Type.DEAD, BiomeDictionary.Type.HILLS);
        setupBiome(registry, "ariente_ocean", new BiomeArienteOcean(
                        new Biome.BiomeProperties("Ariente Ocean")
                                .setBaseHeight(-1.0F).setHeightVariation(0.1F)),
                BiomeDictionary.Type.DEAD, BiomeDictionary.Type.OCEAN);
    }

    private static void setupBiome(IForgeRegistry<Biome> registry, String name, Biome biome, BiomeDictionary.Type... types) {
        biome.setRegistryName(Ariente.MODID, name);
        registry.register(biome);
        BiomeDictionary.addTypes(biome, types);
    }
}
