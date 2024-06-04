package task3.engine.block;

import task3.util.ResourceManager;

public class GoldBlock extends Block {
    GoldBlock(Integer x, Integer y) {
        super(x, y);
        this.blastResistance = 1;
        this.isCollidable = true;
        this.sprite = ResourceManager.loadImage("img/block/gold.png");
        this.points = 5;
    }
}
