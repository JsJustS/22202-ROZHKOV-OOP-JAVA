package task3.engine.block;

import task3.util.ResourceManager;

public class DiamondOreBlock extends Block {
    DiamondOreBlock(Integer x, Integer y) {
        super(x, y);
        this.blastResistance = 1;
        this.isCollidable = true;
        this.sprite = ResourceManager.loadImage("img/block/diamond.png");
        this.points = 10;
    }
}
