package mcjty.arienteworld.setup;


import mcjty.arienteworld.ArienteWorld;
import mcjty.arienteworld.ClientForgeEventHandlers;
import net.minecraft.client.audio.MusicTicker;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ArienteWorld.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistration {

    public static MusicTicker.MusicType arienteMusic;

    @SubscribeEvent
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
