package mcjty.arienteworld;

import mcjty.arienteworld.config.WorldgenConfiguration;
import mcjty.arienteworld.dimension.EditMode;
import mcjty.arienteworld.dimension.EditModeClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientForgeEventHandlers {

    private int ambienceTicks = 3000;

    @SubscribeEvent
    public void onRenderWorldEvent(RenderWorldLastEvent event) {
        if (EditMode.editMode) {
            EditModeClient.renderPart(event.getPartialTicks());
        }
    }

    @SubscribeEvent
    public void onWorldtick(TickEvent event) {
        World world = ArienteWorld.proxy.getClientWorld();
        ambienceTicks--;
        if (ambienceTicks <= 0) {
            if (world.provider.getDimension() == WorldgenConfiguration.DIMENSION_ID.get()) {
                EntityPlayer player = ArienteWorld.proxy.getClientPlayer();
                world.playSound(player, player.getPosition(), ModSounds.ambient, SoundCategory.AMBIENT, 0.5f,
                        0.8F + world.rand.nextFloat() * 0.2F);
            }
            this.ambienceTicks = world.rand.nextInt(12000) + 6000;
        }
    }

}
