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

/**
 * The main class of the Pepse game. Manages the initialization and updating of game objects.
 * @author sagig, eliooo
 */
public class PepseGameManager extends GameManager {
    private static final int CYCLE_LENGTH = 30;
    private static final int LAYER_NIGHT = Layer.UI-2;
    private static final int LAYER_SUN = Layer.BACKGROUND+2;
    private static final int LAYER_SUN_HALO = Layer.BACKGROUND+1;
    private static final int LEAF_LAYER =Layer.STATIC_OBJECTS-1 ;

    private Terrain terrain;
    private Avatar avatar;
    private Flora flora;
    private Chunk currentChunk, previousChunk, nextChunk;
    private WindowController windowController;

    /**
     * Initializes the game by setting up the avatar, terrain, flora, and other game objects.
     *
     * @param imageReader      The image reader for loading images.
     * @param soundReader      The sound reader for loading sounds.
     * @param inputListener    The user input listener for handling user inputs.
     * @param windowController The window controller for managing the game window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        this.windowController = windowController;

        // add game objects to the game
        avatar = new Avatar(Vector2.ZERO, inputListener, imageReader);
        gameObjects().addGameObject(avatar, Layer.DEFAULT);

        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, Layer.BACKGROUND);

        GameObject night = Night.create(windowController.getWindowDimensions(), CYCLE_LENGTH);
        gameObjects().addGameObject(night, LAYER_NIGHT);

        GameObject sun = Sun.create(windowController.getWindowDimensions(),CYCLE_LENGTH);
        gameObjects().addGameObject(sun, LAYER_SUN);

        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, LAYER_SUN_HALO);

        GameObject energyViewer = EnergyViewer.create(Vector2.ZERO, avatar::getEnergy);
        gameObjects().addGameObject(energyViewer, Layer.UI);

        // chunk management
        terrain = new Terrain(windowController.getWindowDimensions(),0);
        flora = new Flora(0, terrain::groundHeightAt, CYCLE_LENGTH);
        chunkInit();

        setCamera(new Camera(avatar, Vector2.ZERO,
                windowController.getWindowDimensions(),
                windowController.getWindowDimensions()));
    }



    /**
     * Updates the game state, managing the loading and unloading of terrain chunks based
     * on the avatar's position.
     *
     * @param deltaTime The time elapsed since the last update.
     */

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        int chunkSize = (int)windowController.getWindowDimensions().x();
        int avatarChunkId =
                (int) Math.floor(avatar.getCenter().x() / chunkSize);

        // change chunks if needed
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

    private void chunkInit() {
        currentChunk = createChunk(0);
        previousChunk = createChunk(-1);
        nextChunk = createChunk(1);
        addChunk(previousChunk);
        addChunk(currentChunk);
        addChunk(nextChunk);
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




    /**
     * The main method to run the Pepse game.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
