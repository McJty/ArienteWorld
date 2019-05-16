package mcjty.arienteworld.dimension;

import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Arrays;

public class PrimerTools {
    public static void setBlockStateRange(ChunkPrimer primer, int s, int e, char c) {
        Arrays.fill(primer.data, s, e, c);
    }

    public static void setBlockStateRangeSafe(ChunkPrimer primer, int s, int e, char c) {
        if (e <= s) {
            return;
        }
        Arrays.fill(primer.data, s, e, c);
    }

    public static void fillChunk(ChunkPrimer primer, char baseChar, int start, int stop) {
        for (int dx = 0 ; dx < 16 ; dx++) {
            for (int dz = 0 ; dz < 16 ; dz++) {
                int index = (dx << 12) | (dz << 8);
                setBlockStateRange(primer, index + start, index + stop, baseChar);
            }
        }
    }
}
