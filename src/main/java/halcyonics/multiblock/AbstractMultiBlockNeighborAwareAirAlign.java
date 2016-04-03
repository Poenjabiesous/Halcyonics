package halcyonics.multiblock;

import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import halcyonics.enums.MultiBlockEnum;
import halcyonics.enums.MultiBlockEnumNeighborAware;

/**
 * Created by Niel on 3/29/2016.
 */
public abstract class AbstractMultiBlockNeighborAwareAirAlign extends AbstractMultiBlockNeighborAware {
    public AbstractMultiBlockNeighborAwareAirAlign(String unlocalizedName) {
        super(unlocalizedName);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

        if (((MultiBlockEnum.EnumType) state.getValue(STRUCTURE)).getID() == 1) {

            if (worldIn.getBlockState(pos.offset(EnumFacing.UP)).getBlock() instanceof BlockAir
                    || worldIn.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock() instanceof BlockAir) {
                return state.withProperty(STRUCTURE, MultiBlockEnum.EnumType.FORMED).withProperty(ORIENTATION, MultiBlockEnumNeighborAware.EnumType.VERTICAL);
            }

            if (worldIn.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() instanceof BlockAir
                    || worldIn.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() instanceof BlockAir) {
                return state.withProperty(STRUCTURE, MultiBlockEnum.EnumType.FORMED).withProperty(ORIENTATION, MultiBlockEnumNeighborAware.EnumType.HORIZONTALZ);
            }

            if (worldIn.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() instanceof BlockAir
                    || worldIn.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() instanceof BlockAir) {
                return state.withProperty(STRUCTURE, MultiBlockEnum.EnumType.FORMED).withProperty(ORIENTATION, MultiBlockEnumNeighborAware.EnumType.HORIZONTALX);
            }
            return state.withProperty(STRUCTURE, MultiBlockEnum.EnumType.FORMED).withProperty(ORIENTATION, MultiBlockEnumNeighborAware.EnumType.UNFORMED);
        }
        return state.withProperty(STRUCTURE, MultiBlockEnum.EnumType.UNFORMED).withProperty(ORIENTATION, MultiBlockEnumNeighborAware.EnumType.UNFORMED);
    }
}
