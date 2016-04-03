package halcyonics.handler;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {

    private static int auraicColliderMaxWidth;
    private static int auraicColliderMaxHeight;
    private static int auraicColliderMaxStorage;
    private static int auraicColliderMaxIO;
    private static int euPowerConversionRatio;
    private static int powerMultiplier;
    private static int stabilityMultiplier;
    private static int coolingMultiplier;

    public static void init(File configFile) {
        Configuration config = new Configuration(configFile);
        config.load();

        auraicColliderMaxWidth = config.getInt("auraicColliderMaxWidth", "MultiBlockStructures", 15, 3, 100, "The maximum horizontal size of the Auraic Collider multiblock structure");
        auraicColliderMaxHeight = config.getInt("auraicColliderMaxHeight", "MultiBlockStructures", 15, 3, 100, "The maximum vertical size of the Auraic Collider multiblock structure");
        euPowerConversionRatio = config.getInt("euPowerConversionRatio", "PowerGeneration", 7, 1, 100, "RF -> EU conversion ratio.");
        powerMultiplier = config.getInt("powerMultiplier", "PowerGeneration", 2, 1, 100, "Power generation multiplier");
        stabilityMultiplier = config.getInt("stabilityMultiplier", "PowerGeneration", 1, 1, 100, "Collider stability multiplier");
        coolingMultiplier = config.getInt("coolingMultiplier", "PowerGeneration", 2, 1, 100, "Collider cooling multiplier");

        auraicColliderMaxStorage = config.getInt("auraicColliderMaxStorage", "PowerStorage", 1000000, 1000000, 99999999, "Collider max energy storage");
        auraicColliderMaxIO = config.getInt("auraicColliderMaxIO", "PowerStorage", 5000, 1000000, 99999999, "Collider max energy IO");

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

    public static int getPowerMultiplier() {
        return powerMultiplier;
    }

    public static int getStabilityMultiplier() {
        return stabilityMultiplier;
    }

    public static int getCoolingMultiplier() {
        return coolingMultiplier;
    }

    public static int getAuraicColliderMaxStorage() {
        return auraicColliderMaxStorage;
    }

    public static int getAuraicColliderMaxIO() {
        return auraicColliderMaxIO;
    }
}
