package pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
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
    private static final Renderable FRUIT_RENDERABLE = new RectangleRenderable(FRUIT_COLOR);
    private static final String LOG_TAG = "log";
    private static final String FRUIT_TAG = "fruit";
    private static final String LEAF_TAG = "leaf";
    private static final int NUM_LEAVES_SIZE = 5;
    private static final int LEAF_SIZE = 20;
    private static final float LEAF_PROBABILITY = 0.7f;

    private GameObject log;
    private final ArrayList<GameObject> leaves = new ArrayList<>();
    private final ArrayList<GameObject> fruits = new ArrayList<>();

    public GameObject getLog(){
        return this.log;
    }

    public void setLog(GameObject log){
        this.log = log;
    }

    public List<GameObject> getLeaves(){
        return new ArrayList<>(this.leaves);
    }

    private void addLeaf(GameObject leaf){
        this.leaves.add(leaf);
    }

    public List<GameObject> getFruits(){
        return new ArrayList<>(this.fruits);
    }

    private void addFruit(GameObject fruit){
        this.fruits.add(fruit);
    }

    public Tree(Vector2 bottom, int height, int seed) {
        Vector2 logTopLeft = new Vector2(bottom.x(), bottom.y() - height*Block.SIZE - Block.SIZE);
        Random random = new Random(seed);
        this.setLog(createLog(logTopLeft, height));
        for(int row = 0; row < NUM_LEAVES_SIZE; row++){
            for(int col = 0; col < NUM_LEAVES_SIZE; col++){
                if(random.nextFloat() < LEAF_PROBABILITY) {
                    Vector2 leafTopLeft = new Vector2(
                            logTopLeft.x() - Block.SIZE + col * Block.SIZE,
                            logTopLeft.y() - Block.SIZE + row * Block.SIZE);
                    this.addLeaf(createLeaf(leafTopLeft));
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

    private static GameObject createLeaf(Vector2 topLeftCorner){
        GameObject leaf = new GameObject(topLeftCorner, new Vector2(LEAF_SIZE, LEAF_SIZE), LEAF_RENDERABLE);
        leaf.setTag(LEAF_TAG);
        return leaf;
    }

}
