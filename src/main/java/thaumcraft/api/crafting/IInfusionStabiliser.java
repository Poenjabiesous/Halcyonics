package thaumcraft.api.crafting;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * 
 * @author Azanor
 * 
 * BlocksHandler that implement this interface act as infusion crafting stabilisers like candles and skulls
 *
 */
public interface IInfusionStabiliser {
	
	/**
	 * returns true if the block can stabilise things
	 */
	public boolean canStabaliseInfusion(World world, BlockPos pos);

}
