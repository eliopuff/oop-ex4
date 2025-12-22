package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class Sun {
    public static GameObject create(Vector2 windowDimensions, float
            cycleLength){
        Renderable renderable = new OvalRenderable(Color.YELLOW);
        GameObject sun = new GameObject(
                Vector2.ZERO, new Vector2(50,50), renderable);
        sun.setTag("sun");
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        return sun;
    }
}
