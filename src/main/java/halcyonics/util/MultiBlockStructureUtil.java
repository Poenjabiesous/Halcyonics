package halcyonics.util;

import com.google.common.collect.Lists;
import halcyonics.tileEntity.ColliderBlockControllerTileEntity;
import halcyonics.tileEntity.MultiBlockEnergyOutputTileEntity;
import halcyonics.tileEntity.SlaveMultiBlockTileEntity;
import net.minecraft.block.BlockAir;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import halcyonics.multiblock.AbstractMultiBlock;
import halcyonics.blocks.ColliderBlockController;
import halcyonics.dto.MultiBlockStructureDTO;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Niel on 3/28/2016.
 */
public class MultiBlockStructureUtil {

    public static MultiBlockStructureDTO checkStructure(World world, BlockPos pos, BlockPos minSize, BlockPos maxSize) {

        MultiBlockStructureDTO structureDTO = new MultiBlockStructureDTO();

        BlockPos lowestY = getReactorOffsetFromBlockPos(world, pos, EnumFacing.DOWN);

        BlockPos minPos = new BlockPos(getReactorOffsetFromBlockPos(world, lowestY, EnumFacing.WEST).getX(),
                getReactorOffsetFromBlockPos(world, lowestY, EnumFacing.DOWN).getY(),
                getReactorOffsetFromBlockPos(world, lowestY, EnumFacing.NORTH).getZ());
        structureDTO.setMinPos(minPos);

        BlockPos maxPos = new BlockPos(getReactorOffsetFromBlockPos(world, lowestY, EnumFacing.EAST).getX(),
                getReactorOffsetFromBlockPos(world, lowestY, EnumFacing.UP).getY(),
                getReactorOffsetFromBlockPos(world, lowestY, EnumFacing.SOUTH).getZ());
        structureDTO.setMaxPos(maxPos);

        BlockPos size = new BlockPos(Math.abs(maxPos.getX() - minPos.getX()) + 1, Math.abs(maxPos.getY() - minPos.getY()) + 1, Math.abs(maxPos.getZ() - minPos.getZ()) + 1);

        structureDTO.setStructureSize(size);

        if (size.getX() < minSize.getX() - 1 || size.getY() < minSize.getY() - 1 || size.getZ() < minSize.getZ() - 1) {
            structureDTO.setStructureCorrect(false);
            structureDTO.setLastErrorMessage("Reactor too small");
            return structureDTO;
        }

        if (size.getX() > maxSize.getX() + 1 || size.getY() > maxSize.getY() + 1 || size.getZ() > maxSize.getZ() + 1) {
            structureDTO.setStructureCorrect(false);
            structureDTO.setLastErrorMessage("Reactor too large");
            return structureDTO;
        }

        ArrayList<BlockPos> reactorBlocksWithAir = Lists.newArrayList(BlockPos.getAllInBox(minPos, maxPos).iterator());
        ArrayList<BlockPos> reactorBlocks = new ArrayList<BlockPos>();
        reactorBlocks.addAll(reactorBlocksWithAir);


        for (BlockPos block : reactorBlocksWithAir) {

            if (isFrame(minPos, maxPos, block)) {
                if ((world.getBlockState(block).getBlock() instanceof AbstractMultiBlock) && ((AbstractMultiBlock) world.getBlockState(block).getBlock()).isValidForFrame()) {
                    continue;
                } else {
                    structureDTO.setStructureCorrect(false);
                    structureDTO.setLastErrorMessage(block.toString() + " not valid for frame.");
                    return structureDTO;
                }
            }

            if (isPanel(minPos, maxPos, block)) {

                //if this is the controller that's running this.
                if (block.equals(pos)) {
                    continue;
                }

                //if there's another controller, this should not work.
                if (world.getBlockState(block).getBlock() instanceof ColliderBlockController) {
                    structureDTO.setStructureCorrect(false);
                    structureDTO.setLastErrorMessage(block.toString() + ": structure can only have one controller.");
                    return structureDTO;
                }

                if ((world.getBlockState(block).getBlock() instanceof AbstractMultiBlock) && ((AbstractMultiBlock) world.getBlockState(block).getBlock()).isValidForPanel()) {
                    continue;
                } else {
                    structureDTO.setStructureCorrect(false);
                    structureDTO.setLastErrorMessage(block.toString() + " not valid for panel.");
                    return structureDTO;
                }
            }

            if (world.getBlockState(block).getBlock() instanceof BlockAir) {
                reactorBlocks.remove(block);
            }
        }

        HashMap<BlockPos, TileEntity> reactorTileEntities = new HashMap<>();

        int energyPorts = 0;
        for (BlockPos block : reactorBlocks) {
            if (world.getTileEntity(block) != null) {
                reactorTileEntities.put(block, world.getTileEntity(block));
            }

            if (world.getTileEntity(block) instanceof MultiBlockEnergyOutputTileEntity) {
                energyPorts++;
            }
        }

        if(energyPorts > 1)
        {
            structureDTO.setStructureCorrect(false);
            structureDTO.setLastErrorMessage("Structure can only have a single Energy Port.");
            return structureDTO;
        }

        structureDTO.setLastErrorMessage("None");
        structureDTO.setStructureCorrect(true);
        structureDTO.setReactorBlocks(reactorBlocks);
        structureDTO.setWorkingStructureBlocks(reactorTileEntities);

        return structureDTO;
    }

    public static MultiBlockStructureDTO checkMadeStructure(World world, MultiBlockStructureDTO structure) {

        for (BlockPos block : structure.getReactorBlocks()) {
            if ((world.getBlockState(block).getBlock() instanceof AbstractMultiBlock)) {
                continue;
            } else {
                structure.setStructureCorrect(false);
                return structure;
            }
        }
        return structure;
    }

    public static BlockPos getReactorOffsetFromBlockPos(World world, BlockPos pos, EnumFacing offset) {
        BlockPos iteratablePos = new BlockPos(pos);
        while (true) {
            if (world.getBlockState(iteratablePos).getBlock() instanceof AbstractMultiBlock) {
                iteratablePos = iteratablePos.offset(offset);
            } else {
                iteratablePos = iteratablePos.offset(offset.getOpposite());
                break;
            }
        }
        return iteratablePos;
    }

    public static void setReactorStructureActive(World world, MultiBlockStructureDTO structure, ColliderBlockControllerTileEntity controller) {
        if (structure != null) {
            for (BlockPos block : structure.getReactorBlocks()) {

                if (world.getTileEntity(block) instanceof SlaveMultiBlockTileEntity) {
                    ((SlaveMultiBlockTileEntity) world.getTileEntity(block)).setMaster(controller);
                }

                if ((world.getBlockState(block).getBlock() instanceof AbstractMultiBlock) && ((AbstractMultiBlock) world.getBlockState(block).getBlock()).isValidForPanel()) {
                    world.setBlockState(block, world.getBlockState(block).getBlock().getStateFromMeta(1), 2);
                }

                if (world.getTileEntity(block) instanceof MultiBlockEnergyOutputTileEntity) {
                    world.getTileEntity(block).validate();
                }

            }
        }
    }

    public static void setReactorStructureInactive(World world, MultiBlockStructureDTO structure) {
        if (structure != null) {
            for (BlockPos block : structure.getReactorBlocks()) {

                if (world.getTileEntity(block) instanceof SlaveMultiBlockTileEntity) {
                    ((SlaveMultiBlockTileEntity) world.getTileEntity(block)).setMaster(null);
                }

                if (world.getTileEntity(block) instanceof MultiBlockEnergyOutputTileEntity) {
                    world.getTileEntity(block).invalidate();
                }

                if ((world.getBlockState(block).getBlock() instanceof AbstractMultiBlock) && ((AbstractMultiBlock) world.getBlockState(block).getBlock()).isValidForPanel()) {
                    world.setBlockState(block, world.getBlockState(block).getBlock().getStateFromMeta(0), 2);
                }
            }
        }
    }

    public static boolean isPanel(BlockPos from, BlockPos to, BlockPos block) {
        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();

        if (x == from.getX() || x == to.getX()) {
            return true;
        }

        if (y == from.getY() || y == to.getY()) {
            return true;
        }

        if (z == from.getZ() || z == to.getZ()) {
            return true;
        }
        return false;
    }

    public static boolean isFrame(BlockPos from, BlockPos to, BlockPos block) {

        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();


        if (x == from.getX() && y == from.getY() && z == from.getZ()) {
            return true;
        }

        if (x == to.getX() && y == to.getY() && z == to.getZ()) {
            return true;
        }

        if (x == from.getX() && y == from.getY()) {
            return true;
        }

        if (x == to.getX() && y == to.getY()) {
            return true;
        }

        if (x == from.getX() && z == from.getZ()) {
            return true;
        }

        if (x == to.getX() && z == to.getZ()) {
            return true;
        }

        if (y == from.getY() && z == from.getZ()) {
            return true;
        }

        if (y == to.getY() && z == to.getZ()) {
            return true;
        }

        if (x == from.getX() && y == to.getY()) {
            return true;
        }

        if (x == to.getX() && y == from.getY()) {
            return true;
        }

        if (x == from.getX() && z == to.getZ()) {
            return true;
        }

        if (x == to.getX() && z == from.getZ()) {
            return true;
        }

        if (y == from.getY() && z == to.getZ()) {
            return true;
        }

        return y == to.getY() && z == from.getZ();

    }
}