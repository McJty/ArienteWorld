package mcjty.arienteworld.dimension;

import mcjty.arienteworld.ArienteStuff;
import mcjty.arienteworld.dimension.features.FeatureRegistry;
import mcjty.arienteworld.dimension.features.FeatureTools;
import mcjty.arienteworld.dimension.features.IFeature;
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
    public void makeBase(IWorld worldIn, IChunk chunkIn) {
        super.makeBase(worldIn, chunkIn);
        islandsGen.setBlocksInChunk(chunkIn.getPos().x, chunkIn.getPos().z, chunkIn);
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

    private void generateActiveFeatures(ChunkPrimer primer, int chunkX, int chunkZ, boolean base, BiomeManager biomes) {
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
