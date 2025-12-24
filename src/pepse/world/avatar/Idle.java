package pepse.world.avatar;

import danogl.gui.ImageReader;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;

public class Idle implements AvatarStrategy{
    private static final String[] IDLE_PATHS = {
            "assets/idle_0.png",
            "assets/idle_1.png",
            "assets/idle_2.png",
            "assets/idle_3.png"};
    private static final float TIME_BETWEEN_FRAMES = 0.1f;

    private final Renderable renderable;
    public Idle(ImageReader imageReader){
        this.renderable = new AnimationRenderable(IDLE_PATHS, imageReader, true, TIME_BETWEEN_FRAMES);
    }

    @Override
    public Renderable getRenderable() {
        return renderable;
    }
}
