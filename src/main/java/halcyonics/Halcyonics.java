package halcyonics;


import halcyonics.handler.BlocksHandler;
import halcyonics.handler.GuiHandler;
import halcyonics.thaumref.ThaumResearch;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import halcyonics.proxy.CommonProxy;
import halcyonics.handler.ConfigHandler;
import halcyonics.reference.ModInfo;
import halcyonics.handler.TileEntityHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;


@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, acceptedMinecraftVersions = "1.8.9", dependencies = ModInfo.DEPENDENCIES)
public class Halcyonics {
    @Mod.Instance(ModInfo.ID)
    public static Halcyonics instance;

    @SidedProxy(clientSide = "halcyonics.proxy.ClientProxy", serverSide = "halcyonics.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static CreativeTabs halcyonicsTab;
    private GuiHandler guiHandler;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        guiHandler = new GuiHandler();
        halcyonicsTab = new HalcyonicsTab(ModInfo.NAME);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        BlocksHandler.init();
        TileEntityHandler.init();
        proxy.preInit(event);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, guiHandler);
        ThaumResearch.init();
    }
}