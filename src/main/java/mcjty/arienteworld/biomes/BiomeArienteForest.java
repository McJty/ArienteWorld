package mcjty.arienteworld.biomes;

import mcjty.arienteworld.dimension.features.IFeature;
import mcjty.arienteworld.dimension.features.SpheresFeature;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BiomeArienteForest extends AbstractArienteBiome {

    private static final Map<String, Double> FEATURE_STRENGTHS = new HashMap<>();

    static {
        FEATURE_STRENGTHS.put(SpheresFeature.FEATURE_SPHERES, 0.0);
    }

    public BiomeArienteForest(BiomeProperties properties) {
        super(properties);
        this.decorator.treesPerChunk = 15;
        this.decorator.extraTreeChance = 0.1F;
        this.decorator.grassPerChunk = 2;
        this.decorator.flowersPerChunk = 0;
    }

    @Override
    public void decorate(World worldIn, Random random, BlockPos pos) {
        super.decorate(worldIn, random, pos);
        generateFlowers(worldIn, random, 20);
    }

    @Override
    public double getFeatureStrength(IFeature feature) {
        return FEATURE_STRENGTHS.getOrDefault(feature.getId(), 0.0);
    }
}