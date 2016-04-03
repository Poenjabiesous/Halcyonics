package halcyonics.multiblock;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import halcyonics.enums.MultiBlockEnum;
import halcyonics.enums.MultiBlockEnumNeighborAware;

/**
 * Created by Niel on 3/29/2016.
 */
public abstract class AbstractMultiBlockNeighborAware extends AbstractMultiBlock {

    public static final PropertyEnum ORIENTATION = PropertyEnum.create("orientation", MultiBlockEnumNeighborAware.EnumType.class);

    public AbstractMultiBlockNeighborAware(String unlocalizedName) {
        super(unlocalizedName);
        this.setDefaultState(this.blockState.getBaseState().withProperty(STRUCTURE, MultiBlockEnum.EnumType.UNFORMED).withProperty(ORIENTATION, MultiBlockEnumNeighborAware.EnumType.UNFORMED));
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[]{STRUCTURE, ORIENTATION});
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

        if (((MultiBlockEnum.EnumType) state.getValue(STRUCTURE)).getID() == 1) {
            boolean horizontalX = false;
            boolean horizontalZ = false;
            boolean verticalY = false;

            if (worldIn.getBlockState(pos.offset(EnumFacing.UP)).getBlock() instanceof AbstractMultiBlockNeighborAware
                    && worldIn.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock() instanceof AbstractMultiBlockNeighborAware) {
                verticalY = true;
            }

            if (worldIn.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() instanceof AbstractMultiBlockNeighborAware
                    && worldIn.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() instanceof AbstractMultiBlockNeighborAware) {
                horizontalX = true;
            }

            if (worldIn.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() instanceof AbstractMultiBlockNeighborAware
                    && worldIn.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() instanceof AbstractMultiBlockNeighborAware) {
                horizontalZ = true;
            }

            if ((horizontalX && !horizontalZ && !verticalY)) {
                return state.withProperty(STRUCTURE, MultiBlockEnum.EnumType.FORMED).withProperty(ORIENTATION, MultiBlockEnumNeighborAware.EnumType.HORIZONTALX);
            }

            if ((!horizontalX && horizontalZ && !verticalY)) {
                return state.withProperty(STRUCTURE, MultiBlockEnum.EnumType.FORMED).withProperty(ORIENTATION, MultiBlockEnumNeighborAware.EnumType.HORIZONTALZ);
            }

            if ((!horizontalX && !horizontalZ && verticalY)) {
                return state.withProperty(STRUCTURE, MultiBlockEnum.EnumType.FORMED).withProperty(ORIENTATION, MultiBlockEnumNeighborAware.EnumType.VERTICAL);
            }

            if ((!horizontalX && !horizontalZ && !verticalY)) {
                return state.withProperty(STRUCTURE, MultiBlockEnum.EnumType.FORMED).withProperty(ORIENTATION, MultiBlockEnumNeighborAware.EnumType.STOP);
            }

            return state.withProperty(STRUCTURE, MultiBlockEnum.EnumType.FORMED).withProperty(ORIENTATION, MultiBlockEnumNeighborAware.EnumType.UNFORMED);

        }
        return state.withProperty(STRUCTURE, MultiBlockEnum.EnumType.UNFORMED).withProperty(ORIENTATION, MultiBlockEnumNeighborAware.EnumType.UNFORMED);
    }
}
