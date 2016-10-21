package com.sshdev.memorywars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Tile {
    private int mTerrainType;
    private int mMovementCost;
    private Texture mTexture;
    
    public Tile() {
        mTerrainType = 0;
        mMovementCost = 1;
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
}
