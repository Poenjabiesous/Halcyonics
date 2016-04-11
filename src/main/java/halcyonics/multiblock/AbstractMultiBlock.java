package halcyonics.multiblock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import halcyonics.enums.MultiBlockEnum;
import halcyonics.Halcyonics;

import java.util.List;

/**
 * Created by Niel on 3/21/2016.
 */
public abstract class AbstractMultiBlock extends Block {

    public static final PropertyEnum STRUCTURE = PropertyEnum.create("structure", MultiBlockEnum.EnumType.class);

    public AbstractMultiBlock(String unlocalizedName) {
        super(Material.rock);
        this.setCreativeTab(Halcyonics.halcyonicsTab);
        this.setResistance(2.0F);
        this.setHardness(2.0F);
        this.setHarvestLevel("pickaxe", 0);
        this.setStepSound(Block.soundTypeStone);
        this.setUnlocalizedName(unlocalizedName);
        this.setDefaultState(this.blockState.getBaseState().withProperty(STRUCTURE, MultiBlockEnum.EnumType.UNFORMED));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(STRUCTURE, meta == 0 ? MultiBlockEnum.EnumType.UNFORMED : MultiBlockEnum.EnumType.FORMED);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        MultiBlockEnum.EnumType type = (MultiBlockEnum.EnumType) state.getValue(STRUCTURE);
        return type.getID();
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[]{STRUCTURE});
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
        list.add(new ItemStack(itemIn, 1, 0));
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if (worldIn.getBlockState(pos.offset(side.getOpposite())) != iblockstate) {
            return true;
        }

        if (block == this) {
            return false;
        }

        if (worldIn.getBlockState(pos.offset(side.getOpposite())).getBlock() instanceof AbstractMultiBlock) {
            return false;
        }
        return block == this ? false : super.shouldSideBeRendered(worldIn, pos, side);
    }

    public abstract boolean isValidForFrame();

    public abstract boolean isValidForPanel();

    public abstract boolean isValidForContent();

    @Override
    public boolean canRenderInLayer(EnumWorldBlockLayer layer) {
        if (layer == EnumWorldBlockLayer.SOLID) {
            return true;
        }
        return false;
    }
}

