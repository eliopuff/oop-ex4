package pepse.world;

import danogl.util.Vector2;

import java.util.List;

public class Terrain {
    private static final int TERRAIN_DEPTH = 20;

    private float groundHeightAtX0;

    public Terrain(Vector2 windowDimensions, int seed){assert false;}

    public float groundHeightAt(float x) {
        return groundHeightAtX0;
    }

    public List<Block> createInRange(int minX, int maxX) {
        assert false;
        return null;
    }
}