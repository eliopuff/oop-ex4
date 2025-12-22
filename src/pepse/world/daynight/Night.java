package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class Night {
    private static final float MIDDAY_OPACITY = 0f;
    private static final float MIDNIGHT_OPACITY = 0.5f;
    private static final float CYCLE_FACTOR = 0.5f;
    public static GameObject create(Vector2 windowDimensions, float
            cycleLength){
        Renderable blackScreen = new RectangleRenderable(Color.BLACK);
        GameObject night = new GameObject(
                Vector2.ZERO, windowDimensions, blackScreen);
        night.setTag("night");

        new Transition<Float>(
                night, night.renderer()::setOpaqueness, MIDDAY_OPACITY, MIDNIGHT_OPACITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT, cycleLength * CYCLE_FACTOR,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null
        );

        return night;
    }
}