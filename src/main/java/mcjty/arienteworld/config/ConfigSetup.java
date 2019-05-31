package mcjty.arienteworld.config;

import mcjty.arienteworld.ArienteWorld;
import mcjty.lib.thirteen.ConfigSpec;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigSetup {

    private static final ConfigSpec.Builder SERVER_BUILDER = new ConfigSpec.Builder();
    private static final ConfigSpec.Builder CLIENT_BUILDER = new ConfigSpec.Builder();

    static {
        AIConfiguration.init(SERVER_BUILDER, CLIENT_BUILDER);
        WorldgenConfiguration.init(SERVER_BUILDER, CLIENT_BUILDER);
        LootConfiguration.init(SERVER_BUILDER, CLIENT_BUILDER);
    }

    public static ConfigSpec SERVER_CONFIG;
    public static ConfigSpec CLIENT_CONFIG;


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

    public static Configuration mainConfig;

    public static void init() {
        mainConfig = new Configuration(new File(ArienteWorld.setup.getModConfigDir().getPath(), "arienteworld.cfg"));
        SERVER_CONFIG = SERVER_BUILDER.build(mainConfig);
        CLIENT_CONFIG = CLIENT_BUILDER.build(mainConfig);
    }

    public static void postInit() {
        if (mainConfig.hasChanged()) {
            mainConfig.save();
        }
    }
}
