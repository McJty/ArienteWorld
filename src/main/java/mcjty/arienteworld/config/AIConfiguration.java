package mcjty.arienteworld.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class AIConfiguration {

    private static final String CATEGORY_AI = "ai";

    public static ForgeConfigSpec.IntValue ALERT_TIME;

    public static void init(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        SERVER_BUILDER.comment("AI settings").push(CATEGORY_AI);

        ALERT_TIME = SERVER_BUILDER
                .comment("The amount of ticks (times 10) that the city will stay on alert after spotting a player. So 120 would be one minute")
                .defineInRange("alertTime", 400, 1, 100000000);

        SERVER_BUILDER.pop();
    }
}
