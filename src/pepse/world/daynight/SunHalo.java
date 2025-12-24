package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class SunHalo {
    private static final Color SUN_HALO_COLOR = new Color(255, 255, 0, 20);
    private static final Vector2 HALO_DIMENSIONS = new Vector2(200, 200);
    private static final String HALO_TAG = "sun halo";

    public static GameObject create(GameObject sun) {
        Renderable renderable = new OvalRenderable(SUN_HALO_COLOR);
        GameObject halo = new GameObject(Vector2.ZERO, HALO_DIMENSIONS, renderable);

        halo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        halo.addComponent((float deltaTime) -> halo.setCenter(sun.getCenter()));
        halo.setTag(HALO_TAG);
        return  halo;
    }
}
