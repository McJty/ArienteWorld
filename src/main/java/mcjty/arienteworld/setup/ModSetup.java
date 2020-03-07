package mcjty.arienteworld.setup;

import mcjty.ariente.api.IArienteMod;
import mcjty.ariente.api.IArienteSystem;
import mcjty.arienteworld.ArienteWorld;
import mcjty.arienteworld.ForgeEventHandlers;
import mcjty.arienteworld.cities.AssetRegistries;
import mcjty.arienteworld.config.Config;
import mcjty.arienteworld.dimension.features.FeatureRegistry;
import mcjty.arienteworld.oregen.WorldGen;
import mcjty.lib.compat.MainCompatHandler;
import mcjty.lib.setup.DefaultModSetup;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

public class ModSetup extends DefaultModSetup {

    public static IArienteSystem arienteSystem;

    public ModSetup() {
        createTab("arienteworld", () -> new ItemStack(Blocks.BEDROCK));
    }   // @todo 1.15

    @Override
    public void init(FMLCommonSetupEvent e) {
        super.init(e);

//        MinecraftForge.EVENT_BUS.register(WorldTickHandler.instance); // @todo 1.15
//        MinecraftForge.TERRAIN_GEN_BUS.register(new TerrainEventHandlers());  // @todo 1.15
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandlers());

        WorldGen.init();
        FeatureRegistry.init();
//        ArienteMessages.registerMessages("arienteWorld");

        BiomeDictionary.addTypes(Registration.ARIENTE_CITY.get(), BiomeDictionary.Type.SPARSE);
        BiomeDictionary.addTypes(Registration.ARIENTE_FOREST.get(), BiomeDictionary.Type.FOREST);
        BiomeDictionary.addTypes(Registration.ARIENTE_HILLS.get(), BiomeDictionary.Type.HILLS);
        BiomeDictionary.addTypes(Registration.ARIENTE_ROUGH.get(), BiomeDictionary.Type.DEAD, BiomeDictionary.Type.HILLS);
        BiomeDictionary.addTypes(Registration.ARIENTE_OCEAN.get(), BiomeDictionary.Type.DEAD, BiomeDictionary.Type.OCEAN);
        BiomeDictionary.addTypes(Registration.ARIENTE_PLAINS.get(), BiomeDictionary.Type.SPARSE);

        for (Biome biome : ForgeRegistries.BIOMES) {
            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)) {
                biome.addFeature(GenerationStage.Decoration.RAW_GENERATION, Registration.DUNGEON_FEATURE.get()
                        .withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
                        .withPlacement(Registration.DUNGEON_PLACEMENT.get().configure(IPlacementConfig.NO_PLACEMENT_CONFIG))
                );
            }
        }

        readAssets();
    }

    @Override
    protected void setupModCompat() {
        MainCompatHandler.registerWaila();
        MainCompatHandler.registerTOP();
//        HoloGuiCompatibility.register();
        arienteSystem = ModList.get()
                .getModContainerById("ariente")
                .map(ModContainer::getMod)
                .filter(mod -> mod instanceof IArienteMod)
                .map(mod -> ((IArienteMod) mod).getSystem())
                .orElseThrow(() -> new RuntimeException("Cannot find Ariente mod!"));
    }

    private void readAssets() {
        AssetRegistries.reset();
        for (String path : Config.ASSETS) {
            if (path.startsWith("/")) {
                try(InputStream inputstream = ArienteWorld.class.getResourceAsStream(path)) {
                    AssetRegistries.load(inputstream, path);
                } catch (IOException ex) {
                    throw new UncheckedIOException(ex);
                }
            } else if (path.startsWith("$")) {
//                File file = new File(getModConfigDir().getPath() + File.separator + path.substring(1));
//                AssetRegistries.load(file);
            } else {
                throw new RuntimeException("Invalid path for ariente resource in 'assets' config!");
            }
        }
    }
}
