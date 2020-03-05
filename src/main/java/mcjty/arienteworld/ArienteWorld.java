package mcjty.arienteworld;


import mcjty.arienteworld.apiimpl.ArienteWorldImplementation;
import mcjty.arienteworld.config.Config;
import mcjty.arienteworld.setup.ModSetup;
import mcjty.arienteworld.setup.Registration;
import mcjty.hologui.api.IHoloGuiHandler;
import mcjty.lib.base.ModBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(ArienteWorld.MODID)
public class ArienteWorld implements ModBase {
    public static final String MODID = "arienteworld";
    public static final String ARIENTE_MODID = "ariente";

    public static ModSetup setup = new ModSetup();

    public static ArienteWorld instance;
    public static ArienteWorldImplementation arienteWorldImplementation = new ArienteWorldImplementation();

    public static IHoloGuiHandler guiHandler;

    public ArienteWorld() {
        // This has to be done VERY early
//        FluidRegistry.enableUniversalBucket();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        Registration.register();

        FMLJavaModLoadingContext.get().getModEventBus().addListener((FMLCommonSetupEvent event) -> setup.init(event));
    }


    // @todo 1.15
//    @Mod.EventHandler
//    public void imcCallback(FMLInterModComms.IMCEvent event) {
//        for (FMLInterModComms.IMCMessage message : event.getMessages()) {
//            if (message.key.equalsIgnoreCase("getArienteWorld")) {
//                Optional<Function<IArienteWorld, Void>> value = message.getFunctionValue(IArienteWorld.class, Void.class);
//                if (value.isPresent()) {
//                    value.get().apply(arienteWorldImplementation);
//                } else {
//                    setup.getLogger().warn("Some mod didn't return a valid result with getArienteWorld!");
//                }
//            }
//        }
//    }

    @Override
    public String getModId() {
        return ArienteWorld.MODID;
    }

    @Override
    public void openManual(PlayerEntity player, int bookindex, String page) {
        // @todo
    }
}
