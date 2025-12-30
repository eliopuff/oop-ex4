package pepse.world.trees;
import danogl.util.Vector2;
import pepse.world.Block;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class Flora {
    private static final int TREE_DIST = 125;
    private static final float PLACEMENT_PROBABILITY = 0.1f;
    private static final int TREE_HEIGHT = 10;
    private final Function<Float, Float> groundHeightFunction;
    private final int seed;
    private final float cycleLength;


    public Flora(int seed, Function<Float, Float> groundHeightFunction, float cycleLength) {
        this.groundHeightFunction = groundHeightFunction;
        this.seed = seed;
        this.cycleLength = cycleLength;
    }

    public List<Tree> createInRange(int minX, int maxX) {
        int realMinX = (minX / Block.SIZE) * Block.SIZE;
        Random random = new Random(seed + realMinX);
        List<Tree> trees = new ArrayList<>();
        for (int x = realMinX; x <= maxX; x += Block.SIZE){
            if (random.nextFloat() < PLACEMENT_PROBABILITY) {
                float height =
                        (float) (Math.floor(groundHeightFunction.apply((float)x) / Block.SIZE) * Block.SIZE);
                trees.add(new Tree(new Vector2(x, height), TREE_HEIGHT, x + seed, cycleLength));
            }
        }
        return trees;
    }

}
