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

