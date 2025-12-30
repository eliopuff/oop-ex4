package pepse.world.trees;

import danogl.GameObject;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class Flora {
    private static final int TREE_DIST = 125;
    private static final float PLACEMENT_PROBABILITY = 0.2f;
    private static final int TREE_HEIGHT = 10;
    private final Random random;
    private final Function<Float, Float> groundHeightFunction;
    private final int seed;


    public Flora(int seed, Function<Float, Float> groundHeightFunction) {
        this.random = new Random(seed);
        this.groundHeightFunction = groundHeightFunction;
        this.seed = seed;
    }
    public List<Tree> createInRange(int minX, int maxX) {
        int realMinX = (minX / Block.SIZE) * Block.SIZE;
        List<Tree> trees = new ArrayList<>();
        for (int x = realMinX; x <= maxX; x += Block.SIZE){
            if (random.nextFloat() < PLACEMENT_PROBABILITY) {
                float height =
                        (float) (Math.floor(groundHeightFunction.apply((float)x) / Block.SIZE) * Block.SIZE);
                trees.add(new Tree(new Vector2(x, height), TREE_HEIGHT, x + seed));
            }
        }
        return trees;
    }

}
