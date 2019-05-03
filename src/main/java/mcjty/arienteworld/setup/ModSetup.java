package mcjty.arienteworld.setup;

import mcjty.arienteworld.ArienteWorld;
import mcjty.lib.compat.MainCompatHandler;
import mcjty.lib.setup.DefaultModSetup;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ModSetup extends DefaultModSetup {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);

        NetworkRegistry.INSTANCE.registerGuiHandler(ArienteWorld.instance, new GuiProxy());

//        ArienteMessages.registerMessages("arienteWorld");
    }

    @Override
    protected void setupModCompat() {
        MainCompatHandler.registerWaila();
        MainCompatHandler.registerTOP();
//        HoloGuiCompatibility.register();
    }

    @Override
    protected void setupConfig() {
//        ConfigSetup.init();
    }

    @Override
    public void createTabs() {
        createTab("arienteWorld", () -> new ItemStack(Items.WATER_BUCKET));
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
    }
}
