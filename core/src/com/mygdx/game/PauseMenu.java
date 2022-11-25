package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PauseMenu implements Screen {
    private Button button;
    private MyGdxGame myGame;
    private TextureRegion backGround;
    PauseMenu(MyGdxGame game) {
        myGame = game;
        backGround = new TextureRegion(new Texture("BACKGROUND/bg1.png"));
        button = myGame.button;
    }

    @Override
    public void show() {
        button.addMainMenuButton();
        button.addResumeButton();
        button.addLoadButton();
        button.addExitButton();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        myGame.batch.setProjectionMatrix(myGame.gamCam.combined);
        myGame.gamCam.update();
        myGame.batch.begin();
        myGame.batch.draw(backGround, -Utils.width / 2f, -Utils.height / 2f, Utils.width, Utils.height);
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
        myGame.batch.dispose();
        button.dispose();
    }
}
