package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class MainScreen implements Screen {
    MyGdxGame mygame;
    TextureRegion backGround;
    Button button;
    MainScreen(MyGdxGame game) {
        mygame = game;
        backGround = new TextureRegion(new Texture("BACKGROUND/mainMenu.png"));
        button = new Button(game);
        button.addNewGameButton();
        button.addResumeButton();
        button.addLoadButton();
        button.addExitButton();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mygame.batch.begin();
        mygame.batch.draw(backGround, 0, 0, Utils.width, Utils.height);
        mygame.batch.end();
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
