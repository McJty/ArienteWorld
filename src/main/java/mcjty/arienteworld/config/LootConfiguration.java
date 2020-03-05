package mcjty.arienteworld.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class LootConfiguration {

    private static final String CATEGORY_LOOT = "loot";

    public static ForgeConfigSpec.DoubleValue SOLDIER_CITYKEY_CHANCE;

    public static void init(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        SERVER_BUILDER.comment("Loot settings").push(CATEGORY_LOOT);

        SOLDIER_CITYKEY_CHANCE = SERVER_BUILDER
                .comment("The chance that a killed soldier will drop a keycard")
                .defineInRange("soldierCitykeyChance", .2, 0, 1);
        SERVER_BUILDER.pop();
    }
}
