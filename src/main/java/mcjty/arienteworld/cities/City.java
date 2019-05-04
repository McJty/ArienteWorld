package mcjty.arienteworld.cities;

import mcjty.ariente.varia.ChunkCoord;
import mcjty.arienteworld.dimension.ArienteChunkGenerator;
import mcjty.arienteworld.dimension.ChunkHeightmap;

public class City {

    private final ChunkCoord center;
    private final CityPlan plan;
    private int height;

    public City(ChunkCoord center, CityPlan plan, int height) {
        this.center = center;
        this.plan = plan;
        this.height = height;

    }

    public ChunkCoord getCenter() {
        return center;
    }

    public CityPlan getPlan() {
        return plan;
    }

    public int getHeight() {
        return height;
    }

    public int getHeight(ArienteChunkGenerator generator) {
        if (height == -1) {
            if (plan.isUnderground()) {
                height = 40;
            } else if (plan.isFloating()) {
                height = 100;
            } else {
                ChunkHeightmap heightmap = generator.getHeightmap(center.getChunkX(), center.getChunkZ());
                height = heightmap.getAverageHeight();
            }
        }
        return height;
    }
}
