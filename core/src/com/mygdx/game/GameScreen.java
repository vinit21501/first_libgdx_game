package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.awt.*;
import java.security.Key;

public class GameScreen implements Screen {
    private final MyGdxGame myGame;
    private final TextureRegion backGround;
    private final Missile missile;
    private final Terrain platform;
    private final Tank player1;
    private final Tank player2;
    private final Button button;
    private float accumulator;

    public GameScreen(MyGdxGame myGame) {
        this.myGame = myGame;
        platform = new Terrain();
        player1 = new Tank(300, 0, 4, true);
        player2 = new Tank(-200, 0, false);
        backGround = new TextureRegion(new Texture("BACKGROUND/bg6.png"));
        missile = new Missile(-300, 300, 100, 60);
        button = myGame.button;
        accumulator = 0;
        platform.renderBody(myGame.world);
        missile.render(myGame.world);
        player1.render(myGame.world);
        player2.render(myGame.world);
    }

    @Override
    public void show() {
        button.addPauseButton();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        myGame.batch.setProjectionMatrix(myGame.gamCam.combined);
        myGame.gamCam.update();
        myGame.batch.begin();
        myGame.batch.draw(backGround, -Utils.width / 2f, -Utils.height / 2f, Utils.width, Utils.height);
        missile.update(myGame.batch);
        player1.update(myGame.batch);
        platform.update();
//        player1.move();
        player2.update(myGame.batch);
        player2.move();
        myGame.batch.end();
        myGame.polyBatch.begin();
        platform.renderTexture(myGame.polyBatch);
        myGame.polyBatch.end();
        button.render(delta);
        updateWorld();
        myGame.debugRenderer.render(myGame.world, myGame.gamCam.combined);
    }

    @Override
    public void resize(int width, int height) {
        myGame.resize(width, height);
    }
    public void updateWorld() {
        float frameTime = Math.min(Gdx.graphics.getDeltaTime(), 0.25f);
        accumulator += frameTime;
        while (accumulator >= Utils.TIME_STEP) {
            accumulator -= Utils.TIME_STEP;
            myGame.world.step(Utils.TIME_STEP, Utils.VELOCITY_ITERATIONS, Utils.POSITION_ITERATIONS);
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        myGame.world.dispose();
        myGame.debugRenderer.dispose();
        myGame.batch.dispose();
        myGame.button.dispose();
        myGame.polyBatch.dispose();
    }
}
