package mcjty.arienteworld;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModSounds {

    public static final SoundEvent music = new SoundEvent(new ResourceLocation(ArienteWorld.MODID, "music")).setRegistryName(new ResourceLocation(ArienteWorld.MODID, "music"));
    public static final SoundEvent ambient = new SoundEvent(new ResourceLocation(ArienteWorld.MODID, "ambient")).setRegistryName(new ResourceLocation(ArienteWorld.MODID, "ambient"));

    public static void init(IForgeRegistry<SoundEvent> registry) {
        registry.register(music);
        registry.register(ambient);
    }

}
