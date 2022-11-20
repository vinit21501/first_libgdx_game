package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class MainScreen implements Screen {
    TextButton.TextButtonStyle buttonStyle;
    TextButton playButton;
    MyGdxGame mygame;
    TextureRegion up;
    TextureRegion down;
    Stage stage;
    TextureRegion backGround;
    Table table;
    MainScreen(MyGdxGame game) {
        mygame = game;
        up = new TextureRegion(new Texture("BUTTON/up.png"));
        down = new TextureRegion(new Texture("BUTTON/down.png"));
        table = new Table();
        table.setWidth(Utils.width);
        table.setHeight(Utils.height);
        table.align(Align.center);
//        table.setPosition(Utils.width / 2, Utils.height / 2, Align.center | Align.right);
        stage = new Stage();
        stage.addActor(table);
        backGround = new TextureRegion(new Texture("BACKGROUND/mainMenu.png"));
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new SpriteDrawable();
        buttonStyle.font = mygame.font;
        buttonStyle.up = new TextureRegionDrawable(up);
        buttonStyle.down = new TextureRegionDrawable(down);
        playButton = new TextButton("PLAY", buttonStyle);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        table.debug();
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mygame.setScreen(new MainScreen(mygame));
                dispose();
            }
        });
        table.add(playButton);
        Gdx.input.setInputProcessor(stage);
        stage.act(delta);
        stage.draw();
//        mygame.batch.begin();
//        mygame.batch.end();
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
        stage.dispose();
    }
}
