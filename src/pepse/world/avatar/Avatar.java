package pepse.world.avatar;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Terrain;
import pepse.world.trees.Tree;

import java.awt.event.KeyEvent;
import java.util.Objects;

public class Avatar extends GameObject {
    private static final int RUN_COST = 2;
    private static final int JUMP_COST = 20;
    private static final int DOUBLE_JUMP_COST = 50;
    private static final int IDLE_GAIN = 1;
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;
    private static final float AVATAR_SIZE = 50;
    private static final int MAX_ENERGY = 100;
    private static final int FRUIT_ENERGY = 10;

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


    private int energy;

    private final Renderable idleRenderable, runRenderable, jumpRenderable;
    private final UserInputListener inputListener;
    private boolean jumpReleased;

    public int getEnergy() {
        return energy;
    }

    public Avatar(Vector2 topLeftCorner,
                  UserInputListener inputListener,
                  ImageReader imageReader){
        super(Vector2.ZERO,
                Vector2.ONES.mult(AVATAR_SIZE),
                new AnimationRenderable(IDLE_PATHS, imageReader, true, TIME_BETWEEN_FRAMES));

        this.idleRenderable = this.renderer().getRenderable();
        this.runRenderable = new AnimationRenderable(RUN_PATHS, imageReader, true, TIME_BETWEEN_FRAMES);
        this.jumpRenderable = new AnimationRenderable(JUMP_PATHS, imageReader, false, TIME_BETWEEN_FRAMES);
        this.inputListener = inputListener;

        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);

        this.energy = MAX_ENERGY;
    }

    @Override
    public void update(float deltaTime){
        super.update(deltaTime);
        boolean jump = inputListener.isKeyPressed(KeyEvent.VK_SPACE);
        boolean rightMove = inputListener.isKeyPressed(KeyEvent.VK_RIGHT);
        boolean leftMove = inputListener.isKeyPressed(KeyEvent.VK_LEFT);
        this.jumpReleased = !jump || this.jumpReleased;
        float xVel = 0;
        if(rightMove && !leftMove && (energy >= RUN_COST || this.getVelocity().y() != 0)) {
            if (renderer().getRenderable() != runRenderable) {
                this.renderer().setRenderable(runRenderable);
            }
            renderer().setIsFlippedHorizontally(false);
            xVel += VELOCITY_X;
            if (this.getVelocity().y() == 0) {
                this.energy -= RUN_COST;
            }
        }
        if(leftMove && !rightMove && (energy >= RUN_COST || this.getVelocity().y() != 0)) {
            if (renderer().getRenderable() != runRenderable) {
                this.renderer().setRenderable(runRenderable);
            }
            renderer().setIsFlippedHorizontally(true);
            xVel -= VELOCITY_X;
            if (this.getVelocity().y() == 0) {
                this.energy -= RUN_COST;
            }
        }
        transform().setVelocityX(xVel);
        if(jump) {
            if (getVelocity().y() == 0 && energy >= JUMP_COST) {
                this.renderer().setRenderable(jumpRenderable);
                this.energy -= JUMP_COST;
                transform().setVelocityY(VELOCITY_Y);
            }
            else if (getVelocity().y() != 0 && energy >= DOUBLE_JUMP_COST && this.jumpReleased) {
                this.energy -= DOUBLE_JUMP_COST;
                transform().setVelocityY(VELOCITY_Y);
            }
            this.jumpReleased = false;
        }
        if (transform().getVelocity().equals(Vector2.ZERO) && this.energy <= MAX_ENERGY-IDLE_GAIN) {
            if (renderer().getRenderable() != idleRenderable) {
                this.renderer().setRenderable(idleRenderable);
            }
            this.energy += IDLE_GAIN;
        }
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if ((Objects.equals(other.getTag(), Terrain.BLOCK_TOP_TAG) ||
                Objects.equals(other.getTag(), Terrain.BLOCK_SUB_TAG))) {
            this.transform().setVelocityY(0);
            this.setCenter(this.getCenter().add(Vector2.UP.mult(collision.getPenetrationArea().y())));
        }
        if ((Objects.equals(other.getTag(), Tree.FRUIT_TAG))) {
            if (energy >= MAX_ENERGY - FRUIT_ENERGY){
                energy = MAX_ENERGY;
            }
            else{
                energy += FRUIT_ENERGY;
            }
        }
    }
}
