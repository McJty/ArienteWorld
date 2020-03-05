package mcjty.arienteworld.setup;


import mcjty.arienteworld.ArienteWorld;
import mcjty.arienteworld.biomes.*;
import mcjty.arienteworld.blocks.DummyBlock;
import mcjty.arienteworld.blocks.plants.BlockArientePlant;
import mcjty.arienteworld.dimension.ArienteModDimension;
import mcjty.arienteworld.oregen.OverworldDungeonFeature;
import mcjty.arienteworld.oregen.OverworldDungeonPlacement;
import mcjty.lib.blocks.BaseBlock;
import mcjty.lib.blocks.RotationType;
import mcjty.lib.builder.BlockBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static mcjty.ariente.Ariente.MODID;

public class Registration {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<Biome> BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, MODID);
    public static final DeferredRegister<SoundEvent> SOUNDS = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, MODID);
    public static final DeferredRegister<ModDimension> DIMENSIONS = new DeferredRegister<>(ForgeRegistries.MOD_DIMENSIONS, MODID);
    public static final DeferredRegister<Feature<?>> FEATURES = new DeferredRegister<>(ForgeRegistries.FEATURES, MODID);
    public static final DeferredRegister<Placement<?>> PLACEMENTS = new DeferredRegister<>(ForgeRegistries.DECORATORS, MODID);

    public static Item.Properties createStandardProperties() {
        return new Item.Properties().group(ArienteWorld.setup.getTab());
    }

    public static void register() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BIOMES.register(FMLJavaModLoadingContext.get().getModEventBus());
        SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        DIMENSIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FEATURES.register(FMLJavaModLoadingContext.get().getModEventBus());
        PLACEMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<OverworldDungeonPlacement> DUNGEON_PLACEMENT = PLACEMENTS.register("overworld_dungeon", OverworldDungeonPlacement::new);
    public static final RegistryObject<OverworldDungeonFeature> DUNGEON_FEATURE = FEATURES.register("overworld_dungeon", OverworldDungeonFeature::new);

    public static final RegistryObject<BiomeArientePlains> ARIENTE_PLAINS = BIOMES.register("ariente_plains", ModBiomes::createBiomePlains);
    public static final RegistryObject<BiomeArienteHills> ARIENTE_HILLS = BIOMES.register("ariente_hills", ModBiomes::createBiomeHills);
    public static final RegistryObject<BiomeArienteOcean> ARIENTE_OCEAN = BIOMES.register("ariente_ocean", ModBiomes::createBiomeOcean);
    public static final RegistryObject<BiomeArienteForest> ARIENTE_FOREST = BIOMES.register("ariente_forest", ModBiomes::createBiomeForest);
    public static final RegistryObject<BiomeArienteRough> ARIENTE_ROUGH = BIOMES.register("ariente_rough", ModBiomes::createBiomeRough);
    public static final RegistryObject<BiomeArienteCity> ARIENTE_CITY = BIOMES.register("ariente_city", ModBiomes::createBiomeCity);

    public static final RegistryObject<BaseBlock> GUARD_DUMMY = BLOCKS.register("guard_dummy", () -> new DummyBlock(new BlockBuilder()));
    public static final RegistryObject<Item> GUARD_DUMMY_ITEM = ITEMS.register("guard_dummy", () -> new BlockItem(GUARD_DUMMY.get(), createStandardProperties()));

    public static final RegistryObject<BaseBlock> SOLDIER_DUMMY = BLOCKS.register("solider_dummy", () -> new DummyBlock(new BlockBuilder()));
    public static final RegistryObject<Item> SOLDIER_DUMMY_ITEM = ITEMS.register("solider_dummy", () -> new BlockItem(SOLDIER_DUMMY.get(), createStandardProperties()));

    public static final RegistryObject<BaseBlock> MASTER_SOLDIER_DUMMY = BLOCKS.register("master_solider_dummy", () -> new DummyBlock(new BlockBuilder()));
    public static final RegistryObject<Item> MASTER_SOLDIER_DUMMY_ITEM = ITEMS.register("master_solider_dummy", () -> new BlockItem(MASTER_SOLDIER_DUMMY.get(), createStandardProperties()));

    public static final RegistryObject<BaseBlock> GLOWLOG = BLOCKS.register("glowlog", () -> new BaseBlock(new BlockBuilder().properties(Block.Properties.create(Material.WOOD).lightValue(10))) {
        @Override
        public RotationType getRotationType() {
            return RotationType.NONE;
        }
    });
    public static final RegistryObject<Item> GLOWLOG_ITEM = ITEMS.register("glowlog", () -> new BlockItem(GLOWLOG.get(), createStandardProperties()));

    public static final RegistryObject<BaseBlock> GLOWLEAVES = BLOCKS.register("glowleaves", () -> new BaseBlock(new BlockBuilder().properties(Block.Properties.create(Material.LEAVES).lightValue(10))) {
        @Override
        public RotationType getRotationType() {
            return RotationType.NONE;
        }
    });
    public static final RegistryObject<Item> GLOWLEAVES_ITEM = ITEMS.register("glowleaves", () -> new BlockItem(GLOWLEAVES.get(), createStandardProperties()));

    public static final RegistryObject<BaseBlock> BLUELOG = BLOCKS.register("bluelog", () -> new BaseBlock(new BlockBuilder().properties(Block.Properties.create(Material.WOOD))) {
        @Override
        public RotationType getRotationType() {
            return RotationType.NONE;
        }
    });
    public static final RegistryObject<Item> BLUELOG_ITEM = ITEMS.register("bluelog", () -> new BlockItem(BLUELOG.get(), createStandardProperties()));

    public static final RegistryObject<BaseBlock> BLUELEAVES = BLOCKS.register("blueleaves", () -> new BaseBlock(new BlockBuilder().properties(Block.Properties.create(Material.LEAVES))) {
        @Override
        public RotationType getRotationType() {
            return RotationType.NONE;
        }
    });
    public static final RegistryObject<Item> BLUELEAVES_ITEM = ITEMS.register("blueleaves", () -> new BlockItem(BLUELEAVES.get(), createStandardProperties()));

    public static final RegistryObject<BaseBlock> DARKLEAVES = BLOCKS.register("darkleaves", () -> new BaseBlock(new BlockBuilder().properties(Block.Properties.create(Material.LEAVES))) {
        @Override
        public RotationType getRotationType() {
            return RotationType.NONE;
        }
    });
    public static final RegistryObject<Item> DARKLEAVES_ITEM = ITEMS.register("darkleaves", () -> new BlockItem(DARKLEAVES.get(), createStandardProperties()));

    public static final RegistryObject<BlockArientePlant> BLACKBUSH = BLOCKS.register("black_bush", BlockArientePlant::new);
    public static final RegistryObject<Item> BLACKBUSH_ITEM = ITEMS.register("black_bush", () -> new BlockItem(BLACKBUSH.get(), createStandardProperties()));

    public static final RegistryObject<BlockArientePlant> DARKGRASS = BLOCKS.register("dark_grass", BlockArientePlant::new);
    public static final RegistryObject<Item> DARKGRASS_ITEM = ITEMS.register("dark_grass", () -> new BlockItem(DARKGRASS.get(), createStandardProperties()));

    public static final RegistryObject<BlockArientePlant> SMALLFLOWER = BLOCKS.register("small_flower", BlockArientePlant::new);
    public static final RegistryObject<Item> SMALLFLOWER_ITEM = ITEMS.register("small_flower", () -> new BlockItem(SMALLFLOWER.get(), createStandardProperties()));

    public static final RegistryObject<Block> LAPISORE = BLOCKS.register("lapisore", () -> new Block(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Item> LAPISORE_ITEM = ITEMS.register("lapisore", () -> new BlockItem(LAPISORE.get(), createStandardProperties()));

    public static final RegistryObject<Block> GLOWSTONE = BLOCKS.register("glowstoneore", () -> new Block(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Item> GLOWSTONE_ITEM = ITEMS.register("glowstoneore", () -> new BlockItem(GLOWSTONE.get(), createStandardProperties()));

    public static final RegistryObject<ArienteModDimension> DIMENSION = DIMENSIONS.register("ariente", ArienteModDimension::new);
}

