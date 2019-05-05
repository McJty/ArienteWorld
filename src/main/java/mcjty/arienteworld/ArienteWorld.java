package mcjty.arienteworld;


import mcjty.ariente.api.IArienteWorld;
import mcjty.arienteworld.apiimpl.ArienteWorldImplementation;
import mcjty.arienteworld.commands.*;
import mcjty.arienteworld.setup.ModSetup;
import mcjty.hologui.api.IHoloGuiHandler;
import mcjty.lib.base.ModBase;
import mcjty.lib.proxy.IProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;

import java.util.Optional;
import java.util.function.Function;


@Mod(modid = ArienteWorld.MODID, name = ArienteWorld.MODNAME,
        dependencies =
                "required-after:mcjtylib_ng@[" + ArienteWorld.MIN_MCJTYLIB_VER + ",);" +
                "required-after:hologui@[" + ArienteWorld.MIN_HOLOGUI_VER + ",);" +
                "required-after:ariente@[" + ArienteWorld.MIN_ARIENTE_VER + ",);" +
                "after:forge@[" + ArienteWorld.MIN_FORGE11_VER + ",)",
        acceptedMinecraftVersions = "[1.12,1.13)",
        version = ArienteWorld.VERSION)
public class ArienteWorld implements ModBase {
    public static final String MODID = "arienteworld";
    public static final String ARIENTE_MODID = "ariente";
    public static final String MODNAME = "Ariente World";
    public static final String VERSION = "0.0.20-alpha";
    public static final String MIN_FORGE11_VER = "14.23.3.2694";
    public static final String MIN_MCJTYLIB_VER = "3.5.1";
    public static final String MIN_HOLOGUI_VER = "0.0.7-beta";
    public static final String MIN_ARIENTE_VER = "0.0.20-alpha";

    @SidedProxy(clientSide = "mcjty.arienteworld.setup.ClientProxy", serverSide = "mcjty.arienteworld.setup.ServerProxy")
    public static IProxy proxy;
    public static ModSetup setup = new ModSetup();

    @Mod.Instance
    public static ArienteWorld instance;
    public static ArienteWorldImplementation arienteWorldImplementation = new ArienteWorldImplementation();

    public static IHoloGuiHandler guiHandler;

    public ArienteWorld() {
        // This has to be done VERY early
        FluidRegistry.enableUniversalBucket();
    }


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        setup.preInit(event);
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        setup.init(e);
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        setup.postInit(e);
        proxy.postInit(e);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandSaveCity());
        event.registerServerCommand(new CommandVariant());
        event.registerServerCommand(new CommandEditMode());
        event.registerServerCommand(new CommandFindCity());
        event.registerServerCommand(new CommandInfo());
        event.registerServerCommand(new CommandPacify());
        event.registerServerCommand(new CommandCityCard());
    }

    @Mod.EventHandler
    public void imcCallback(FMLInterModComms.IMCEvent event) {
        for (FMLInterModComms.IMCMessage message : event.getMessages()) {
            if (message.key.equalsIgnoreCase("getArienteWorld")) {
                Optional<Function<IArienteWorld, Void>> value = message.getFunctionValue(IArienteWorld.class, Void.class);
                if (value.isPresent()) {
                    value.get().apply(arienteWorldImplementation);
                } else {
                    setup.getLogger().warn("Some mod didn't return a valid result with getArienteWorld!");
                }
            }
        }
    }

    @Override
    public String getModId() {
        return ArienteWorld.MODID;
    }

    @Override
    public void openManual(EntityPlayer player, int bookindex, String page) {
        // @todo
    }
}
