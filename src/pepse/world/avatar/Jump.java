package pepse.world.avatar;

import danogl.gui.ImageReader;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;

public class Jump implements AvatarStrategy {
    private static final String[] IMAGE_PATHS = {
            "assets/jump_0.png",
            "assets/jump_1.png",
            "assets/jump_2.png",
            "assets/jump_3.png",
    };
    private static final float TIME_BETWEEN_FRAMES = 0.1f;

    private final Renderable renderable;

    public Jump(ImageReader imageReader){
        this.renderable = new AnimationRenderable(IMAGE_PATHS, imageReader, true, TIME_BETWEEN_FRAMES);
    }

    @Override
    public Renderable getRenderable() {
        return renderable;
    }
}
