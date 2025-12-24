package pepse.world.avatar;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Avatar extends GameObject {
    private static final String IDLE_PATH = "assets/idle_0.png";
    private static final String[] IDLE_PATHS = {
            "assets/idle_0.png",
            "assets/idle_1.png",
            "assets/idle_2.png",
            "assets/idle_3.png"};
    private static final String[] JUMP_PATHS = {
            "assets/jump_0.png",
            "assets/jump_1.png",
            "assets/jump_2.png",
            "assets/jump_3.png"};
    private static final String[] RUN_PATHS = {
            "assets/run_0.png",
            "assets/run_1.png",
            "assets/run_2.png",
            "assets/run_3.png",
            "assets/run_4.png",
            "assets/run_5.png"};
    private static final float TIME_BETWEEN_FRAMES = 0.1f;

    private final Renderable idleRenderable, runRenderable, jumpRenderable;
    private final UserInputListener inputListener;
    public Avatar(Vector2 topLeftCorner,
                  UserInputListener inputListener,
                  ImageReader imageReader){
        super(Vector2.ZERO,
                Vector2.ONES,
                new AnimationRenderable(IDLE_PATHS, imageReader, true, TIME_BETWEEN_FRAMES));

        this.idleRenderable = this.renderer().getRenderable();
        this.runRenderable = new AnimationRenderable(RUN_PATHS, imageReader, true, TIME_BETWEEN_FRAMES);
        this.jumpRenderable = new AnimationRenderable(JUMP_PATHS, imageReader, false, TIME_BETWEEN_FRAMES);

        this.inputListener = inputListener;

    }
}
