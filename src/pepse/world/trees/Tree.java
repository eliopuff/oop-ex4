package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.GameObjectPhysics;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Block;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tree {
    private static final Color LOG_COLOR = new Color(100, 50, 20);
    private static final Color LEAF_COLOR = new Color(50, 200, 30);
    private static final Color FRUIT_COLOR = new Color(200, 50, 50);
    private static final Renderable LOG_RENDERABLE = new RectangleRenderable(LOG_COLOR);
    private static final Renderable LEAF_RENDERABLE = new RectangleRenderable(LEAF_COLOR);
    private static final Renderable FRUIT_RENDERABLE = new OvalRenderable(FRUIT_COLOR);
    private static final String LOG_TAG = "log";

    /**
     * Tag for identifying fruit type of tree objects
     */
    public static final String FRUIT_TAG = "fruit";
    private static final String LEAF_TAG = "leaf";
    private static final String EATEN_TAG = "eaten";
    private static final int NUM_LEAVES_SIZE = 7;
    private static final int LEAF_SIZE = 25;
    private static final float LEAF_PROBABILITY = 0.7f;
    private static final int FRUIT_SIZE = 15;
    private static final float FRUIT_PROBABILITY = 0.05f;

    private GameObject log;
    private final ArrayList<GameObject> leaves = new ArrayList<>();
    private final ArrayList<GameObject> fruits = new ArrayList<>();

    // Getters and setters

    /**  Get the log of the tree
     * @return the log GameObject
     */
    public GameObject getLog(){
        return this.log;
    }

    /**  Set the log of the tree
     * @param log the log GameObject to set
     */
    public void setLog(GameObject log){
        this.log = log;
    }

    /**  Get the leaves of the tree
     * @return a list of leaf GameObjects
     */
    public List<GameObject> getLeaves(){
        return new ArrayList<>(this.leaves);
    }

    private void addLeaf(GameObject leaf){
        this.leaves.add(leaf);
    }

    /**  Get the fruits of the tree
     * @return a list of fruit GameObjects
     */
    public List<GameObject> getFruits(){
        return new ArrayList<>(this.fruits);
    }

    private void addFruit(GameObject fruit){
        this.fruits.add(fruit);
    }

    /**  Constructor for the Tree class
     * @param bottom the bottom position of the tree
     * @param height the height of the tree in blocks
     * @param seed the seed for random generation
     * @param cycleLength the cycle length for fruit regrowth
     */
    public Tree(Vector2 bottom, int height, int seed, float cycleLength) {
        Vector2 logTopLeft = new Vector2(bottom.x(), bottom.y() - height*Block.SIZE - Block.SIZE);
        Random random = new Random(seed);
        this.setLog(createLog(logTopLeft, height));
        for(int row = 0; row < NUM_LEAVES_SIZE; row++){
            for(int col = 0; col < NUM_LEAVES_SIZE; col++){
                Vector2 leafTopLeft = new Vector2(
                        logTopLeft.x() - (float) (Block.SIZE * NUM_LEAVES_SIZE) /2 + col * Block.SIZE,
                        logTopLeft.y() - (float) (Block.SIZE * NUM_LEAVES_SIZE) /2 + row * Block.SIZE);
                if(random.nextFloat() < LEAF_PROBABILITY) {

                    this.addLeaf(createLeaf(leafTopLeft, random));
                }
                if (random.nextFloat() < FRUIT_PROBABILITY) {
                    this.addFruit(createFruit(leafTopLeft, cycleLength));
                }
            }
        }
    }

    private static GameObject createLog(Vector2 topLeftCorner, int height){
        GameObject log = new GameObject(topLeftCorner, new Vector2(Block.SIZE, height*Block.SIZE),
                LOG_RENDERABLE);
        log.setTag(LOG_TAG);
        log.physics().preventIntersectionsFromDirection(Vector2.ZERO);
        log.physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        return log;
    }

    private GameObject createLeaf(Vector2 topLeftCorner, Random random){
        GameObject leaf = new GameObject(topLeftCorner, new Vector2(LEAF_SIZE, LEAF_SIZE), LEAF_RENDERABLE);
        leaf.setTag(LEAF_TAG);
        Runnable transitionTask = () -> {
        new Transition<>(leaf, leaf.renderer()::setRenderableAngle, -8f, 8f,
                Transition.CUBIC_INTERPOLATOR_FLOAT, 1.5f,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
        new Transition<>(leaf, leaf::setDimensions, new Vector2(LEAF_SIZE, LEAF_SIZE),
                new Vector2(LEAF_SIZE * 1.2f, LEAF_SIZE * 1.2f),
                Transition.CUBIC_INTERPOLATOR_VECTOR, 2f,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
        };
        new ScheduledTask(leaf, random.nextFloat(), false, transitionTask);
        return leaf;
    }

    private GameObject createFruit(Vector2 topLeftCorner, float cycleLength){
        GameObject fruit = new GameObject(topLeftCorner, new Vector2(FRUIT_SIZE, FRUIT_SIZE),
                FRUIT_RENDERABLE) {
            @Override
            public void onCollisionExit(GameObject other) {
                super.onCollisionExit(other);
                this.setTag(EATEN_TAG);
                this.renderer().setRenderable(null);
                new ScheduledTask(this, cycleLength, false,
                        () -> {setTag(FRUIT_TAG);
                    renderer().setRenderable(FRUIT_RENDERABLE);}
                );
            }
        };
        fruit.setTag(FRUIT_TAG);
        return fruit;
    }

}
