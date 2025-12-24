package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.utils.ColorSupplier;

import java.awt.*;
import java.util.List;

public class Terrain {
    private static final int TERRAIN_DEPTH = 20;
    private static final float HEIGHT_FACTOR = (2/3f);
    private static final Color BASE_GROUND_COLOR = new Color(212,
            123, 74);
    private static final String BLOCK_SUB_TAG = "Block_sub";
    private static final String BLOCK_TOP_TAG = "Block_top";

    private final float groundHeightAtX0;

    public Terrain(Vector2 windowDimensions, int seed){
        groundHeightAtX0 = getGroundHeightAtX0(windowDimensions);
    }

    public static float getGroundHeightAtX0(Vector2 windowDimensions) {
        return windowDimensions.y() * HEIGHT_FACTOR;
    }

    public float groundHeightAt(float x) {
        return groundHeightAtX0;
    }

    public List<Block> createInRange(int minX, int maxX) {
        Renderable rectangle = new RectangleRenderable(ColorSupplier.approximateColor(
                BASE_GROUND_COLOR));
        List<Block> blocks = new java.util.ArrayList<>();
        int realMinX = (minX / Block.SIZE) * Block.SIZE;
        for (int x = realMinX; x <= maxX; x += Block.SIZE){
            float height = (float) (Math.floor(groundHeightAt(x) / Block.SIZE) * Block.SIZE);

            Block topBlock = new Block(new Vector2(x, height), rectangle);
            topBlock.setTag(BLOCK_TOP_TAG);
            blocks.add(topBlock);

            float startY = height - Block.SIZE;
            float endY = height + TERRAIN_DEPTH*Block.SIZE;
            for (float y = startY; y < endY; y+= Block.SIZE){
                Block block = new Block(new Vector2(x, y), rectangle);
                block.setTag(BLOCK_SUB_TAG);
                blocks.add(block);
            }
        }
        return blocks;
    }
}