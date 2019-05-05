package mcjty.arienteworld;

import mcjty.arienteworld.blocks.ModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModCrafting {
    public static void init() {
        GameRegistry.addSmelting(ModBlocks.lithiumore, new ItemStack(ArienteStuff.lithiumIngot, 1), 0.0f);
        GameRegistry.addSmelting(ModBlocks.manganeseore, new ItemStack(ArienteStuff.manganeseIngot, 1), 0.0f);
        GameRegistry.addSmelting(ModBlocks.platinumore, new ItemStack(ArienteStuff.platinumIngot, 1), 0.0f);
        GameRegistry.addSmelting(ModBlocks.silverore, new ItemStack(ArienteStuff.silverIngot, 1), 0.0f);
    }
}
