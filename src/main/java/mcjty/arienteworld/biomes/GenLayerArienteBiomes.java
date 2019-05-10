package mcjty.arienteworld.biomes;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerArienteBiomes extends GenLayer {

    protected Biome commonBiomes[] = (new Biome[]{
            ModBiomes.arientePlains,
            ModBiomes.arienteHills,
            ModBiomes.arienteOcean,
            ModBiomes.arienteForest,
            ModBiomes.arienteRough,
            ModBiomes.arienteCity
    });

    public GenLayerArienteBiomes(long l) {
        super(l);
    }

    @Override
    public int[] getInts(int x, int z, int width, int depth) {
        int dest[] = IntCache.getIntCache(width * depth);
        for (int dz = 0; dz < depth; dz++) {
            for (int dx = 0; dx < width; dx++) {
                initChunkSeed(dx + x, dz + z);
                dest[dx + dz * width] = Biome.getIdForBiome(commonBiomes[nextInt(commonBiomes.length)]);
//                if (nextInt(RARE_BIOME_CHANCE) == 0) {
//                    // make rare biome
//                    dest[dx + dz * width] = Biome.getIdForBiome(rareBiomes[nextInt(rareBiomes.length)]);
//                } else {
//                    // make common biome
//                    dest[dx + dz * width] = Biome.getIdForBiome(commonBiomes[nextInt(commonBiomes.length)]);
//                }
            }

        }
        return dest;
    }
}
