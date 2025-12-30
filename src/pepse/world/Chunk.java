package pepse.world;

import pepse.world.trees.Tree;

import java.util.List;

public class Chunk
{
    private final int id;
    private final List<Block> blocks;
    private final List<Tree> trees;

    public Chunk(int id, List<Block> blocks, List<Tree> trees)
    {
        this.id = id;
        this.blocks = blocks;
        this.trees = trees;
    }

    public int getId() {
        return id;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public List<Tree> getTrees() {
        return trees;
    }
}
