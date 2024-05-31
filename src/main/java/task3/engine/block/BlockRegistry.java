package task3.engine.block;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class BlockRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlockRegistry.class);
    private static final HashMap<Integer, Class<? extends Block>> registeredBlocks = new HashMap<>();

    public enum Blocks {
        BEDROCK
    }

    static {
        register(Blocks.BEDROCK, BedrockBlock.class);
    }

    public static Block getBlockById(int x, int y, Blocks id) {
        return getBlockById(x, y, id.ordinal());
    }

    public static Block getBlockById(int x, int y, int id) {
        Class<? extends Block> blockClass = registeredBlocks.get(id);
        if (blockClass == null) return null;
        try {
            return blockClass.getDeclaredConstructor(new Class[]{Integer.class, Integer.class}).newInstance(x, y);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            LOGGER.error(e.toString());
        }
        return null;
    }

    private static void register(Blocks id, Class<? extends Block> blockClass) {
        registeredBlocks.put(id.ordinal(), blockClass);
    }
}
