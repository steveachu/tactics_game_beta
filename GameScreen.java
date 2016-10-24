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
        private Tile[][] mWorld;
        private char[][] mPlayerPosition;
        
        private final int SCREEN_WIDTH_PX = 800;
        private final int SCREEN_HEIGHT_PX = 480;
        private final int TILE_SIZE = 32;
        private final int FRAME_COLS = 6;
        private final int FRAME_ROWS = 1;
        private final int WATER_COLS = 2;
        private final int WATER_ROWS = 1;
        private final int MTN_COLS = 3;
        private final int MTN_ROWS = 3;
        
        private Tile mWaterTile;
        private Tile mGrassTile;
        private Tile mDirtTile, mDirtTile2, mDirtTile3;
        private Tile mBridgeTile;
        private Tile mOrangeTreeTile;
               
        private Sound mInvalidSound;
        private Sound mSelectionSound;
        
        private Texture mMountainSheet;
        
        private Animation mPlayerAnimation;
        private Texture mPlayerSheet;
        private TextureRegion[] mPlayerFrames;
        private TextureRegion mCurrentPlayerFrame;
        
        private Animation mWaterAnimation;
        private Texture mWaterSheet;
        private TextureRegion mCurrentWaterFrame;
        private TextureRegion[] mWaterFrames;
        
        private Texture mMtnTexture;
        
        private Texture mMenuBar;
        
        private float mStateTime;

        private int mPlayerX;
        private int mPlayerY;
        
    public GameScreen(final MemoryWars gam) {
        this.game = gam;
        // Generate the map
        /*mWorld  = new short[][] {
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
            {3,4,3,7,6,6,3,3,4,3,3,3,3,1,1,1,1,1,0,0,0,0,0,0,0},
            {3,3,3,6,6,6,6,6,4,3,3,3,3,3,6,1,1,1,1,1,1,1,0,0,0},
            {3,3,3,6,6,6,6,6,4,4,3,3,3,4,3,1,1,1,1,1,1,1,0,0,0},
            {3,3,3,6,3,4,3,3,3,3,6,3,3,3,4,3,6,1,1,1,1,1,1,0,0},
            {3,3,6,6,6,6,3,3,3,3,3,3,6,6,6,6,6,1,1,1,1,1,1,0,0}
        };*/               
        
        mMenuBar = new Texture(Gdx.files.internal("menu_bar.png"));                        
        
        initSpriteAnimation();
        initTextures();
        initSounds(); 
        
        mWorld = new Tile[25][15];
        for (int i = 0; i < mWorld.length; i++) {
            for (int j = 0; j < mWorld[i].length; j++) {
                mWorld[i][j] = mDirtTile;
            }
        }
        
        mWorld[5][5] = mWaterTile;
        mWorld[5][6] = mWaterTile;
        mWorld[5][7] = mWaterTile;
        mWorld[5][8] = mWaterTile;
        mWorld[5][9] = mWaterTile;
        
        /*
        mWorld = new Tile[][] {
            {mWaterTile,mWaterTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile},
            {mDirtTile,mDirtTile,mWaterTile,mWaterTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile},
            {mDirtTile,mDirtTile,mWaterTile,mWaterTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile},
            {mDirtTile,mDirtTile,mWaterTile,mWaterTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile},
            {mDirtTile,mDirtTile,mDirtTile,mDirtTile,mWaterTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile},
            {mDirtTile,mDirtTile,mDirtTile,mDirtTile,mWaterTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile},
            {mDirtTile,mDirtTile,mDirtTile,mDirtTile,mWaterTile,mWaterTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile},
            {mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mWaterTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile},
            {mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mWaterTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile},
            {mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mWaterTile,mWaterTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile},
            {mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mWaterTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile},
            {mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mWaterTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile},
            {mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mWaterTile,mWaterTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile},
            {mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mWaterTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile},
            {mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mWaterTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile,mDirtTile}            
        }; 
        */
        /*
        mWorld = new Tile[][] {
            {mBridgeTile, mDirtTile, mDirtTile},
            {mDirtTile, mWaterTile, mDirtTile},
            {mDirtTile, mWaterTile, mGrassTile} // TOP RIGHT
        };*/
                        
        // Create the camera and spritebatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        // Create a rectangle for the bucket hitbox
        mPlayer = new Rectangle();
        
        mPlayerX = 0;
        mPlayerY = 0;
        
        //mPlayerPosition = new char[][] {{0}};
        //mPlayerPosition[mPlayerX][mPlayerY] = 1;
        
        mPlayer.width = 32;
        mPlayer.height = 32;
    }
    
    private void initSounds() {
        mInvalidSound = Gdx.audio.newSound(Gdx.files.internal("invalid.mp3"));
        mSelectionSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        mFirstLevelMusic = Gdx.audio.newMusic(Gdx.files.internal(
         "first_level.mp3"));
        mFirstLevelMusic.setLooping(true); 
    }
    
    private void initTextures() {        
        mMtnTexture = new Texture(Gdx.files.internal("mountain.png"));
        mGrassTile = new Tile(new Texture(Gdx.files.internal("grass.png")));
        mDirtTile = new Tile(new Texture(Gdx.files.internal("dirt.png")));
        mDirtTile2 = new Tile(new Texture(Gdx.files.internal("dirt2.png")));
        mDirtTile3 = new Tile(new Texture(Gdx.files.internal("dirt3.png")));
        mBridgeTile = new Tile(new Texture(Gdx.files.internal("bridge.png")));
        mOrangeTreeTile = new Tile(new Texture(Gdx.files.internal("tree.png")));        
        mWaterTile = new Tile(new Texture(Gdx.files.internal("water.png")));
        
        mWaterTile.setTerrainType(1);
        mDirtTile.setTerrainType(2);
        mBridgeTile.setTerrainType(3);
        
        mPlayerImage = new Texture(Gdx.files.internal("player.png"));        
    }
    
    private void initSpriteAnimation() {
        mPlayerSheet = new Texture(Gdx.files.internal("player.png"));
        mWaterSheet = new Texture(Gdx.files.internal("water2.png"));
        mMountainSheet = new Texture(Gdx.files.internal("mountain.png"));
        
        TextureRegion[][] tmp = TextureRegion.split(mPlayerSheet, 
         mPlayerSheet.getWidth()/FRAME_COLS, 
         mPlayerSheet.getHeight()/FRAME_ROWS);  
        
        TextureRegion[][] wtr = TextureRegion.split(mWaterSheet, 
         mWaterSheet.getWidth() / WATER_COLS, 
         mWaterSheet.getHeight() / WATER_ROWS);
        

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
            //game.batch.draw(mMenuBar, 20, 20);
            game.batch.draw(mCurrentPlayerFrame, mPlayerX, mPlayerY);
            game.batch.draw(mPlayerAnimation.getKeyFrame(delta, true), 0, 0);

            game.batch.end();

            checkPlayerMovement();
            checkPlayerBounds();
    }
    
    private void generateMap() {
        for (int i = 0; i < mWorld.length; i++) {
            for (int j = 0; j < mWorld[i].length; j++) {
                if (mWorld[i][j].equals(mDirtTile)) {
                    game.batch.draw(mDirtTile.getImage(), i * TILE_SIZE, j * TILE_SIZE);
                }
                else if (mWorld[i][j].equals(mWaterTile)) {
                    game.batch.draw(mWaterAnimation.getKeyFrame(mStateTime, true), i * TILE_SIZE, j * TILE_SIZE);
                }
                else if (mWorld[i][j].equals(mGrassTile)) {
                    game.batch.draw(mGrassTile.getImage(), i * TILE_SIZE, j * TILE_SIZE);
                }
                else if (mWorld[i][j].equals(mBridgeTile)) {
                    game.batch.draw(mBridgeTile.getImage(), i * TILE_SIZE, j * TILE_SIZE);
                }
                
                /*
                
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
                    case 7:
                        game.batch.draw(mMountainSheet, j * TILE_SIZE, i * TILE_SIZE);
                        break;
                    default:
                        break;
                        
                }*/
            }
        }
    }
    
    public void checkPlayerMovement() {  
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {                       
            System.out.println("mPlayerX:" + mPlayerX + ", mPlayerY: " + mPlayerY); 
            System.out.println("Array: " + mWorld[mPlayerX/32][mPlayerY/32]);
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {                       
            if((mPlayerX / TILE_SIZE) - 1 < 0) {
                mInvalidSound.play();
            }            
            else if(mWorld[(mPlayerX / TILE_SIZE) - 1][(mPlayerY / TILE_SIZE)].equals(mWaterTile)) {
                mInvalidSound.play();
            }
            else {
               mSelectionSound.play(); 
               mPlayerX -= TILE_SIZE;      
            }                               
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {                                                          
            // First check if player is at the edge of the map
            if((mPlayerX / TILE_SIZE) + 1 >= SCREEN_WIDTH_PX / TILE_SIZE) {
                mInvalidSound.play();
            }            
            else if(mWorld[(mPlayerX / TILE_SIZE) + 1][(mPlayerY / TILE_SIZE)].equals(mWaterTile)) {
                mInvalidSound.play();
            }
            else {
                mSelectionSound.play(); 
                mPlayerX += TILE_SIZE;
            }            
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if((mPlayerY / TILE_SIZE) + 1 >= SCREEN_HEIGHT_PX / TILE_SIZE) {
                mInvalidSound.play();
            }            
            else if(mWorld[(mPlayerX / TILE_SIZE)][(mPlayerY / TILE_SIZE) + 1].equals(mWaterTile)) {
                mInvalidSound.play();
            }
            else {
                mSelectionSound.play();
                mPlayerY += TILE_SIZE;
            }     
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            if((mPlayerY / TILE_SIZE) - 1 < 0) {
                mInvalidSound.play();
            }            
            else if(mWorld[mPlayerX / TILE_SIZE][(mPlayerY / TILE_SIZE) - 1].equals(mWaterTile)) {
                mInvalidSound.play();
            }
            else {
               mSelectionSound.play();
               mPlayerY -= TILE_SIZE;
            }                         
        }                
    }

    public void checkPlayerBounds() {
        if (mPlayerX < 0) {
            mPlayerX = 0;
        }
        
        if (mPlayerY < 0) {
            mPlayerY = 0;
        }
        
        if (mPlayerY > SCREEN_HEIGHT_PX - TILE_SIZE) {
            mPlayerY = SCREEN_HEIGHT_PX - TILE_SIZE;
        }

        if (mPlayerX > SCREEN_WIDTH_PX - TILE_SIZE) {
            mPlayerX = SCREEN_WIDTH_PX - TILE_SIZE;
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
