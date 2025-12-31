package pepse.world;

import pepse.world.trees.Tree;

import java.util.List;

/**
 * A class representing a chunk of the game world, containing blocks and trees.
 */
public class Chunk
{
    private final int id;
    private final List<Block> blocks;
    private final List<Tree> trees;

    /**
     * Constructs a Chunk object with the specified id, blocks, and trees.
     *
     * @param id     The unique identifier for the chunk.
     * @param blocks The list of blocks in the chunk.
     * @param trees  The list of trees in the chunk.
     */
    public Chunk(int id, List<Block> blocks, List<Tree> trees)
    {
        this.id = id;
        this.blocks = blocks;
        this.trees = trees;
    }

    /**
     * Returns the unique identifier of the chunk.
     *
     * @return The chunk's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the list of blocks in the chunk.
     *
     * @return The list of blocks.
     */
    public List<Block> getBlocks() {
        return blocks;
    }

    /**
     * Returns the list of trees in the chunk.
     *
     * @return The list of trees.
     */
    public List<Tree> getTrees() {
        return trees;
    }
}
