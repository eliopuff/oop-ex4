package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.avatar.Avatar;
import pepse.world.avatar.EnergyViewer;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;

import java.util.List;


public class PepseGameManager extends GameManager {
    private static final int CYCLE_LENGTH = 30;
    private static final int LAYER_NIGHT = Layer.UI-2;
    private static final int LAYER_SUN = Layer.BACKGROUND+2;
    private static final int LAYER_SUN_HALO = Layer.BACKGROUND+1;
    private static final int BLOCK_RANGE_MAX = 20460;

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
        Terrain terrain = new Terrain(windowController.getWindowDimensions(),0);
        List<Block> blocks = terrain.createInRange(0, BLOCK_RANGE_MAX);
        for (Block block : blocks) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
        gameObjects().addGameObject(Night.create(windowController.getWindowDimensions(),CYCLE_LENGTH),
                LAYER_NIGHT);
        GameObject sun = Sun.create(windowController.getWindowDimensions(),CYCLE_LENGTH);
        gameObjects().addGameObject(sun, LAYER_SUN);
        gameObjects().addGameObject(SunHalo.create(sun), LAYER_SUN_HALO);
        Avatar avatar = new Avatar(Vector2.ZERO, inputListener, imageReader);
        gameObjects().addGameObject(avatar, Layer.DEFAULT);
        GameObject energyViewer = EnergyViewer.create(Vector2.ZERO, avatar::getEnergy);
        gameObjects().addGameObject(energyViewer, Layer.UI);
    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
