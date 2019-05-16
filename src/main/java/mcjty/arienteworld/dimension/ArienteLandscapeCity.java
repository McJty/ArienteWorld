package mcjty.arienteworld.dimension;

import mcjty.ariente.api.MarbleColor;
import mcjty.arienteworld.ArienteStuff;
import mcjty.arienteworld.biomes.IArienteBiome;
import mcjty.arienteworld.cities.AssetRegistries;
import mcjty.arienteworld.cities.CityTools;
import mcjty.arienteworld.cities.Transform;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.*;

public class ArienteLandscapeCity {

    public static final int CITY_LEVEL = 65;    // @todo make variable?
    public static final int NUM_PARKS = 9;
    public static final int NUM_BUILDINGS = 10;
    public static final int MAX_STRIP_LENGTH = 6;
    public static final int CITYLEV_HEIGHT = 6;        // The height of city levitator section

    public static final String CITY_PALETTE = "landscapecities";

    private static Map<ChunkPos, Boolean> cityChunkCache = new HashMap<>();
    private static Map<ChunkPos, LStripInfo> cityStripInfoCache = new HashMap<>();

    // For the citylev network (city levitator network)
    private static class LStripInfo {
        private final boolean isLevel0Chunk;
        private final int length;
        private final int direction; // Like EnumFacing.ordinal() (2, 3, 4, 5)
        private Boolean isLevel1Chunk;
        private Boolean containsStrip;

        public LStripInfo(int chunkX, int chunkZ) {
            Random random = new Random(chunkX * 132897987541L + chunkZ * 341873128712L);
            random.nextFloat();

            boolean gridpoint = (chunkX % 2) == 0 && (chunkZ % 2) == 0;

            isLevel0Chunk = gridpoint && random.nextFloat() < .1;
            length = random.nextInt(MAX_STRIP_LENGTH+1-2) + 2;
            direction = random.nextInt(4) + 2;

            if (random.nextFloat() < .1 || !gridpoint) {
                isLevel1Chunk = false;      // Already set to false
            }
        }
    }

    private static LStripInfo getStripInfo(int chunkX, int chunkZ) {
        ChunkPos cp = new ChunkPos(chunkX, chunkZ);
        if (!cityStripInfoCache.containsKey(cp)) {
            LStripInfo info = new LStripInfo(chunkX, chunkZ);
            cityStripInfoCache.put(cp, info);
        }
        return cityStripInfoCache.get(cp);
    }

    private static boolean isStripStartStation(int chunkX, int chunkZ) {
        LStripInfo info0 = getStripInfo(chunkX, chunkZ);
        if (info0.isLevel0Chunk) {
            return true;
        }
        if (info0.isLevel1Chunk != null) {
            return info0.isLevel1Chunk;
        }
        boolean level1 = false;
        for (int i = 1 ; i <= MAX_STRIP_LENGTH ; i++) {
            LStripInfo info;
            info = getStripInfo(chunkX + i, chunkZ);
            if (info.isLevel0Chunk && info.direction == EnumFacing.WEST.ordinal() && info.length > i) {
                level1 = true;
                break;
            }
            info = getStripInfo(chunkX - i, chunkZ);
            if (info.isLevel0Chunk && info.direction == EnumFacing.EAST.ordinal() && info.length > i) {
                level1 = true;
                break;
            }
            info = getStripInfo(chunkX, chunkZ + i);
            if (info.isLevel0Chunk && info.direction == EnumFacing.NORTH.ordinal() && info.length > i) {
                level1 = true;
                break;
            }
            info = getStripInfo(chunkX, chunkZ - i);
            if (info.isLevel0Chunk && info.direction == EnumFacing.SOUTH.ordinal() && info.length > i) {
                level1 = true;
                break;
            }
        }
        info0.isLevel1Chunk = level1;
        return level1;
    }

    public static boolean isCityLevitatorChunk(int chunkX, int chunkZ) {
        LStripInfo info0 = getStripInfo(chunkX, chunkZ);

        if (info0.containsStrip != null) {
            return info0.containsStrip;
        }

        if (info0.isLevel0Chunk) {
            info0.containsStrip = true;
            return true;
        }
        if (info0.isLevel1Chunk != null && info0.isLevel1Chunk) {
            info0.containsStrip = true;
            return true;
        }

        for (int i = 1 ; i <= MAX_STRIP_LENGTH ; i++) {
            LStripInfo info;
            info = getStripInfo(chunkX + i, chunkZ);
            if (isStripStartStation(chunkX+i, chunkZ) && info.direction == EnumFacing.WEST.ordinal() && info.length > i) {
                info0.containsStrip = true;
                return true;
            }
            info = getStripInfo(chunkX - i, chunkZ);
            if (isStripStartStation(chunkX-i, chunkZ) && info.direction == EnumFacing.EAST.ordinal() && info.length > i) {
                info0.containsStrip = true;
                return true;
            }
            info = getStripInfo(chunkX, chunkZ + i);
            if (isStripStartStation(chunkX, chunkZ+i) && info.direction == EnumFacing.NORTH.ordinal() && info.length > i) {
                info0.containsStrip = true;
                return true;
            }
            info = getStripInfo(chunkX, chunkZ - i);
            if (isStripStartStation(chunkX, chunkZ-i) && info.direction == EnumFacing.SOUTH.ordinal() && info.length > i) {
                info0.containsStrip = true;
                return true;
            }
        }

        info0.containsStrip = false;
        return false;
    }


    public static boolean isLandscapeCityChunk(int chunkX, int chunkZ, @Nullable Biome[] biomesForGeneration) {
        ChunkPos pos = new ChunkPos(chunkX, chunkZ);
        if (!cityChunkCache.containsKey(pos)) {
            if (biomesForGeneration == null) {
                throw new IllegalStateException("biomesForGeneration can't be null here!");
            }
            cityChunkCache.put(pos, hasCityBiomes(biomesForGeneration));
        }
        return cityChunkCache.get(pos);
    }

    private static boolean hasCityBiomes(Biome[] biomesForGeneration) {
        int yes = 0;
        int no = 0;
        for (Biome biome : biomesForGeneration) {
            if (biome instanceof IArienteBiome && ((IArienteBiome) biome).isCityBiome()) {
                yes++;
            } else {
                no++;
            }
        }
        return yes > no+((yes+no)/10);
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

    // Used when there is no room for a building
    public static String getParkPart(int chunkX, int chunkZ) {
        Random random = new Random(chunkX * 234516783139L + chunkZ * 567000003533L);
        random.nextFloat();
        return "park" + (random.nextInt(NUM_PARKS)+1);
    }

    // Only relevant on city chunks. Returns the height of this city part (lower y for the building)
    public static int getBuildingHeight(int chunkX, int chunkZ) {
        Random random = new Random(chunkZ * 593441843L + chunkX * 217645177L);
        random.nextFloat();
        if (isCityLevitatorChunk(chunkX, chunkZ)) {
            // If this is a building above a citylev network then we start the building right
            // above this
            return CITY_LEVEL + CITYLEV_HEIGHT;
        } else {
            return CITY_LEVEL + random.nextInt(6);
        }
    }

    // Indexed by bitmask for all four directions:
    // <-X> <-Z> <+X> <+Z>
    private static final List<Pair<String, Transform>> CITYLEV_PARTS = Arrays.asList(
            Pair.of("citylev_straight", Transform.ROTATE_NONE),     // 0000: Impossible configuration
            Pair.of("citylev_end", Transform.ROTATE_270),           // 0001: OK
            Pair.of("citylev_end", Transform.ROTATE_180),           // 0010: OK
            Pair.of("citylev_bend", Transform.ROTATE_270),          // 0011: OK
            Pair.of("citylev_end", Transform.ROTATE_90),            // 0100: OK
            Pair.of("citylev_straight", Transform.ROTATE_90),       // 0101: OK
            Pair.of("citylev_bend", Transform.ROTATE_180),          // 0110: OK
            Pair.of("citylev_three", Transform.ROTATE_270),         // 0111: OK
            Pair.of("citylev_end", Transform.ROTATE_NONE),          // 1000: OK
            Pair.of("citylev_bend", Transform.ROTATE_NONE),         // 1001: OK
            Pair.of("citylev_straight", Transform.ROTATE_NONE),     // 1010: OK
            Pair.of("citylev_three", Transform.ROTATE_NONE),        // 1011: OK
            Pair.of("citylev_bend", Transform.ROTATE_90),           // 1100: OK
            Pair.of("citylev_three", Transform.ROTATE_90),          // 1101: OK
            Pair.of("citylev_three", Transform.ROTATE_180),         // 1110: OK
            Pair.of("citylev_cross", Transform.ROTATE_NONE)         // 1111: OK
    );

    public static void generate(int chunkX, int chunkZ, ChunkPrimer primer, ArienteCityGenerator cityGenerator) {
        char baseChar = (char) Block.BLOCK_STATE_IDS.get(ArienteStuff.marble.getDefaultState());
        char fillChar = (char) Block.BLOCK_STATE_IDS.get(ArienteStuff.marble.getDefaultState().withProperty(MarbleColor.COLOR, MarbleColor.BLACK));

        if (CityTools.isDungeonChunk(chunkX, chunkZ)) {
            // Only generate marble until landscape level
            for (int dx = 0 ; dx < 16 ; dx++) {
                for (int dz = 0 ; dz < 16 ; dz++) {
                    int index = (dx << 12) | (dz << 8);
                    PrimerTools.setBlockStateRange(primer, index, index + CITY_LEVEL + CITYLEV_HEIGHT, baseChar);
                }
            }
            return;
        }

        int height = getBuildingHeight(chunkX, chunkZ);
        boolean levitatorChunk = isCityLevitatorChunk(chunkX, chunkZ);
        if (levitatorChunk) {
            height -= CITYLEV_HEIGHT;   // Building height is where the building starts. We need to go lower here
        }

        for (int dx = 0 ; dx < 16 ; dx++) {
            for (int dz = 0 ; dz < 16 ; dz++) {
                int index = (dx << 12) | (dz << 8);
                PrimerTools.setBlockStateRange(primer, index, index + CITY_LEVEL-2, baseChar);
                PrimerTools.setBlockStateRange(primer, index + CITY_LEVEL-2, index + height, fillChar);
            }
        }

        String part = getBuildingPart(chunkX, chunkZ);
        // Sample the world above this to see if we have room for a building
        if (part.startsWith("building")) {
            int partHeight = AssetRegistries.PARTS.get(part).getSliceCount();
            if (isChunkOccupied(primer, height+1, height + partHeight + (levitatorChunk ? CITYLEV_HEIGHT : 0))) {
                part = getParkPart(chunkX, chunkZ);
            }
        }


        if (levitatorChunk) {
            Pair<String, Transform> pair = getCityLevitatorPart(chunkX, chunkZ);
            cityGenerator.generatePart(primer, CITY_PALETTE, AssetRegistries.PARTS.get(pair.getKey()),
                    pair.getValue(), 0, height, 0);

            Random random = new Random(chunkX * 341873128712L + chunkZ * 132897987541L);
            random.nextFloat();
            if (random.nextFloat() < .8) {
                // Disable the building above this in most cases
                part = null;
            } else {
                // We generate a roofpart on top of which the building will come
                cityGenerator.generatePart(primer, CITY_PALETTE, AssetRegistries.PARTS.get("citylev_roofpart"),
                        Transform.ROTATE_NONE, 0, height, 0);
                height += CITYLEV_HEIGHT;
            }
        }

        if (part != null) {
            cityGenerator.generatePart(primer, CITY_PALETTE, AssetRegistries.PARTS.get(part), Transform.ROTATE_NONE,
                    0, height, 0);
        }
    }

    private static boolean isChunkOccupied(ChunkPrimer primer, int y1, int y2) {
        char air = (char) Block.BLOCK_STATE_IDS.get(Blocks.AIR.getDefaultState());

        for (int dx = 0 ; dx < 16 ; dx += 2) {
            for (int dz = 0 ; dz < 16 ; dz += 2) {
                int index = (dx << 12) | (dz << 8);
                int y = y1;
                while (y <= y2) {
                    if (primer.data[index + y] != air) {
                        return true;
                    }
                    y += 2;
                }
            }
        }
        return false;
    }

    public static Pair<String, Transform> getCityLevitatorPart(int chunkX, int chunkZ) {
        // Generate a bitmask of 4 bits for all four directions around this chunk to see
        // which citylev part we need (and transform)
        int citylev0 = isCityLevitatorChunk(chunkX-1, chunkZ) ? 8 : 0;
        int citylev1 = isCityLevitatorChunk(chunkX, chunkZ-1) ? 4 : 0;
        int citylev2 = isCityLevitatorChunk(chunkX+1, chunkZ) ? 2 : 0;
        int citylev3 = isCityLevitatorChunk(chunkX, chunkZ+1) ? 1 : 0;
        int mask = citylev0 | citylev1 | citylev2 | citylev3;
        return CITYLEV_PARTS.get(mask);
    }

    public static void main(String[] args) {
        for (int z = 0 ; z < 60 ; z++) {
            String b = "";
            for (int x = 0 ; x < 60 ; x++) {
                boolean chunk = isCityLevitatorChunk(x, z);
                LStripInfo info = getStripInfo(x, z);
                b += chunk ? (info.isLevel0Chunk ? '#' : ((info.isLevel1Chunk != null && info.isLevel1Chunk) ? '*' : '+')) : ' ';
            }
            System.out.println(b);
        }
    }
}
