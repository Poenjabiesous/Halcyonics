package halcyonics.handler;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {

    private static int auraicColliderMaxWidth;
    private static int auraicColliderMaxHeight;
    private static int auraicColliderMaxStorage;
    private static int euPowerConversionRatio;
    private static int auraicColliderRFPerOperation;

    public static void init(File configFile) {
        Configuration config = new Configuration(configFile);
        config.load();

        auraicColliderMaxWidth = config.getInt("auraicColliderMaxWidth", "MultiBlockStructures", 15, 3, 100, "The maximum horizontal size of the Auraic Collider multiblock structure");
        auraicColliderMaxHeight = config.getInt("auraicColliderMaxHeight", "MultiBlockStructures", 15, 3, 100, "The maximum vertical size of the Auraic Collider multiblock structure");
        euPowerConversionRatio = config.getInt("euPowerConversionRatio", "PowerGeneration", 7, 1, 100, "RF -> EU conversion ratio.");
        auraicColliderRFPerOperation = config.getInt("auraicColliderRFPerOperation", "PowerGeneration", 100, 1, 99999999, "Auraic Collider's Vis Accelerator RF generation per tick");
        auraicColliderMaxStorage = config.getInt("auraicColliderMaxStorage", "PowerStorage", 1000000, 1000000, 99999999, "Collider max energy storage");

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

    public static int getEuPowerConversionRatio() {
        return euPowerConversionRatio;
    }

    public static int getAuraicColliderRFPerOperation() {
        return auraicColliderRFPerOperation;
    }

    public static void setAuraicColliderRFPerOperation(int auraicColliderRFPerOperation) {
        ConfigHandler.auraicColliderRFPerOperation = auraicColliderRFPerOperation;
    }

    public static int getAuraicColliderMaxStorage() {
        return auraicColliderMaxStorage;
    }
}
