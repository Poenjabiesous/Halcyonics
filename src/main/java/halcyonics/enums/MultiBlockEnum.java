package halcyonics.enums;

import net.minecraft.util.IStringSerializable;

/**
 * Created by Niel on 3/24/2016.
 */
public class MultiBlockEnum {

    public enum EnumType implements IStringSerializable {
        UNFORMED(0, "unformed"),
        FORMED(1, "formed");

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
