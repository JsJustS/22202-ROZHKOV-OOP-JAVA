package task3.engine.block;

public class AirBlock extends Block {
    AirBlock(int x, int y) {
        super(x, y);
        this.blastResistance = 0;
        this.isCollidable = false;
    }
}
