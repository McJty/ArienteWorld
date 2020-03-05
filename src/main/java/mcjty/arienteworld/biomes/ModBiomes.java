package mcjty.arienteworld.biomes;

import mcjty.arienteworld.ArienteWorld;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

public class ModBiomes {

    @ObjectHolder(ArienteWorld.MODID + ":ariente_plains")
    public static BiomeArientePlains arientePlains;

    @ObjectHolder(ArienteWorld.MODID + ":ariente_hills")
    public static BiomeArienteHills arienteHills;

    @ObjectHolder(ArienteWorld.MODID + ":ariente_ocean")
    public static BiomeArienteOcean arienteOcean;

    @ObjectHolder(ArienteWorld.MODID + ":ariente_forest")
    public static BiomeArienteForest arienteForest;

    @ObjectHolder(ArienteWorld.MODID + ":ariente_rough")
    public static BiomeArienteRough arienteRough;

    @ObjectHolder(ArienteWorld.MODID + ":ariente_city")
    public static BiomeArienteCity arienteCity;


    public static void registerBiomes(IForgeRegistry<Biome> registry) {
        setupBiome(registry, "ariente_plains", new BiomeArientePlains(
                        new Biome.Builder()     // @todo 1.15 "Ariente Plains")
                                .depth(0.125F)
                                .scale(0.05F)
                                .temperature(0.8F)
                                .downfall(0.4F)),
                BiomeDictionary.Type.SPARSE);
        setupBiome(registry, "ariente_forest", new BiomeArienteForest(
                        new Biome.Builder()     // @todo 1.15 "Ariente Forest")
                                .depth(0.125F)
                                .scale(0.05F)
                                .temperature(0.8F)
                                .downfall(0.4F)),
                BiomeDictionary.Type.FOREST);
        setupBiome(registry, "ariente_hills", new BiomeArienteHills(
                        new Biome.Builder() // @todo 1.15 "Ariente Hills")
                                .depth(0.45F)
                                .scale(0.3F)
                                .temperature(0.8F)
                                .downfall(0.4F)),
                BiomeDictionary.Type.HILLS);
        setupBiome(registry, "ariente_rough", new BiomeArienteRough(
                        new Biome.Builder() // @todo 1.15 "Ariente Rough")
                                .depth(0.25F)
                                .scale(0.5F)
                                .temperature(0.8F)
                                .downfall(0.4F)),
                BiomeDictionary.Type.DEAD, BiomeDictionary.Type.HILLS);
        setupBiome(registry, "ariente_ocean", new BiomeArienteOcean(
                        new Biome.Builder() // @todo 1.15 "Ariente Ocean")
                                .depth(-1.0F).scale(0.1F)),
                BiomeDictionary.Type.DEAD, BiomeDictionary.Type.OCEAN);
        setupBiome(registry, "ariente_city", new BiomeArienteCity(
                        new Biome.Builder() // @todo 1.15 "Ariente City")
                                .depth(0.125F)
                                .scale(0.05F)
                                .temperature(0.8F)
                                .downfall(0.4F)),
                BiomeDictionary.Type.SPARSE);
    }

    private static void setupBiome(IForgeRegistry<Biome> registry, String name, Biome biome, BiomeDictionary.Type... types) {
        biome.setRegistryName(ArienteWorld.MODID, name);
        registry.register(biome);
        BiomeDictionary.addTypes(biome, types);
    }
}
