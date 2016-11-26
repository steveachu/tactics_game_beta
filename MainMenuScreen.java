package com.sshdev.memorywars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen {
    final MemoryWars game;
    
    private OrthographicCamera mCamera;
    private Sound mStartSound;
    private Texture mBackgroundImage;
    
    public MainMenuScreen(final MemoryWars gam) {
        game = gam;
        mBackgroundImage = new Texture(Gdx.files.internal("background.png"));
        mStartSound = Gdx.audio.newSound(Gdx.files.internal("start.mp3"));
        
        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, 800, 480);
    }
    
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        mCamera.update();
        game.batch.setProjectionMatrix(mCamera.combined);
        
        game.batch.begin();
        game.batch.draw(mBackgroundImage, delta, delta);
        game.font.draw(game.batch, "Welcome to da game!", 100, 150);
        game.font.draw(game.batch, "Press any key to begin", 100, 100);
        game.batch.end();        
            
        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {            
            mStartSound.play();
            game.setScreen(new GameScreen(game));
            dispose();            
        }
    }

    public void dispose() {

    }

    public void show() {
        
    }
    
    public void resize(int width, int height) {
        
    }

    public void pause() {
        mStartSound.play();            
    }

    public void resume() {

    }

    public void hide() {

    }
}
