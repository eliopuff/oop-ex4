package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;

import java.util.List;


public class PepseGameManager extends GameManager {
    private static final int CYCLE_LENGTH = 30;
    private static final int LAYER_NIGHT = Layer.UI-1;

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
        Terrain terrain = new Terrain(windowController.getWindowDimensions(),0);
        List<Block> blocks = terrain.createInRange(0, 20460);
        for (Block block : blocks) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
        gameObjects().addGameObject(Night.create(windowController.getWindowDimensions(),CYCLE_LENGTH),
                LAYER_NIGHT);
    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
