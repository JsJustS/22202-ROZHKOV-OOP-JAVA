package task3.engine.block;

import task3.util.ResourceManager;

public class BrickBlock extends Block {
    BrickBlock(Integer x, Integer y) {
        super(x, y);
        this.blastResistance = 10;
        this.isCollidable = true;
        this.sprite = ResourceManager.loadImage("img/block/brick.png");
    }
}
