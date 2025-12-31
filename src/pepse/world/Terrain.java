package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.utils.ColorSupplier;
import pepse.utils.NoiseGenerator;

import java.awt.*;
import java.util.List;

/**
 * A class representing the terrain of the game world, including ground height and block creation.
 */
public class Terrain {
    private static final int TERRAIN_DEPTH = 20;
    private static final float HEIGHT_FACTOR = (2/3f);
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final Double NOISE_FACTOR = 7.0*Block.SIZE;

    /**
     * Tag for identifying bottom type of terrain blocks
     */
    public static final String BLOCK_SUB_TAG = "Block_sub";
    /**
     * Tag for identifying top type of terrain blocks
     */
    public static final String BLOCK_TOP_TAG = "Block_top";

    private final float groundHeightAtX0;
    private final NoiseGenerator noiseGenerator;

    /**
     * Constructs a Terrain object with the specified window dimensions and seed for noise generation.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param seed             The seed for the noise generator.
     */
    public Terrain(Vector2 windowDimensions, int seed){
        groundHeightAtX0 = getGroundHeightAtX0(windowDimensions);
        noiseGenerator = new NoiseGenerator(seed, (int) groundHeightAtX0);
    }

    /**
     * Calculates the ground height at x = 0 based on the window dimensions.
     *
     * @param windowDimensions The dimensions of the game window.
     * @return The ground height at x = 0.
     */
    public static float getGroundHeightAtX0(Vector2 windowDimensions) {
        return windowDimensions.y() * HEIGHT_FACTOR;
    }

    /**
     * Calculates the ground height at a given x-coordinate using noise generation.
     *
     * @param x The x-coordinate.
     * @return The ground height at the specified x-coordinate.
     */
    public float groundHeightAt(float x) {
        return groundHeightAtX0 + (float) noiseGenerator.noise(x, NOISE_FACTOR);
    }

    /**
     * Creates a list of blocks representing the terrain within the specified x-coordinate range.
     *
     * @param minX The minimum x-coordinate.
     * @param maxX The maximum x-coordinate.
     * @return A list of blocks representing the terrain.
     */
    public List<Block> createInRange(int minX, int maxX) {
        List<Block> blocks = new java.util.ArrayList<>();
        int realMinX = (minX / Block.SIZE) * Block.SIZE;
        for (int x = realMinX; x <= maxX; x += Block.SIZE){
            float height = (float) (Math.floor(groundHeightAt(x) / Block.SIZE) * Block.SIZE);
            Renderable rectangle = new RectangleRenderable(ColorSupplier.approximateColor(
                    BASE_GROUND_COLOR));

            Block topBlock = new Block(new Vector2(x, height), rectangle);
            topBlock.setTag(BLOCK_SUB_TAG);
            blocks.add(topBlock);

            float startY = height - Block.SIZE;
            float endY = height + TERRAIN_DEPTH*Block.SIZE;
            for (float y = startY; y < endY; y+= Block.SIZE){
                rectangle = new RectangleRenderable(ColorSupplier.approximateColor(
                        BASE_GROUND_COLOR));
                Block block = new Block(new Vector2(x, y), rectangle);
                block.setTag(BLOCK_TOP_TAG);
                blocks.add(block);
            }
        }
        return blocks;
    }
}