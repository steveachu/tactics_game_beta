package com.sshdev.memorywars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
        final MemoryWars game;

        private Texture mPlayerImage;
        private Music mFirstLevelMusic;
	private OrthographicCamera camera;
        private SpriteBatch batch;
        private Rectangle mPlayer;
        private char[][] mWorld;
        
        private final int SCREEN_WIDTH_PX = 800;
        private final int SCREEN_HEIGHT_PX = 480;
        private final int TILE_SIZE = 32;
        private final int FRAME_COLS = 6;
        private final int FRAME_ROWS = 1;
        private final int WATER_COLS = 2;
        private final int WATER_ROWS = 1;
        
        private Sound mSelectionSound;
        
        private Tile mWaterTile;
        private Tile mGrassTile;
        private Tile mDirtTile, mDirtTile2, mDirtTile3;
        private Tile mBridgeTile;
        private Tile mOrangeTreeTile;
        private Tile mMountainTile;
        
        private Animation mPlayerAnimation;
        private Texture mPlayerSheet;
        private TextureRegion[] mPlayerFrames;
        private TextureRegion mCurrentPlayerFrame;
        
        private Animation mWaterAnimation;
        private Texture mWaterSheet;
        private TextureRegion mCurrentWaterFrame;
        private TextureRegion[] mWaterFrames;
        
        private Texture mMenuBar;
        
        private float mStateTime;

    public GameScreen(final MemoryWars gam) {
        this.game = gam;
        // Generate the map
        mWorld  = new char[][] {
            {3,3,3,3,3,3,1,1,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
            {3,3,3,3,3,3,1,1,1,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
            {3,3,4,3,3,3,3,1,1,1,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
            {3,3,3,3,4,3,3,3,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {3,3,4,3,4,6,4,3,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {3,6,6,4,3,3,3,3,6,6,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
            {6,6,6,6,3,3,3,3,6,6,4,1,1,1,0,0,0,0,0,0,0,0,0,0,0},
            {3,3,3,3,4,3,6,3,3,6,6,1,1,1,0,0,0,0,0,0,0,0,0,0,0},
            {6,3,6,4,3,6,6,3,3,3,3,3,1,1,0,0,0,0,0,0,0,0,0,0,0},
            {6,3,6,6,3,3,4,3,3,3,3,6,1,1,1,1,0,0,0,0,0,0,0,0,0},
            {3,4,3,3,3,3,3,3,4,3,3,3,3,1,1,1,1,1,0,0,0,0,0,0,0},
            {3,3,3,3,3,3,6,6,4,3,3,3,3,3,6,1,1,1,1,1,1,1,0,0,0},
            {3,3,3,4,3,3,6,6,4,4,3,3,3,4,3,1,1,1,1,1,1,1,0,0,0},
            {3,3,3,6,3,4,3,3,3,3,6,3,3,3,4,3,6,1,1,1,1,1,1,0,0},
            {3,3,6,6,6,6,3,3,3,3,3,3,6,6,6,6,6,1,1,1,1,1,1,0,0}
        };
        
        mMenuBar = new Texture(Gdx.files.internal("menu_bar.png"));                        
        initSpriteAnimation();
        initTextures();
        
        mSelectionSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        mFirstLevelMusic = Gdx.audio.newMusic(Gdx.files.internal(
         "first_level.mp3"));
        mFirstLevelMusic.setLooping(true);  
        // Create the camera and spritebatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH_PX, SCREEN_HEIGHT_PX);
        
        // Create a rectangle for the bucket hitbox
        mPlayer = new Rectangle();
        mPlayer.x = 64; // center the bucket on x-axis
        mPlayer.y = 67; // bottom left of the bucket is 20px above floor
        
        mPlayer.width = 32;
        mPlayer.height = 32;
    }
    
    private void initTextures() {
        mWaterTile = new Tile(new Texture(Gdx.files.internal("water2.png")));
        mGrassTile = new Tile(new Texture(Gdx.files.internal("grass.png")));
        mDirtTile = new Tile(new Texture(Gdx.files.internal("dirt.png")));
        mDirtTile2 = new Tile(new Texture(Gdx.files.internal("dirt2.png")));
        mDirtTile3 = new Tile(new Texture(Gdx.files.internal("dirt3.png")));
        mBridgeTile = new Tile(new Texture(Gdx.files.internal("bridge.png")));
        mOrangeTreeTile = new Tile(new Texture(Gdx.files.internal("tree.png")));        
        mPlayerImage = new Texture(Gdx.files.internal("player.png"));
    }
    
    private void initSpriteAnimation() {
        mPlayerSheet = new Texture(Gdx.files.internal("player.png"));
        mWaterSheet = new Texture(Gdx.files.internal("water2.png"));
        
        TextureRegion[][] tmp = TextureRegion.split(mPlayerSheet, 
         mPlayerSheet.getWidth()/FRAME_COLS, 
         mPlayerSheet.getHeight()/FRAME_ROWS);  
        
        TextureRegion[][] wtr = TextureRegion.split(mWaterSheet, 
         mWaterSheet.getWidth() / WATER_COLS, mWaterSheet.getHeight() / WATER_ROWS);
        
        mPlayerFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        mWaterFrames = new TextureRegion[WATER_COLS * WATER_ROWS];
        
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                mPlayerFrames[index++] = tmp[i][j];
            }
        }
        
        int jIndex = 0;
        for (int i = 0; i < WATER_ROWS; i++) {
            for (int j = 0; j < WATER_COLS; j++) {
                mWaterFrames[jIndex++] = wtr[i][j];
            }
        }
        
        mStateTime = 0f;
        mPlayerAnimation = new Animation(0.1f, mPlayerFrames);
        mWaterAnimation = new Animation(0.1f, mWaterFrames);
        
    }

    public void render (float delta) {
            // Clear the screen with a dark blue color            
            Gdx.gl.glClearColor(0, 0, 0.2f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            
            camera.update();

            game.batch.setProjectionMatrix(camera.combined);

            // Animating the player
            mStateTime += Gdx.graphics.getDeltaTime();
            mCurrentPlayerFrame = mPlayerAnimation.getKeyFrame(mStateTime, true);
            mCurrentWaterFrame = mWaterAnimation.getKeyFrame(mStateTime, true);
            
            // Animating the water
            
            
            
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            game.batch.begin();
            
            // This method lays out the tiles
            // based on the map array
            generateMap();
            game.batch.draw(mMenuBar, 20, 20);
            game.batch.draw(mCurrentPlayerFrame, mPlayer.x, mPlayer.y);
            game.batch.draw(mPlayerAnimation.getKeyFrame(delta, true), 0, 0);

            game.batch.end();

            checkPlayerMovement();
            checkPlayerBounds();
    }
    
    private void generateMap() {
        for (int i = mWorld.length - 1; i >= 0; i--) {
            for (int j = mWorld[i].length - 1; j >= 0; j--) {
                switch (mWorld[i][j]) {
                    case 0:
                        game.batch.draw(mGrassTile.getImage(), j * TILE_SIZE,
                         i * TILE_SIZE);
                        break;
                    case 1:                        
                        game.batch.draw(mWaterAnimation.getKeyFrame(mStateTime, true), j * TILE_SIZE, i * TILE_SIZE);
                        break;
                    case 2:
                        game.batch.draw(mBridgeTile.getImage(), j* TILE_SIZE, 
                         i * TILE_SIZE);
                        break;
                    case 3:
                        game.batch.draw(mDirtTile.getImage(), j * TILE_SIZE,
                         i * TILE_SIZE);
                        break;
                    case 4:
                        game.batch.draw(mDirtTile2.getImage(), j * TILE_SIZE,
                         i* TILE_SIZE);
                        break;
                    case 5:
                        game.batch.draw(mOrangeTreeTile.getImage(),
                         j * TILE_SIZE, i * TILE_SIZE);
                        break;
                    case 6:
                        game.batch.draw(mDirtTile3.getImage(), j * TILE_SIZE, i * TILE_SIZE);
                        break;
                    default:
                        break;
                }
            }
        }
    }
    
    public void checkPlayerMovement() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            mSelectionSound.play();
            mPlayer.x -= TILE_SIZE;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            mSelectionSound.play();
            mPlayer.x += TILE_SIZE;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            mSelectionSound.play();
            mPlayer.y += TILE_SIZE;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            mSelectionSound.play();
            mPlayer.y -= TILE_SIZE;
        }
    }
    
    public void checkPlayerBounds() {
        if (mPlayer.x < 0) {
            mPlayer.x = 0;
        }

        if (mPlayer.x > 800 - 32) {
            mPlayer.x = 800 - 32;
        }    
    }
    
    public void show() {
        mFirstLevelMusic.play();
    }

    public void resize(int width, int height) {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void hide() {

    }
    
    public void dispose () {
        mPlayerImage.dispose();
        mFirstLevelMusic.dispose();
        batch.dispose();
    }
}
