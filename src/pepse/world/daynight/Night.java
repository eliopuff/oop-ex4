package pepse.world.daynight;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class Night {
    public static GameObject create(Vector2 windowDimensions, float
            cycleLength){
        Renderable blackScreen = new RectangleRenderable(Color.BLACK);
        GameObject night = new GameObject(
                Vector2.ZERO, windowDimensions, blackScreen);
        night.setTag("night");
        return night;
    }
}