package mcjty.arienteworld.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    static {
        AIConfiguration.init(COMMON_BUILDER, CLIENT_BUILDER);
        WorldgenConfiguration.init(COMMON_BUILDER, CLIENT_BUILDER);
        LootConfiguration.init(COMMON_BUILDER, CLIENT_BUILDER);
    }

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;


    public static String[] ASSETS = new String[] {
            "/assets/arienteworld/citydata/city0_0.json",
            "/assets/arienteworld/citydata/city0_1.json",
            "/assets/arienteworld/citydata/city1_1.json",
            "/assets/arienteworld/citydata/city1_2.json",
            "/assets/arienteworld/citydata/city2_1.json",
            "/assets/arienteworld/citydata/city3_1.json",
            "/assets/arienteworld/citydata/city4_1.json",
            "/assets/arienteworld/citydata/city5_1.json",
            "/assets/arienteworld/citydata/general.json",
            "/assets/arienteworld/citydata/station.json",
            "/assets/arienteworld/citydata/landscapecities.json",
            "$ariente/userassets.json"
    };

    public static int SHIELD_PANEL_LIFE = 100;

    // @todo 1.15
//    public static Configuration mainConfig;

    public static void init() {
//        mainConfig = new Configuration(new File(ArienteWorld.setup.getModConfigDir().getPath(), "arienteworld.cfg"));
        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

//    public static void postInit() {
//        if (mainConfig.hasChanged()) {
//            mainConfig.save();
//        }
//    }
}
