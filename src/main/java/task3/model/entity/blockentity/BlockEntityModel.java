package task3.model.entity.blockentity;

import task3.model.entity.EntityModel;
import task3.model.entity.RenderLayer;

public class BlockEntityModel extends EntityModel {
    protected int blastResistance = 0;
    protected int points = 0;

    public BlockEntityModel() {
        setWidth(1);
        setHeight(1);
        setRenderLayer(RenderLayer.BLOCKS);
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
}
