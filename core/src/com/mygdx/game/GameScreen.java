package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.awt.*;

public class GameScreen implements Screen {
    private final MyGdxGame myGame;
    private final TextureRegion backGround;
    private final Missile missile;
    private final Terrain platform;
    private final Tank player1;
    private final Tank player2;
    private final Button button;
    public GameScreen(MyGdxGame myGame) {
        this.myGame = myGame;
        platform = new Terrain();
        player1 = new Tank(300, 0, 4, true);
        player2 = new Tank(-200, 0, false);
        backGround = new TextureRegion(new Texture("BACKGROUND/bg6.png"));
        missile = new Missile(-300, 300, 100, 60);
        button = myGame.button;
        button.addPauseButton();
    }

    @Override
    public void show() {
        platform.renderBody(myGame.world);
        missile.render(myGame.world);
        player1.render(myGame.world);
        player2.render(myGame.world);
    }

    @Override
    public void render(float delta) {
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
        myGame.debugRenderer.render(myGame.world, myGame.gamCam.combined);
    }

    @Override
    public void resize(int width, int height) {
        myGame.resize(width, height);
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
