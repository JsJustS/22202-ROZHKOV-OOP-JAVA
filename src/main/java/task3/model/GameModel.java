package task3.model;

import task3.engine.block.Block;
import task3.engine.entity.Entity;
import task3.view.pubsub.Publisher;

import java.util.ArrayList;

public class GameModel extends Publisher implements IModel {
    private int fieldWidthInBlocks;
    private int fieldHeightInBlocks;

    private ArrayList<Entity> entities;
    private ArrayList<Block> blocks;
}
