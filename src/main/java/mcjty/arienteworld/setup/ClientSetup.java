package mcjty.arienteworld.setup;


import mcjty.arienteworld.ClientForgeEventHandlers;
import net.minecraft.client.audio.MusicTicker;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static MusicTicker.MusicType arienteMusic;

    public static void init(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ClientForgeEventHandlers());
        // @todo 1.15
//        arienteMusic = EnumHelperClient.addMusicType("ariente_music", ModSounds.music, 12000, 24000);
    }

//    @SubscribeEvent
//    public static void registerModels(ModelRegistryEvent event) {
//        ModBlocks.initModels();
//    }

}
