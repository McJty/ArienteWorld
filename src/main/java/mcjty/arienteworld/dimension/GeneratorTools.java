package mcjty.arienteworld.dimension;

import net.minecraft.block.BlockState;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.registries.GameData;

public class GeneratorTools {

    public static void setBlockState(ChunkPrimer primer, int index, BlockState state) {
        primer.data[index] = (char) GameData.getBlockStateIDMap().get(state);
    }

    public static BlockState getBlockState(ChunkPrimer primer, int index) {
        return GameData.getBlockStateIDMap().getByValue(primer.data[index]);
    }

}
