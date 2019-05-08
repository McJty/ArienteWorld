package mcjty.arienteworld.biomes;

import mcjty.arienteworld.dimension.features.IFeature;
import mcjty.arienteworld.dimension.features.SpheresFeature;

import java.util.HashMap;
import java.util.Map;

public class BiomeArienteOcean extends AbstractArienteBiome {

    private static final Map<String, Double> FEATURE_STRENGTHS = new HashMap<>();

    static {
        FEATURE_STRENGTHS.put(SpheresFeature.FEATURE_SPHERES, 0.7);
    }

    public BiomeArienteOcean(BiomeProperties properties) {
        super(properties);
        this.decorator.treesPerChunk = 0;
        this.decorator.extraTreeChance = 0;
        this.decorator.flowersPerChunk = 0;
    }

    @Override
    public double getFeatureStrength(IFeature feature) {
        return FEATURE_STRENGTHS.getOrDefault(feature.getId(), 0.0);
    }
}
