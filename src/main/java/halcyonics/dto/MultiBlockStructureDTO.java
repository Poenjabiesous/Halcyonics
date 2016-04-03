package halcyonics.dto;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Niel on 4/2/2016.
 */
public class MultiBlockStructureDTO {

    private String lastErrorMessage;
    private ArrayList<BlockPos> reactorBlocks;
    private HashMap<BlockPos, TileEntity> workingStructureBlocks;
    private BlockPos structureSize;
    private BlockPos minPos;
    private BlockPos maxPos;
    private boolean structureCorrect;



    public MultiBlockStructureDTO() {
        this.lastErrorMessage = "";
        this.reactorBlocks = new ArrayList<BlockPos>();
        this.workingStructureBlocks = new HashMap<BlockPos, TileEntity>();
        this.structureSize = new BlockPos(0, 0, 0);
        this.minPos = new BlockPos(0, 0, 0);
        this.maxPos = new BlockPos(0, 0, 0);
        this.structureCorrect = false;
    }

    public void set(MultiBlockStructureDTO structure)
    {
        this.lastErrorMessage = structure.getLastErrorMessage();
        this.reactorBlocks = structure.getReactorBlocks();
        this.workingStructureBlocks = structure.getWorkingStructureBlocks();
        this.structureSize = structure.getStructureSize();
        this.minPos = structure.getMinPos();
        this.maxPos = structure.getMaxPos();
        this.structureCorrect = structure.isStructureCorrect();
    }

    public String getLastErrorMessage() {
        return this.lastErrorMessage;
    }

    public void setLastErrorMessage(String lastErrorMessage) {
        this.lastErrorMessage = lastErrorMessage;
    }

    public ArrayList<BlockPos> getReactorBlocks() {
        return reactorBlocks;
    }

    public void setReactorBlocks(ArrayList<BlockPos> reactorBlocks) {
        this.reactorBlocks = reactorBlocks;
    }

    public HashMap<BlockPos, TileEntity> getWorkingStructureBlocks() {
        return workingStructureBlocks;
    }

    public void setWorkingStructureBlocks(HashMap<BlockPos, TileEntity> workingStructureBlocks) {
        this.workingStructureBlocks = workingStructureBlocks;
    }

    public BlockPos getStructureSize() {
        return structureSize;
    }

    public void setStructureSize(BlockPos structureSize) {
        this.structureSize = structureSize;
    }

    public BlockPos getMinPos() {
        return minPos;
    }

    public void setMinPos(BlockPos minPos) {
        this.minPos = minPos;
    }

    public BlockPos getMaxPos() {
        return maxPos;
    }

    public void setMaxPos(BlockPos maxPos) {
        this.maxPos = maxPos;
    }

    public boolean isStructureCorrect() {
        return structureCorrect;
    }

    public void setStructureCorrect(boolean structureCorrect) {
        this.structureCorrect = structureCorrect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MultiBlockStructureDTO that = (MultiBlockStructureDTO) o;

        if (structureCorrect != that.structureCorrect) return false;
        if (!lastErrorMessage.equals(that.lastErrorMessage)) return false;
        if (!reactorBlocks.equals(that.reactorBlocks)) return false;
        if (!workingStructureBlocks.equals(that.workingStructureBlocks)) return false;
        if (!structureSize.equals(that.structureSize)) return false;
        if (!minPos.equals(that.minPos)) return false;
        return maxPos.equals(that.maxPos);
    }

    @Override
    public int hashCode() {
        int result = lastErrorMessage.hashCode();
        result = 31 * result + reactorBlocks.hashCode();
        result = 31 * result + workingStructureBlocks.hashCode();
        result = 31 * result + structureSize.hashCode();
        result = 31 * result + minPos.hashCode();
        result = 31 * result + maxPos.hashCode();
        result = 31 * result + (structureCorrect ? 1 : 0);
        return result;
    }

    public TileEntity getTileEntityForBlockPos(BlockPos pos) {
        return getWorkingStructureBlocks().get(pos);
    }

    public void invalidateStructure() {
        this.lastErrorMessage = "";
        this.reactorBlocks.clear();
        this.workingStructureBlocks.clear();
        this.structureSize = new BlockPos(0, 0, 0);
        this.minPos = new BlockPos(0, 0, 0);
        this.maxPos = new BlockPos(0, 0, 0);
        this.structureCorrect = false;
    }
}
