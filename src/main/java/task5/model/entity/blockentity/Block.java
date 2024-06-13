package task5.model.entity.blockentity;

import java.util.Arrays;
import java.util.List;

public enum Block {
    AIR,
    BEDROCK,
    BRICK,
    STONE,
    COBBLESTONE,
    GOLD,
    DIAMOND;

    public static List<Block> getWallBlocks() {
        return Arrays.asList(BEDROCK, BRICK);
    }

    public static List<Block> getPathBlocks() {
        return Arrays.asList(STONE, COBBLESTONE, GOLD, DIAMOND);
    }
}
