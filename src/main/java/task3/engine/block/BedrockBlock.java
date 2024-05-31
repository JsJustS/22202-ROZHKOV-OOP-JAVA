package task3.engine.block;

import task3.util.ResourceManager;

public class BedrockBlock extends Block {

    BedrockBlock(Integer x, Integer y) {
        super(x, y);
        this.blastResistance = 100;
        this.isCollidable = true;
        this.sprite = ResourceManager.loadImage("img/block/bedrock.png");
    }
}
