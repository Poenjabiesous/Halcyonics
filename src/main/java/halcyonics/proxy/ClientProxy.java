package halcyonics.proxy;


import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import halcyonics.handler.BlockRenderRegisterHandler;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLInitializationEvent e) {
        super.preInit(e);
        BlockRenderRegisterHandler.init();
    }
}