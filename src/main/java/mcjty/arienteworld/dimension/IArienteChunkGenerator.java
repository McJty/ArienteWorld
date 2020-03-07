package mcjty.arienteworld.dimension;

public interface IArienteChunkGenerator {
    // Get a heightmap for a chunk. If needed calculate (and cache) a primer
    ChunkHeightmap getHeightmap(int chunkX, int chunkZ);
}
