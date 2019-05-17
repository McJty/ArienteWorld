package mcjty.arienteworld.biomes;

import mcjty.arienteworld.dimension.features.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BiomeArientePlains extends AbstractArienteBiome {

    private static final Map<String, Double> FEATURE_STRENGTHS = new HashMap<>();

    static {
        FEATURE_STRENGTHS.put(SpheresFeature.FEATURE_SPHERES, 0.7);
        FEATURE_STRENGTHS.put(SpikesFeature.FEATURE_SPIKES, 0.0);
        FEATURE_STRENGTHS.put(BubbleFeature.FEATURE_BUBBLES, 0.0);
        FEATURE_STRENGTHS.put(GlowBubbleFeature.FEATURE_GLOWBUBBLES, 0.0);
    }

    public BiomeArientePlains(BiomeProperties properties) {
        super(properties);
        this.decorator.treesPerChunk = 0;
        this.decorator.extraTreeChance = 0.1F;
        this.decorator.flowersPerChunk = 0;
        this.decorator.grassPerChunk = 10;
    }

    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos) {
        super.decorate(worldIn, rand, pos);
        generateFlowers(worldIn, rand, 5);
    }

    @Override
    public double getFeatureStrength(IFeature feature) {
        return FEATURE_STRENGTHS.getOrDefault(feature.getId(), 0.0);
    }
}
