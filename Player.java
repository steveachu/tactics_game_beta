package com.sshdev.memorywars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
    private Animation mAnimation;
    private TextureRegion[] mFrames;
    private Texture mSpriteSheet;
    
    private final int FRAME_COLS = 6;
    private final int FRAME_ROWS = 1;

    public Player() {
         mSpriteSheet = new Texture(Gdx.files.internal("player.png"));
        
        TextureRegion[][] tmp = TextureRegion.split(mSpriteSheet, 
         mSpriteSheet.getWidth()/FRAME_COLS, 
         mSpriteSheet.getHeight()/FRAME_ROWS);  
        
        mFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
    }
    
}
