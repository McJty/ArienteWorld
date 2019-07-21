package mcjty.arienteworld.setup;

import mcjty.arienteworld.ClientForgeEventHandlers;
import mcjty.arienteworld.ModSounds;
import mcjty.lib.setup.DefaultClientProxy;
import net.minecraft.client.audio.MusicTicker;
import net.minecraftforge.client.EnumHelperClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends DefaultClientProxy {

    public static MusicTicker.MusicType arienteMusic;

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        MinecraftForge.EVENT_BUS.register(new ClientForgeEventHandlers());
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
        arienteMusic = EnumHelperClient.addMusicType("ariente_music", ModSounds.music, 12000, 24000);
    }
}
