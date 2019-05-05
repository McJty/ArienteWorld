package mcjty.arienteworld;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ArienteStuff {

    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":key_card")
    public static Item keyCardItem;

    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":energy_sabre")
    public static Item energySabre;

    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":powersuit_head")
    public static Item powerSuitHelmet;
    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":powersuit_chest")
    public static Item powerSuitChest;
    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":powersuit_legs")
    public static Item powerSuitLegs;
    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":powersuit_feet")
    public static Item powerSuitBoots;

    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":ingot_lithium")
    public static Item lithiumIngot;
    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":ingot_manganese")
    public static Item manganeseIngot;
    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":ingot_platinum")
    public static Item platinumIngot;
    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":ingot_silver")
    public static Item silverIngot;

    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":reinforced_marble")
    public static Block reinforcedMarble;

    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":flux_beam")
    public static Block fluxBeamBlock;

    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":marble")
    public static Block marble;

    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":marble_bricks")
    public static Block marble_bricks;

    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":marble_smooth")
    public static Block marble_smooth;

    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":marble_pilar")
    public static Block marble_pilar;

    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":marble_slab")
    public static Block marbleSlabBlock;

    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":blacktech")
    public static Block blackmarble_techpat;

    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":invisible_door")
    public static Block invisibleDoorBlock;

    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":warper")
    public static Block warperBlock;

    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":elevator")
    public static Block elevatorBlock;

    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":flatlight")
    public static Block flatLightBlock;

    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":level_marker")
    public static Block levelMarkerBlock;

    @GameRegistry.ObjectHolder(ArienteWorld.ARIENTE_MODID + ":slope")
    public static Block slopeBlock;
}
