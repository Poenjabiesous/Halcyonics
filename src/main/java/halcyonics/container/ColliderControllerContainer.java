package halcyonics.container;

import halcyonics.tileEntity.ColliderBlockControllerTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

/**
 * Created by Niel on 1/21/2017.
 */
public class ColliderControllerContainer extends Container {
    ColliderBlockControllerTileEntity tileEntity;


    public ColliderControllerContainer(ColliderBlockControllerTileEntity te) {
        this.tileEntity = te;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tileEntity.isUseableByPlayer(player);
    }
}
