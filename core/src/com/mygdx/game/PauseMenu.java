package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PauseMenu implements Screen {
    Button button;
    MyGdxGame myGame;
    TextureRegion backGround;
    PauseMenu(MyGdxGame game) {
        myGame = game;
        backGround = new TextureRegion(new Texture("badlogic.jpg"));
        button = game.button;
        button.addMainMenuButton();
        button.addResumeButton();
        button.addLoadButton();
        button.addExitButton();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        myGame.batch.begin();
        myGame.batch.draw(backGround, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        myGame.batch.end();
        button.render(delta);
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

    }

    @Override
    public void dispose() {
        button.dispose();
    }
}
