package halcyonics.handler;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {

    private static int auraicColliderMaxWidth;
    private static int auraicColliderMaxHeight;
    private static int auraicColliderRFPerOperation;
    private static int auraicColliderMaxVisPerAccelerator;

    public static void init(File configFile) {
        Configuration config = new Configuration(configFile);
        config.load();

        auraicColliderMaxWidth = config.getInt("auraicColliderMaxWidth", "MultiBlockStructures", 15, 3, 100, "The maximum horizontal size of the Auraic Collider multiblock structure");
        auraicColliderMaxHeight = config.getInt("auraicColliderMaxHeight", "MultiBlockStructures", 15, 3, 100, "The maximum vertical size of the Auraic Collider multiblock structure");
        auraicColliderRFPerOperation = config.getInt("auraicColliderRFPerOperation", "PowerGeneration", 40, 1, 99999999, "Auraic Collider's Vis Accelerator base RF generation per tick");
        auraicColliderMaxVisPerAccelerator = config.getInt("auraicColliderMaxVisPerAccelerator", "PowerGeneration", 10, 1, 99999999, "Auraic Collider's Vis Accelerator max vis per tick per Accelerator.");

        if (config.hasChanged()) {
            config.save();
        }
    }

    public static int getAuraicColliderMaxHeight() {
        return auraicColliderMaxHeight;
    }

    public static int getAuraicColliderMaxWidth() {
        return auraicColliderMaxWidth;
    }

    public static int getAuraicColliderRFPerOperation() {
        return auraicColliderRFPerOperation;
    }

    public static int getAuraicColliderMaxVisPerAccelerator() {
        return auraicColliderMaxVisPerAccelerator;
    }
}
