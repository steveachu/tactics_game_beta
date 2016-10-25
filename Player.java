package com.sshdev.memorywars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private Animation mAnimation;
    private TextureRegion[] mFrames;
    private Texture mSpriteSheet;
    
    private Rectangle mPlayer;
    private int mArrayPosX, mArrayPosY;    
    
    private final int FRAME_COLS = 6;
    private final int FRAME_ROWS = 1;
    private final int TILE_SIZE = 32;
    
    public Player() {
        // Textures and animations for the player
        mSpriteSheet = new Texture(Gdx.files.internal("player.png"));
        
        TextureRegion[][] tmp = TextureRegion.split(mSpriteSheet, 
         mSpriteSheet.getWidth()/FRAME_COLS, 
         mSpriteSheet.getHeight()/FRAME_ROWS);  
        
        mFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        
        mAnimation = new Animation(0.1f, mFrames);
        
        mPlayer = new Rectangle();
        
        // Set the width and height of the player
        mPlayer.width = TILE_SIZE;
        mPlayer.height = TILE_SIZE;
        
        // This converts the player's (x,y) coordinate 
        // to array representation
        // i.e. coordinates (32, 32) into mWorld[1][1]
        mArrayPosX = (int)mPlayer.x / TILE_SIZE;
        mArrayPosY = (int)mPlayer.y / TILE_SIZE;
    }
    
    public TextureRegion[] getFrames() {
        return mFrames;
    }
    
    public void move(String direction) {
        if (direction.toLowerCase().equals("up")) {
            mArrayPosY += TILE_SIZE;
        }
        else if (direction.toLowerCase().equals("down")) {
            mArrayPosY -= TILE_SIZE;
        }
        else if (direction.toLowerCase().equals("right")) {
            mArrayPosX += TILE_SIZE;
        }
        else if (direction.toLowerCase().equals("left")) {
            mArrayPosX -= TILE_SIZE;
        }
    }
    
    public Animation getAnimation() {
        return mAnimation;
    }
    
    public void setX(int x) {
        mArrayPosX = x;
    }
    
    public void setY(int y) {
        mArrayPosY = y;
    }
    
    public int getX() {
        return mArrayPosX;
    }
    
    public int getY() {
        return mArrayPosY;
    }    
}
