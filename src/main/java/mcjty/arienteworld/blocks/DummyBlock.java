package mcjty.arienteworld.blocks;

import mcjty.lib.blocks.BaseBlock;
import mcjty.lib.blocks.RotationType;
import mcjty.lib.builder.BlockBuilder;

public class DummyBlock extends BaseBlock {

    public DummyBlock(BlockBuilder builder) {
        super(builder);
    }

    @Override
    public RotationType getRotationType() {
        return RotationType.HORIZROTATION;
    }
}
