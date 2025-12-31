package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Terrain;

import java.awt.*;
import java.util.function.Consumer;

/**
 * A class representing the sun in the game world.
 */
public class Sun {
    private static final Vector2 SUN_DIMENSIONS = new Vector2(150, 150);
    private static final String SUN_TAG = "sun";
    private static final float HALF_FLOAT = 0.5f;
    private static final float INITIAL_ANGLE = 0.0f;
    private static final float FINAL_ANGLE = 360.0f;

    /**
     * Creates a sun GameObject that moves in a circular path to simulate day-night cycles.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param cycleLength      The length of a full day-night cycle.
     * @return A GameObject representing the sun.
     */
    public static GameObject create(Vector2 windowDimensions, float
            cycleLength){
        Renderable renderable = new OvalRenderable(Color.YELLOW);
        GameObject sun = new GameObject(
                Vector2.ZERO, SUN_DIMENSIONS, renderable);
        sun.setTag(SUN_TAG);

        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        float groundHeightAtX0 = Terrain.getGroundHeightAtX0(windowDimensions);

        Vector2 initialSunCenter = new Vector2(windowDimensions.x() * HALF_FLOAT,
                groundHeightAtX0 * HALF_FLOAT);
        sun.setCenter(initialSunCenter);
        createSumMovementTransition(windowDimensions, cycleLength, groundHeightAtX0, sun, initialSunCenter);
        return sun;
    }

    private static void createSumMovementTransition(Vector2 windowDimensions, float cycleLength,
                                                    float groundHeightAtX0, GameObject sun,
                                                    Vector2 initialSunCenter) {
        Vector2 cycleCenter = new Vector2(windowDimensions.x() * HALF_FLOAT,
                groundHeightAtX0);

        Consumer<Float> movement = (Float angle) -> sun.setCenter
                (initialSunCenter.subtract(cycleCenter)
                        .rotated(angle)
                        .add(cycleCenter));

        new Transition<>(sun, movement, INITIAL_ANGLE, FINAL_ANGLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT, cycleLength, Transition.TransitionType.TRANSITION_LOOP,
                null);
    }
}

