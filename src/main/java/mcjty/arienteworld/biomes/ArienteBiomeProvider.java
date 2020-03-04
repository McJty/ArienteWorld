package mcjty.arienteworld.biomes;

import net.minecraft.world.World;
import net.minecraft.world.biome.provider.BiomeProvider;

public class ArienteBiomeProvider extends BiomeProvider {

    public ArienteBiomeProvider(World world) {
        super(world.getWorldInfo());
        getBiomesToSpawnIn().clear();
        getBiomesToSpawnIn().add(ModBiomes.arientePlains);
        getBiomesToSpawnIn().add(ModBiomes.arienteHills);
        getBiomesToSpawnIn().add(ModBiomes.arienteOcean);
        getBiomesToSpawnIn().add(ModBiomes.arienteForest);
        getBiomesToSpawnIn().add(ModBiomes.arienteRough);
        getBiomesToSpawnIn().add(ModBiomes.arienteCity);
        makeLayers(world.getSeed());
    }

    private void makeLayers(long seed) {
        GenLayer biomes = new GenLayerArienteBiomes(1L);

        biomes = new GenLayerZoom(1000, biomes);
        biomes = new GenLayerZoom(1001, biomes);

        biomes = new GenLayerZoom(1002, biomes);
        biomes = new GenLayerZoom(1003, biomes);
        biomes = new GenLayerZoom(1004, biomes);
        biomes = new GenLayerZoom(1005, biomes);
        biomes = new GenLayerZoom(1006, biomes);

        GenLayer genlayervoronoizoom = new GenLayerVoronoiZoom(10L, biomes);

        biomes.initWorldGenSeed(seed);
        genlayervoronoizoom.initWorldGenSeed(seed);

        genBiomes = biomes;
        biomeIndexLayer = genlayervoronoizoom;
    }

}
