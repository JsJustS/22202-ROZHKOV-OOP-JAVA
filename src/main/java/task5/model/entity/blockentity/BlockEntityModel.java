package task5.model.entity.blockentity;

import task5.model.entity.EntityModel;
import task5.model.entity.EntityType;
import task5.model.entity.RenderLayer;

public class BlockEntityModel extends EntityModel {
    protected int blastResistance = 0;
    protected int points = 0;
    protected String spritePath;
    protected Block type;

    public BlockEntityModel() {
        setWidth(1);
        setHeight(1);
        setRenderLayer(RenderLayer.BLOCKS);
        this.type = Block.AIR;
        this.entityType = EntityType.Block;
    }

    @Override
    public String getSpritePath() {
        return spritePath;
    }

    public void setSpritePath(String spritePath) {
        this.spritePath = spritePath;
    }

    public int getBlastResistance() {
        return blastResistance;
    }

    public void setBlastResistance(int blastResistance) {
        this.blastResistance = blastResistance;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Block getType() {
        return type;
    }

    public void setType(Block type) {
        this.type = type;
    }
}
