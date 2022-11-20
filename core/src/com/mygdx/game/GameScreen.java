package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;

public class GameScreen implements Screen {
    final MyGdxGame myGame;
    Texture backGround;
    private Missle missle;
    private Terrain platform;
    private float accumulator;
    Tank player1;
    public GameScreen(MyGdxGame myGame) {
        this.myGame = myGame;
        platform = new Terrain();
        player1 = new Tank();
        accumulator = 1;
        backGround = new Texture("BACKGROUND/bg1.png");
        missle = new Missle(200, 200, 1000, 60);
    }

    @Override
    public void show() {
        platform.update();
        platform.render(myGame.world);
        missle.render(myGame.world);
        player1.render(myGame.world);
        missle.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        myGame.gamCam.update();
        myGame.batch.setProjectionMatrix(myGame.gamCam.combined);
        myGame.batch.begin();
        player1.update(myGame.batch);
        player1.move();
        myGame.batch.end();
        myGame.debugRenderer.render(myGame.world, myGame.gamCam.combined);
        update(delta);
    }

    public void update(float deltaTime) {
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= Utils.TIME_STEP) {
            accumulator -= Utils.TIME_STEP;
            myGame.world.step(Utils.TIME_STEP, Utils.VELOCITY_ITERATIONS, Utils.POSITION_ITERATIONS);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        myGame.batch.dispose();
        myGame.world.dispose();
    }
}
