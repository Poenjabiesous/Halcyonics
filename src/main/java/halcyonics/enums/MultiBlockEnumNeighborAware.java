package halcyonics.enums;

import net.minecraft.util.IStringSerializable;

/**
 * Created by Niel on 3/24/2016.
 */
public class MultiBlockEnumNeighborAware {

    public enum EnumType implements IStringSerializable {
        UNFORMED(0, "unformed"),
        STOP(1, "stop"),
        HORIZONTALX(2, "horizontalx"),
        HORIZONTALZ(3, "horizontalz"),
        VERTICAL(4, "vertical");

        private int ID;
        private String name;

        private EnumType(int ID, String name) {
            this.ID = ID;
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return getName();
        }

        public int getID() {
            return ID;
        }
    }

}
