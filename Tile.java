package com.sshdev.memorywars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Tile {
    private int mTerrainType;
    private int mMovementCost;
    private Texture mTexture;
    private boolean mIsPassable;
    
    public String toString() {
        if (mTerrainType == 1) {
            return "WATER TILE";
        }
        else if (mTerrainType == 2) {
            return "DIRT TILE";
        }
        else if (mTerrainType == 3) {
            return "BRIDGE";
        }
        else {
            return "MISC TILE";
        }
    }
    
    public void setTerrainType(int type) {
        mTerrainType = type;
    }
    
    public Tile() {
        mTerrainType = 0;
        mMovementCost = 1;
        mIsPassable = true; // All tiles passable by default
        mTexture = new Texture(Gdx.files.internal("grass.png"));
    }
    
    public Tile(Texture tileImage) {
        mTerrainType = 0;
        mMovementCost = 1;
        mTexture = tileImage;
    }
    
    public Texture getImage() {
        return mTexture;
    }
    
    public void setPassable(boolean flag) {
        mIsPassable = flag;
    }
}
