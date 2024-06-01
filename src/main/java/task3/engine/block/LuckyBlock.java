package task3.engine.block;

import task3.util.ResourceManager;

public class LuckyBlock extends Block {
    LuckyBlock(Integer x, Integer y) {
        super(x, y);
        this.blastResistance = 1;
        this.isCollidable = true;
        this.sprite = ResourceManager.loadImage("img/block/luckyblock.png");
    }
}
