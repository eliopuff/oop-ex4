package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * A class representing the night overlay in the game world.
 */
public class Night {
    private static final float MIDDAY_OPACITY = 0f;
    private static final float MIDNIGHT_OPACITY = 0.5f;
    private static final float CYCLE_FACTOR = 0.5f;
    private static final String NIGHT_TAG = "night";

    /**
     * Creates a night GameObject that overlays the entire window dimensions and transitions
     * its opacity to simulate day-night cycles.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param cycleLength      The length of a full day-night cycle.
     * @return A GameObject representing the night overlay.
     */
    public static GameObject create(Vector2 windowDimensions, float
            cycleLength){
        Renderable blackScreen = new RectangleRenderable(Color.BLACK);
        GameObject night = new GameObject(
                Vector2.ZERO, windowDimensions, blackScreen);
        night.setTag(NIGHT_TAG);
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        new Transition<>(
                night, night.renderer()::setOpaqueness, MIDDAY_OPACITY, MIDNIGHT_OPACITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT, cycleLength * CYCLE_FACTOR,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null
        );

        return night;
    }
}