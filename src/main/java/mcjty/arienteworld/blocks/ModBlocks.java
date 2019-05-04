package mcjty.arienteworld.blocks;

import mcjty.arienteworld.blocks.plants.BlockArientePlant;
import mcjty.arienteworld.ArienteWorld;
import mcjty.lib.blocks.BaseBlock;
import mcjty.lib.builder.BaseBlockBuilder;
import mcjty.lib.builder.BlockFlags;
import mcjty.lib.builder.GenericBlockBuilderFactory;
import net.minecraftforge.oredict.OreDictionary;

import static mcjty.lib.blocks.BaseBlock.RotationType.HORIZROTATION;
import static mcjty.lib.blocks.BaseBlock.RotationType.NONE;
import static mcjty.lib.builder.BlockFlags.*;

public class ModBlocks {

    public static BaseBlock guardDummy;
    public static BaseBlock soldierDummy;
    public static BaseBlock masterSoldierDummy;

    public static BaseBlock glowlog;
    public static BaseBlock glowleaves;
    public static BaseBlock bluelog;
    public static BaseBlock blueleaves;
    public static BaseBlock darkleaves;
    public static BlockArientePlant blackBush;
    public static BlockArientePlant darkGrass;
    public static BlockArientePlant smallFlower;

    public static GenericBlockBuilderFactory builderFactory;


    public static void init() {
        builderFactory = new GenericBlockBuilderFactory(ArienteWorld.instance).creativeTabs(ArienteWorld.setup.getTab());

        initPlants();
        initTechnical();
    }

    private static void initTechnical() {
        guardDummy = new BaseBlockBuilder<>(ArienteWorld.instance, "guard_dummy")
                .rotationType(HORIZROTATION)
                .build();
        soldierDummy = new BaseBlockBuilder<>(ArienteWorld.instance, "soldier_dummy")
                .rotationType(HORIZROTATION)
                .build();
        masterSoldierDummy = new BaseBlockBuilder<>(ArienteWorld.instance, "master_soldier_dummy")
                .rotationType(HORIZROTATION)
                .build();
    }

    private static void initPlants() {
        glowlog = new BaseBlockBuilder<>(ArienteWorld.instance, "glowlog")
                .rotationType(NONE)
                .creativeTabs(ArienteWorld.setup.getTab())
                .lightValue(10)
                .flags(NON_OPAQUE, RENDER_TRANSLUCENT, BlockFlags.RENDER_NOSIDES)
                .build();
        glowleaves = new BaseBlockBuilder<>(ArienteWorld.instance, "glowleaves")
                .rotationType(NONE)
                .creativeTabs(ArienteWorld.setup.getTab())
                .lightValue(10)
                .flags(NON_OPAQUE, RENDER_TRANSLUCENT)
                .build();
        bluelog = new BaseBlockBuilder<>(ArienteWorld.instance, "bluelog")
                .rotationType(NONE)
                .creativeTabs(ArienteWorld.setup.getTab())
                .build();
        blueleaves = new BaseBlockBuilder<>(ArienteWorld.instance, "blueleaves")
                .rotationType(NONE)
                .creativeTabs(ArienteWorld.setup.getTab())
                .flags(NON_OPAQUE, RENDER_CUTOUT)
                .build();
        darkleaves = new BaseBlockBuilder<>(ArienteWorld.instance, "darkleaves")
                .rotationType(NONE)
                .creativeTabs(ArienteWorld.setup.getTab())
                .flags(NON_OPAQUE, RENDER_CUTOUT)
                .build();
        blackBush = new BlockArientePlant("black_bush");
        darkGrass = new BlockArientePlant("dark_grass");
        smallFlower = new BlockArientePlant("small_flower");
    }



    public static void initOreDict() {
        OreDictionary.registerOre("logWood", glowlog);
        OreDictionary.registerOre("logWood", bluelog);
    }

    public static void initModels() {
        guardDummy.initModel();
        soldierDummy.initModel();
        masterSoldierDummy.initModel();

        glowlog.initModel();
        glowleaves.initModel();
        bluelog.initModel();
        blueleaves.initModel();
        darkleaves.initModel();
        blackBush.initModel();
        darkGrass.initModel();
        smallFlower.initModel();
    }
}