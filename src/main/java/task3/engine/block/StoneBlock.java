package task3.engine.block;

import task3.util.ResourceManager;

public class StoneBlock extends Block {
    StoneBlock(Integer x, Integer y) {
        super(x, y);
        this.blastResistance = 5;
        this.isCollidable = true;
        this.sprite = ResourceManager.loadImage("img/block/stone.png");
    }
}