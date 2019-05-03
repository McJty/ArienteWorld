package mcjty.arienteworld;

import mcjty.arienteworld.dimension.EditMode;
import mcjty.arienteworld.dimension.EditModeClient;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientForgeEventHandlers {

    @SubscribeEvent
    public void onRenderWorldEvent(RenderWorldLastEvent event) {
        if (EditMode.editMode) {
            EditModeClient.renderPart(event.getPartialTicks());
        }
    }
}
