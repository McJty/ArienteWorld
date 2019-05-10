package mcjty.arienteworld.dimension;

import mcjty.arienteworld.ArienteStuff;
import mcjty.arienteworld.biomes.IArienteBiome;
import mcjty.arienteworld.cities.AssetRegistries;
import mcjty.arienteworld.cities.CityTools;
import mcjty.arienteworld.cities.Transform;
import net.minecraft.block.Block;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ArienteLandscapeCity {

    private static final int CITY_LEVEL = 70;    // @todo make variable?
    private static final int NUM_PARKS = 4;
    private static final int NUM_BUILDINGS = 10;
    public static final String CITY_PALETTE = "landscapecities";

    private static Map<ChunkPos, Boolean> cityChunkCache = new HashMap<>();

    public static boolean isLandscapeCityChunk(int chunkX, int chunkZ, Biome[] biomesForGeneration) {
        ChunkPos pos = new ChunkPos(chunkX, chunkZ);
        if (!cityChunkCache.containsKey(pos)) {
            cityChunkCache.put(pos, hasCityBiomes(biomesForGeneration));
        }
        return cityChunkCache.get(pos);
    }

    private static boolean hasCityBiomes(Biome[] biomesForGeneration) {
        int cnt = 4;
        for (Biome biome : biomesForGeneration) {
            if (biome instanceof IArienteBiome && ((IArienteBiome) biome).isCityBiome()) {
                cnt--;
                if (cnt <= 0) {
                    break;
                }
            }
        }
        if (cnt > 0) {
            return false;
        }
        return true;
    }

    // Only relevant on city chunks. Returns the part name
    public static String getBuildingPart(int chunkX, int chunkZ) {
        Random random = new Random(chunkX * 234516783139L + chunkZ * 567000003533L);
        random.nextFloat();
        if (random.nextFloat() < .6) {
            return "park" + (random.nextInt(NUM_PARKS)+1);
        } else {
            return "building" + (random.nextInt(NUM_BUILDINGS)+1);
        }
    }

    // Only relevant on city chunks. Returns the height of this city part
    public static int getBuildingHeight(int chunkX, int chunkZ) {
        Random random = new Random(chunkZ * 593441843L + chunkX * 217645177L);
        random.nextFloat();
        return CITY_LEVEL + random.nextInt(6);
    }

    public static void generate(int chunkX, int chunkZ, ChunkPrimer primer, ArienteCityGenerator cityGenerator) {
        char baseChar = (char) Block.BLOCK_STATE_IDS.get(ArienteStuff.marble.getDefaultState());
        char fillChar = (char) Block.BLOCK_STATE_IDS.get(ArienteStuff.marble_smooth.getDefaultState());
        int height = getBuildingHeight(chunkX, chunkZ);

        for (int dx = 0 ; dx < 16 ; dx++) {
            for (int dz = 0 ; dz < 16 ; dz++) {
                int index = (dx << 12) | (dz << 8);
                PrimerTools.setBlockStateRange(primer, index, index + CITY_LEVEL-2, baseChar);
                PrimerTools.setBlockStateRange(primer, index + CITY_LEVEL-2, index + height, fillChar);
            }
        }

        if (CityTools.isCityChunk(chunkX, chunkZ)) {
            // Only generate marble until landscape level
        } else {
            String part = getBuildingPart(chunkX, chunkZ);
            cityGenerator.generatePart(primer, CITY_PALETTE, AssetRegistries.PARTS.get(part), Transform.ROTATE_NONE,
                    0, height, 0);
        }
    }
}
