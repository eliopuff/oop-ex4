package pepse.world.avatar;

import danogl.gui.ImageReader;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;

public class Run implements AvatarStrategy{
    private static final String[] IMAGE_PATHS = {
            "assets/run_0.png",
            "assets/run_1.png",
            "assets/run_2.png",
            "assets/run_3.png",
            "assets/run_4.png",
            "assets/run_5.png",
    };
    private static final float TIME_BETWEEN_FRAMES = 0.1f;

    private final Renderable renderable;

    public Run(ImageReader imageReader){
        this.renderable = new AnimationRenderable(IMAGE_PATHS, imageReader, true, TIME_BETWEEN_FRAMES);
    }

    @Override
    public Renderable getRenderable() {
        return renderable;
    }
}
