package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class MainMenu implements Screen {
    OrthographicCamera gamCam;
    final MyGdxGame myGame;
    Texture backGround;
    int SEGMENT_WIDTH;
    World world;
    private Missle missle;
    private Terrain platform;
    private float accumulator;
    Tank player1;
    Box2DDebugRenderer debugRenderer;
    public MainMenu(MyGdxGame myGame) {
        this.myGame = myGame;
        platform = new Terrain();
        player1 = new Tank();
        accumulator = 1;
        gamCam = new OrthographicCamera();
        backGround = new Texture("BACKGROUND/bg1.png");
        missle = new Missle(200, 200, 1000, 60);
    }

    @Override
    public void show() {
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.DynamicBody;
//        bodyDef.position.set(300, 300);
//        Body body = world.createBody(bodyDef);
//        CircleShape circle = new CircleShape();
//        circle.setRadius(6f);
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape = circle;
//        fixtureDef.density = 0.5f;
//        body.createFixture(fixtureDef);
//        body.setLinearVelocity(1000, 0);
//        circle.dispose();
        platform.update();
        platform.render(world);
        missle.render(world);
        player1.render(world);
        missle.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gamCam.update();
        myGame.batch.setProjectionMatrix(gamCam.combined);
        myGame.batch.begin();
        player1.update(myGame.batch);
        player1.move();
        myGame.batch.end();
        debugRenderer.render(world, gamCam.combined);
        update(delta);
    }

    public void update(float deltaTime) {
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= Utils.TIME_STEP) {
            accumulator -= Utils.TIME_STEP;
            world.step(Utils.TIME_STEP, Utils.VELOCITY_ITERATIONS, Utils.POSITION_ITERATIONS);
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
        world.dispose();
    }
}
