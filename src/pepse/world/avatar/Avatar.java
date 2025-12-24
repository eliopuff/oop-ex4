package pepse.world.avatar;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Avatar extends GameObject {
    private static final float RUN_COST = 2f;
    private static final float JUMP_COST = 20f;
    private static final float DOUBLE_JUMP_COST = 50f;
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;

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
                new Vector2(300,200),
                new AnimationRenderable(IDLE_PATHS, imageReader, true, TIME_BETWEEN_FRAMES));

        this.idleRenderable = this.renderer().getRenderable();
        this.runRenderable = new AnimationRenderable(RUN_PATHS, imageReader, true, TIME_BETWEEN_FRAMES);
        this.jumpRenderable = new AnimationRenderable(JUMP_PATHS, imageReader, false, TIME_BETWEEN_FRAMES);

        this.inputListener = inputListener;

    }

    @Override
    public void update(float deltaTime){
        super.update(deltaTime);
        float xVel = 0;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT))
            xVel -= VELOCITY_X;
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT))
            xVel += VELOCITY_X;
        transform().setVelocityX(xVel);
        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0)
            transform().setVelocityY(VELOCITY_Y);
    }
}
