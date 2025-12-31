package pepse.world.avatar;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.util.function.Supplier;

/**
 * A class representing an energy viewer for the avatar.
 */
public class EnergyViewer{
    private static final String PREFIX = "Energy: ";
    private static final String SUFFIX = "%";
    private static final Vector2 DIMENSIONS = new Vector2(50,20);

    /**
     * Creates an energy viewer GameObject that displays the avatar's energy level.
     *
     * @param topLeftCorner  The top-left corner of the energy viewer.
     * @param energyProvider A supplier that provides the current energy level of the avatar.
     * @return A GameObject representing the energy viewer.
     */
    public static GameObject create(Vector2 topLeftCorner,
                                    Supplier<Integer> energyProvider) {
        TextRenderable textRenderable = new TextRenderable(
                PREFIX + energyProvider.get() + SUFFIX);
        GameObject viewer = new GameObject(topLeftCorner, DIMENSIONS, textRenderable);
        viewer.addComponent((float deltaTime) -> textRenderable
                .setString(PREFIX + energyProvider.get() + SUFFIX));
        viewer.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        return viewer;
    }
}
