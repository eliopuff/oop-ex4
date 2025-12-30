package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.Chunk;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.avatar.Avatar;
import pepse.world.avatar.EnergyViewer;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;
import pepse.world.trees.Tree;

import java.util.List;


public class PepseGameManager extends GameManager {
    private static final int CYCLE_LENGTH = 30;
    private static final int LAYER_NIGHT = Layer.UI-2;
    private static final int LAYER_SUN = Layer.BACKGROUND+2;
    private static final int LAYER_SUN_HALO = Layer.BACKGROUND+1;
    private static final int BLOCK_RANGE_MAX = 200;
    private static final int LEAF_LAYER =Layer.STATIC_OBJECTS-1 ;
    private Terrain terrain;
    private Avatar avatar;
    private Flora flora;
    private Chunk currentChunk, previousChunk, nextChunk;
    private WindowController windowController;

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        avatar = new Avatar(Vector2.ZERO, inputListener, imageReader);
//        Vector2 deltaRelativeToAvatar =
//                windowController.getWindowDimensions().mult(0.5f).add( avatar.getTopLeftCorner().mult(-1));
        this.windowController = windowController;
        gameObjects().addGameObject(avatar, Layer.DEFAULT);
        setCamera(new Camera(avatar, Vector2.ZERO,
                windowController.getWindowDimensions(),
                windowController.getWindowDimensions()));
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
        terrain = new Terrain(windowController.getWindowDimensions(),0);

        gameObjects().addGameObject(Night.create(windowController.getWindowDimensions(),CYCLE_LENGTH),
                LAYER_NIGHT);
        GameObject sun = Sun.create(windowController.getWindowDimensions(),CYCLE_LENGTH);
        gameObjects().addGameObject(sun, LAYER_SUN);
        gameObjects().addGameObject(SunHalo.create(sun), LAYER_SUN_HALO);
        GameObject energyViewer = EnergyViewer.create(Vector2.ZERO, avatar::getEnergy);
        gameObjects().addGameObject(energyViewer, Layer.UI);

        flora = new Flora(0, terrain::groundHeightAt, CYCLE_LENGTH);
        currentChunk = createChunk(0);
        previousChunk = createChunk(-1);
        nextChunk = createChunk(1);
        addChunk(previousChunk);
        addChunk(currentChunk);
        addChunk(nextChunk);

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        int chunkSize = (int)windowController.getWindowDimensions().x();
        int avatarChunkId =
                (int) Math.floor(avatar.getCenter().x() / chunkSize);
        if (previousChunk.getId() == avatarChunkId){
            removeChunk(nextChunk);
            nextChunk = currentChunk;
            currentChunk = previousChunk;
            previousChunk = createChunk(avatarChunkId - 1);
            addChunk(previousChunk);
        }
        else if (nextChunk.getId() == avatarChunkId){
            removeChunk(previousChunk);
            previousChunk = currentChunk;
            currentChunk = nextChunk;
            nextChunk = createChunk(avatarChunkId + 1);
            addChunk(nextChunk);
        }
    }

    private Chunk createChunk(int chunkId) {
        int chunkSize = (int)windowController.getWindowDimensions().x();
        return new Chunk(chunkId,
                terrain.createInRange(chunkId * chunkSize,
                        (chunkId + 1) * chunkSize - 1),
                flora.createInRange(chunkId * chunkSize,
                        (chunkId + 1) * chunkSize - 1));
    }

    private void addChunk(Chunk chunk) {
        for (Tree tree : chunk.getTrees()) {
            gameObjects().addGameObject(tree.getLog(), Layer.STATIC_OBJECTS);
            for (GameObject leaf : tree.getLeaves()) {
                gameObjects().addGameObject(leaf, LEAF_LAYER);
            }
            for (GameObject fruit : tree.getFruits()) {
                gameObjects().addGameObject(fruit, Layer.STATIC_OBJECTS);
            }
        }
        for (Block block : chunk.getBlocks()) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
    }

    private void removeChunk(Chunk chunk) {
        for (Tree tree : chunk.getTrees()) {
            gameObjects().removeGameObject(tree.getLog(), Layer.STATIC_OBJECTS);
            for (GameObject leaf : tree.getLeaves()) {
                gameObjects().removeGameObject(leaf, LEAF_LAYER);
            }
            for (GameObject fruit : tree.getFruits()) {
                gameObjects().removeGameObject(fruit, Layer.STATIC_OBJECTS);
            }
        }
        for (Block block : chunk.getBlocks()) {
            gameObjects().removeGameObject(block, Layer.STATIC_OBJECTS);
        }
    }



    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
