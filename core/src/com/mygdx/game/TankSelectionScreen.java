package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TankSelectionScreen implements Screen {
    private static MyGdxGame myGame;
    private static TextureRegion[] tankTexture;
    private final TextButton.TextButtonStyle buttonStyle;
    private static TextureRegion backGround;
    private static TextButton playerTank, leftButton, rightButton;
    private static TankSelectionScreen ownClass;
    private static int player1, player2;
    private static int tank;
    private static Table table;
    private TextureRegionDrawable up, down;
    private static Stage stage;
    private TankSelectionScreen(MyGdxGame myGame) {
        TankSelectionScreen.myGame = myGame;
        stage = new Stage(new ScreenViewport());
        table = new Table();
        up = new TextureRegionDrawable(new TextureRegion(new Texture("BUTTON/up.png")));
        down = new TextureRegionDrawable(new TextureRegion(new Texture("BUTTON/down.png")));
        stage.addActor(table);
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = myGame.getFont();
        buttonStyle.up = up;
        buttonStyle.down = down;
        stage.addActor(table);
        myGame.getMultiplexer().addProcessor(stage);
        backGround = new TextureRegion(new Texture("BACKGROUND/bg5.png"));
        tankTexture = new TextureRegion[5];
        tank = 1;
        for (int i = 1; i <= 5; ++i) {
            tankTexture[i - 1] = new TextureRegion(new Texture("TANKS/TANK" + i + "F.png"));
        }
    }
    public static TankSelectionScreen getInstances(MyGdxGame myGame) {
        if (ownClass == null) ownClass = new TankSelectionScreen(myGame);
        return ownClass;
    }
    public static void setPlayer1() {
        TankSelectionScreen.player1 = tank;
    }

    public static void setPlayer2() {
        TankSelectionScreen.player2 = tank;
    }

    @Override
    public void show() {
        addLeft();
        addRight();
        addPlayer1Tank();
        addPlayer2Tank();
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.padTop(Gdx.graphics.getHeight() / 1.5f);
        for(Cell t: table.getCells()) {
            t.expandX();
            t.size(Gdx.graphics.getWidth() / 100f * 30, Gdx.graphics.getHeight() / 100f * Utils.getButtonHeight());
        }
        myGame.getBatch().setProjectionMatrix(myGame.getGamCam().combined);
        myGame.getGamCam().update();
        myGame.getBatch().begin();
        myGame.getBatch().draw(backGround, -Utils.getWidth() / 2f , -Utils.getHeight() / 2f, Utils.getWidth(), Utils.getHeight());
        myGame.getBatch().draw(tankTexture[tank - 1],(-Utils.getWidth() / 3f) , -Utils.getHeight() / 3f);
        if (player1 != 0 && player2 != 0) {
                Utils.setTotalLoaded(Utils.getTotalLoaded() + 1);
                Utils.setLoadedNum(Utils.getTotalLoaded());
                myGame.setGameScreen(new GameScreen(myGame, player1, player2));
                player1 = 0; player2 = 0;
                table.reset();
                myGame.setScreen(myGame.getGameScreen());
        }
        myGame.getBatch().end();
        stage.act(delta);
        stage.draw();
    }
    public static void decreaseTank() {
        if (tank == 1) {
            tank = 5;
        } else {
            tank--;
        }
    }
    public static void increaseTank() {
        if (tank == 5) {
            tank = 1;
        } else {
            tank++;
        }
    }
    public void addPlayer1Tank() {
        playerTank = new TextButton("CHOOSE PLAYER 1", buttonStyle);
        table.add(playerTank).align(Align.left).padLeft(50);
        playerTank.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TankSelectionScreen.setPlayer1();
            }
        });
    }
    public void addPlayer2Tank() {
        playerTank = new TextButton("CHOOSE PLAYER 2", buttonStyle);
        table.add(playerTank).align(Align.right).padRight(50).padTop(50).row();
        playerTank.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TankSelectionScreen.setPlayer2();
            }
        });
    }
    public void addLeft() {
        leftButton = new TextButton("<", buttonStyle);
        leftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TankSelectionScreen.decreaseTank();
            }
        });
        table.add(leftButton).align(Align.left).padLeft(50);
    }
    public void addRight() {
        rightButton = new TextButton(">", buttonStyle);
        rightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TankSelectionScreen.increaseTank();
            }
        });
        table.add(rightButton).align(Align.right).padRight(50).row();
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

    }

    @Override
    public void dispose() {
        myGame.getBatch().dispose();
        stage.dispose();
    }
}
