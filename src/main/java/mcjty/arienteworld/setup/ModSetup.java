package mcjty.arienteworld.setup;

import mcjty.ariente.api.IArienteMod;
import mcjty.arienteworld.ArienteWorld;
import mcjty.arienteworld.ForgeEventHandlers;
import mcjty.arienteworld.TerrainEventHandlers;
import mcjty.arienteworld.blocks.ModBlocks;
import mcjty.arienteworld.cities.AssetRegistries;
import mcjty.arienteworld.config.ConfigSetup;
import mcjty.arienteworld.dimension.DimensionRegister;
import mcjty.arienteworld.oregen.WorldGen;
import mcjty.arienteworld.oregen.WorldTickHandler;
import mcjty.lib.compat.MainCompatHandler;
import mcjty.lib.setup.DefaultModSetup;
import mcjty.lib.varia.Logging;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

public class ModSetup extends DefaultModSetup {

    public static IArienteMod arienteMod;

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);

        MinecraftForge.EVENT_BUS.register(WorldTickHandler.instance);
        MinecraftForge.TERRAIN_GEN_BUS.register(new TerrainEventHandlers());
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandlers());
        NetworkRegistry.INSTANCE.registerGuiHandler(ArienteWorld.instance, new GuiProxy());

        DimensionRegister.init();
        ModBlocks.init();
        WorldGen.init();
//        ArienteMessages.registerMessages("arienteWorld");
    }

    @Override
    protected void setupModCompat() {
        MainCompatHandler.registerWaila();
        MainCompatHandler.registerTOP();
//        HoloGuiCompatibility.register();
        for (ModContainer container : Loader.instance().getModList()) {
            if (ArienteWorld.ARIENTE_MODID.equals(container.getModId())) {
                if (container.getMod() instanceof IArienteMod) {
                    arienteMod = (IArienteMod) container.getMod();
                } else {
                    Logging.logError("Cannot find a valid Ariente mod!");
                }
                break;
            }
        }
    }

    @Override
    protected void setupConfig() {
        ConfigSetup.init();
    }

    @Override
    public void createTabs() {
        createTab("arienteWorld", () -> new ItemStack(ModBlocks.glowleaves));
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        AssetRegistries.reset();
        for (String path : ConfigSetup.ASSETS) {
            if (path.startsWith("/")) {
                try(InputStream inputstream = ArienteWorld.class.getResourceAsStream(path)) {
                    AssetRegistries.load(inputstream, path);
                } catch (IOException ex) {
                    throw new UncheckedIOException(ex);
                }
            } else if (path.startsWith("$")) {
                File file = new File(getModConfigDir().getPath() + File.separator + path.substring(1));
                AssetRegistries.load(file);
            } else {
                throw new RuntimeException("Invalid path for ariente resource in 'assets' config!");
            }
        }
    }
}
