package pepse.world.avatar;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;

public class Avatar extends GameObject {
    private static final String IDLE_PATH = "assets/idle_0.png";
    public Avatar(Vector2 topLeftCorner,
                  UserInputListener inputListener,
                  ImageReader imageReader){
        super(Vector2.ZERO,
                Vector2.ONES,
                imageReader.readImage(IDLE_PATH, true));


    }
}
