package pepse.world.avatar;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.util.function.Supplier;

public class EnergyViewer{
    private static final String PREFIX = "Energy: ";
    private static final String SUFFIX = "%";
    private static final Vector2 DIMENSIONS = new Vector2(50,20);

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
