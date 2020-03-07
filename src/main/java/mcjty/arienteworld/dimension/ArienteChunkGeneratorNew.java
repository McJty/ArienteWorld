package mcjty.arienteworld.dimension;

import mcjty.arienteworld.ArienteStuff;
import mcjty.arienteworld.cities.City;
import mcjty.arienteworld.cities.CityTools;
import mcjty.arienteworld.dimension.features.FeatureRegistry;
import mcjty.arienteworld.dimension.features.FeatureTools;
import mcjty.arienteworld.dimension.features.IFeature;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.palette.UpgradeData;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.OverworldGenSettings;

import java.util.HashMap;
import java.util.Map;

import static mcjty.arienteworld.dimension.ArienteLandscapeCity.CITY_LEVEL;

public class ArienteChunkGeneratorNew extends NoiseChunkGenerator<OverworldGenSettings> implements IArienteChunkGenerator {

    private static final float[] field_222576_h = Util.make(new float[25], (p_222575_0_) -> {
        for(int lvt_1_1_ = -2; lvt_1_1_ <= 2; ++lvt_1_1_) {
            for(int lvt_2_1_ = -2; lvt_2_1_ <= 2; ++lvt_2_1_) {
                float lvt_3_1_ = 10.0F / MathHelper.sqrt((float)(lvt_1_1_ * lvt_1_1_ + lvt_2_1_ * lvt_2_1_) + 0.2F);
                p_222575_0_[lvt_1_1_ + 2 + (lvt_2_1_ + 2) * 5] = lvt_3_1_;
            }
        }

    });

    private final OctavesNoiseGenerator depthNoise;
    private ArienteTerrainGenerator terraingen = new ArienteTerrainGenerator();
    private IslandsTerrainGenerator islandsGen = new IslandsTerrainGenerator();
    private ArienteDungeonGenerator dungeonGenerator = new ArienteDungeonGenerator();

    private Map<ChunkPos, ChunkPrimer> cachedPrimers = new HashMap<>();
    private Map<ChunkPos, ChunkHeightmap> cachedHeightmaps = new HashMap<>();
    private Map<ChunkPos, Map<String, Double>> activeFeatureCache = new HashMap<>();

    public ArienteChunkGeneratorNew(IWorld worldObj, BiomeProvider biomeProvider, int horizontalNoiseGranularityIn, int verticalNoiseGranularityIn, int p_i49931_5_, OverworldGenSettings settings, boolean usePerlin) {
        super(worldObj, biomeProvider, horizontalNoiseGranularityIn, verticalNoiseGranularityIn, p_i49931_5_, settings, usePerlin);
        long seed = this.world.getSeed();
        this.randomSeed.skip(2620);
        this.depthNoise = new OctavesNoiseGenerator(this.randomSeed, 15, 0);

        terraingen.setup(this.world, randomSeed, ArienteStuff.marble.getDefaultState());
        islandsGen.setup(this.world, this.world.getSeed());
//        islandgen.setup(worldObj, random, this, 40);
//        island2gen.setup(worldObj, new Random((seed + 314) * 516), this, 40);

        // @todo 1.15
//        caveGenerator = TerrainGen.getModdedMapGen(caveGenerator, CAVE);
        dungeonGenerator.initialize(this);
    }

    @Override
    protected double[] getBiomeNoiseColumn(int noiseX, int noiseZ) {
        double[] noiseColumn = new double[2];
        float lvt_4_1_ = 0.0F;
        float lvt_5_1_ = 0.0F;
        float lvt_6_1_ = 0.0F;
        int sea = this.getSeaLevel();
        float biomeNoise = this.biomeProvider.getNoiseBiome(noiseX, sea, noiseZ).getDepth();

        for(int dx = -2; dx <= 2; ++dx) {
            for(int dz = -2; dz <= 2; ++dz) {
                Biome biome = this.biomeProvider.getNoiseBiome(noiseX + dx, sea, noiseZ + dz);
                float depth = biome.getDepth();
                float scale = biome.getScale();

                float lvt_15_1_ = field_222576_h[dx + 2 + (dz + 2) * 5] / (depth + 2.0F);
                if (biome.getDepth() > biomeNoise) {
                    lvt_15_1_ /= 2.0F;
                }

                lvt_4_1_ += scale * lvt_15_1_;
                lvt_5_1_ += depth * lvt_15_1_;
                lvt_6_1_ += lvt_15_1_;
            }
        }

        lvt_4_1_ /= lvt_6_1_;
        lvt_5_1_ /= lvt_6_1_;
        lvt_4_1_ = lvt_4_1_ * 0.9F + 0.1F;
        lvt_5_1_ = (lvt_5_1_ * 4.0F - 1.0F) / 8.0F;
        noiseColumn[0] = (double)lvt_5_1_ + this.getNoiseDepthAt(noiseX, noiseZ);
        noiseColumn[1] = (double)lvt_4_1_;
        return noiseColumn;
    }

    @Override
    public void makeBase(IWorld worldIn, IChunk chunk) {
        super.makeBase(worldIn, chunk);
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;
        islandsGen.setBlocksInChunk(chunkX, chunkZ, chunk);


        BlockPos.Mutable pos = new BlockPos.Mutable();
        boolean isLandscapeCityChunk = ArienteLandscapeCity.isLandscapeCityChunk(chunkX, chunkZ, world, null /* @todo 1.15 find biomes? */ );
        if (isLandscapeCityChunk) {
            ArienteLandscapeCity.generate(chunkX, chunkZ, chunk, dungeonGenerator);
        } else {
            // Check all adjacent chunks and see if we need to generate a wall
            if (ArienteLandscapeCity.isLandscapeCityChunk(chunkX-1, chunkZ, world, null)) {
                for (int dz = 0 ; dz < 16 ; dz++) {
                    PrimerTools.setBlockStateRange(chunk, 0, CITY_LEVEL-2, dz,CITY_LEVEL+6, dungeonGenerator.getCityWallChar());
                    chunk.setBlockState(pos.setPos(0, CITY_LEVEL+6, dz), dungeonGenerator.getCityWallTop(), false);
                }
            }
            if (ArienteLandscapeCity.isLandscapeCityChunk(chunkX+1, chunkZ, world, null)) {
                for (int dz = 0 ; dz < 16 ; dz++) {
                    PrimerTools.setBlockStateRange(chunk, 15, CITY_LEVEL-2, dz,CITY_LEVEL+6, dungeonGenerator.getCityWallChar());
                    chunk.setBlockState(pos.setPos(15, CITY_LEVEL+6, dz), dungeonGenerator.getCityWallTop(), false);
                }
            }
            if (ArienteLandscapeCity.isLandscapeCityChunk(chunkX, chunkZ-1, world, null)) {
                for (int dx = 0 ; dx < 16 ; dx++) {
                    PrimerTools.setBlockStateRange(chunk, dx, CITY_LEVEL-2, 0, CITY_LEVEL+6, dungeonGenerator.getCityWallChar());
                    chunk.setBlockState(pos.setPos(dx, CITY_LEVEL+6, 0), dungeonGenerator.getCityWallTop(), false);
                }
            }
            if (ArienteLandscapeCity.isLandscapeCityChunk(chunkX, chunkZ+1, world, null)) {
                for (int dx = 0 ; dx < 16 ; dx++) {
                    int index = (dx << 12) | (15 << 8);
                    PrimerTools.setBlockStateRange(chunk, dx, CITY_LEVEL-2, 15, CITY_LEVEL+6, dungeonGenerator.getCityWallChar());
                    chunk.setBlockState(pos.setPos(dx, CITY_LEVEL+6, 15), dungeonGenerator.getCityWallTop(), false);
                }
            }
        }

        generateActiveFeatures(chunk, chunkX, chunkZ, false, world.getBiomeManager());

        if (!isLandscapeCityChunk) {
            // Don't do this for city chunks
            fixForNearbyCity(chunk, chunkX, chunkZ);
        }

        // @todo 1.15
//        caveGenerator.generate(this.worldObj, chunkX, chunkZ, chunkprimer);
        dungeonGenerator.generate(chunkX, chunkZ, chunk);
        LevitatorNetworkGenerator.generate(chunkX, chunkZ, chunk, this);
    }

    @Override
    public ArienteDungeonGenerator getDungeonGenerator() {
        return dungeonGenerator;
    }

    private NoiseGeneratorPerlin varianceNoise;
    private double[] varianceBuffer = new double[256];

    private void fixForNearbyCity(IChunk primer, int x, int z) {
        if (varianceNoise == null) {
            this.varianceNoise = new NoiseGeneratorPerlin(randomSeed, 4);
        }
        this.varianceBuffer = this.varianceNoise.getRegion(this.varianceBuffer, (x * 16), (z * 16), 16, 16, 1.0 / 16.0, 1.0 / 16.0, 1.0D);
        BlockState air = Blocks.AIR.getDefaultState();

        if (CityTools.isDungeonChunk(x, z)) {
            ChunkPos center = CityTools.getNearestDungeonCenter(x, z);
            City city = CityTools.getCity(center);
            if (!city.getPlan().isUnderground()) {
                int height = city.getHeight(this);
                for (int dx = 0; dx < 16; dx++) {
                    for (int dz = 0; dz < 16; dz++) {
                        PrimerTools.setBlockStateRange(primer, dx, height, dz, 128, air);
                    }
                }
            }
        } else {
            int[][] cityHeights = new int[3][3];
            boolean hasCity = false;
            int height = 0;
            for (int cx = -1 ; cx <= 1 ; cx++) {
                for (int cz = -1 ; cz <= 1 ; cz++) {
                    if (CityTools.isDungeonChunk(x+cx, z+cz)) {
                        ChunkPos center = CityTools.getNearestDungeonCenter(x+cx, z+cz);
                        City city = CityTools.getCity(center);
                        if (!city.getPlan().isUnderground()) {
                            height = city.getHeight(this);
                            cityHeights[cx + 1][cz + 1] = height;
                            hasCity = true;
                        } else {
                            cityHeights[cx+1][cz+1] = -1;
                        }
                    } else {
                        cityHeights[cx+1][cz+1] = -1;
                    }
                }
            }
            if (!hasCity) {
                return;
            }
            for (int dx = 0; dx < 16; dx++) {
                for (int dz = 0; dz < 16; dz++) {
                    double vr = varianceBuffer[dx + dz * 16];
                    int mindist = getMinDist(cityHeights, dx, dz);
                    if (mindist <= 1) {
                        vr /= 3.0f;
                    } else if (mindist <= 2) {
                        vr /= 2.0f;
                    } else if (mindist <= 3) {
                        vr /= 1.5f;
                    }
                    int dh = mindist * 2 + height + (int) vr;
//                    int index = (dx << 12) | (dz << 8) + dh;
                    PrimerTools.setBlockStateRangeSafe(primer, dx, dh, dz, 128, air);
                }
            }
        }
    }


    private int getMinDist(int[][] cityHeights, int dx, int dz) {
        int mindist = 1000;
        if (cityHeights[0][1] != -1) {
            mindist = dx;
        }
        if (cityHeights[0][0] != -1) {
            int dist = Math.max(dx, dz);
            if (dist < mindist) {
                mindist = dist;
            }
        }
        if (cityHeights[0][2] != -1) {
            int dist = Math.max(dx, 15-dz);
            if (dist < mindist) {
                mindist = dist;
            }
        }
        if (cityHeights[2][1] != -1) {
            int dist = 15-dx;
            if (dist < mindist) {
                mindist = dist;
            }
        }
        if (cityHeights[2][0] != -1) {
            int dist = Math.max(15-dx, dz);
            if (dist < mindist) {
                mindist = dist;
            }
        }
        if (cityHeights[2][2] != -1) {
            int dist = Math.max(15-dx, 15-dz);
            if (dist < mindist) {
                mindist = dist;
            }
        }
        if (cityHeights[1][0] != -1) {
            int dist = dz;
            if (dist < mindist) {
                mindist = dist;
            }
        }
        if (cityHeights[1][2] != -1) {
            int dist = 15-dz;
            if (dist < mindist) {
                mindist = dist;
            }
        }
        return mindist;
    }


    private double getNoiseDepthAt(int noiseX, int noiseZ) {
        double noise = this.depthNoise.getValue((double)(noiseX * 200), 10.0D, (double)(noiseZ * 200), 1.0D, 0.0D, true) * 65535.0D / 8000.0D;
        if (noise < 0.0D) {
            noise = -noise * 0.3D;
        }

        noise = noise * 3.0D - 2.0D;
        if (noise < 0.0D) {
            noise /= 28.0D;
        } else {
            if (noise > 1.0D) {
                noise = 1.0D;
            }

            noise /= 40.0D;
        }

        return noise;
    }


    @Override
    protected double func_222545_a(double p_222545_1_, double p_222545_3_, int p_222545_5_) {
        double lvt_8_1_ = ((double)p_222545_5_ - (8.5D + p_222545_1_ * 8.5D / 8.0D * 4.0D)) * 12.0D * 128.0D / 256.0D / p_222545_3_;
        if (lvt_8_1_ < 0.0D) {
            lvt_8_1_ *= 4.0D;
        }

        return lvt_8_1_;
    }

    @Override
    protected void fillNoiseColumn(double[] noiseColumn, int noiseX, int noiseZ) {
        this.calcNoiseColumn(noiseColumn, noiseX, noiseZ, 684.4119873046875D, 684.4119873046875D, 8.555149841308594D, 4.277574920654297D, 3, -10);
    }

    @Override
    public int getGroundHeight() {
        return this.world.getSeaLevel() + 1;
    }

    @Override
    public ChunkHeightmap getHeightmap(int chunkX, int chunkZ) {
        ChunkPos key = new ChunkPos(chunkX, chunkZ);
        if (cachedHeightmaps.containsKey(key)) {
            return cachedHeightmaps.get(key);
        } else if (cachedPrimers.containsKey(key)) {
            ChunkHeightmap heightmap = new ChunkHeightmap(cachedPrimers.get(key));
            cachedHeightmaps.put(key, heightmap);
            return heightmap;
        } else {
            ChunkPrimer primer = generatePrimer(chunkX, chunkZ);
            cachedPrimers.put(key, primer);
            ChunkHeightmap heightmap = new ChunkHeightmap(cachedPrimers.get(key));
            cachedHeightmaps.put(key, heightmap);
            return heightmap;
        }
    }

    public ChunkPrimer generatePrimer(int chunkX, int chunkZ) {
        ChunkPrimer chunkprimer = new ChunkPrimer(new ChunkPos(chunkX, chunkZ), UpgradeData.EMPTY); // @todo 1.15 new ChunkPrimer();

//        this.biomesForGeneration = worldObj.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, chunkX * 4 - 2, chunkZ * 4 - 2, 10, 10);

        terraingen.generate(chunkX, chunkZ, chunkprimer, world.getBiomeManager());
        islandsGen.setBlocksInChunk(chunkX, chunkZ, chunkprimer);

        generateActiveFeatures(chunkprimer, chunkX, chunkZ, true, world.getBiomeManager());

        return chunkprimer;
    }


    private Map<String, Double> getActiveFeatures(int chunkX, int chunkZ, BiomeManager biomes) {
        ChunkPos pos = new ChunkPos(chunkX, chunkZ);
        if (!activeFeatureCache.containsKey(pos)) {
            // @todo 1.15 is this right?
            Map<String, Double> activeFeatures = FeatureTools.getActiveFeatures(biomes.getBiome(new BlockPos(chunkX * 16 + 8, 64, chunkZ * 16 + 8)));
            activeFeatureCache.put(pos, activeFeatures);
        }
        return activeFeatureCache.get(pos);
    }

    private void generateActiveFeatures(IChunk primer, int chunkX, int chunkZ, boolean base, BiomeManager biomes) {
        int size = 1;
        for (int dx = -size ; dx <= size ; dx++) {
            int cx = chunkX + dx;
            for (int dz = -size; dz <= size; dz++) {
                int cz = chunkZ + dz;
                Map<String, Double> activeFeatures = getActiveFeatures(cx, cz, biomes);
                for (Map.Entry<String, Double> pair : activeFeatures.entrySet()) {
                    IFeature feature = FeatureRegistry.getFeature(pair.getKey());
                    if (feature.isBase() == base) {
                        if (FeatureTools.isFeatureCenter(feature, pair.getValue(), world, cx, cz)) {
                            feature.generate(world, primer, chunkX, chunkZ, dx, dz);
                        }
                    }
                }
            }
        }
    }
}
