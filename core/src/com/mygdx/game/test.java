package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class test extends ApplicationAdapter {
    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;
    private World world;
    private static final float width = 10;
    private static final float height = 30;
    private static final float camSpeed = 4f;
    private static final float SLICE_WIDTH = 5;
    private Body groundBody;
    private Body aa;
    private static final int PPM = 100;

    private EdgeShape slicePoly;
    private FixtureDef sliceFixture = new FixtureDef();

    float needToCreateStuff;


    private QueryCallback deleteStuff = new QueryCallback() {
        @Override
        public boolean reportFixture(Fixture fixture) {
            final Body tmpBody = fixture.getBody();
            if (tmpBody == groundBody){
                groundBody.destroyFixture(fixture);
            }
            return true;
        }
    };

    @Override
    public void create() {
        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(width, height);
        camera.position.set(width / 2, 0, 0);
        camera.update();

        createPhysics();
    }

    @Override
    public void render() {
        update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render(world, camera.combined);
    }

    private void update(float delta) {
        handleInput(delta);

        world.step(delta, 6, 2);

        final float posX = camera.position.x - width / 1.5f;
        final float posY = camera.position.y;
        world.QueryAABB(deleteStuff, posX, posY - height / 2f, posX + SLICE_WIDTH, posY + height / 2f);
    }

    private void handleInput(float dt){
        float cameraMovement = camSpeed * dt;
        camera.position.x += cameraMovement*1f;
        //camera.position.x = car.getBodyX() * dt;

        generateStuff(cameraMovement, true);
        if(Gdx.input.isKeyPressed(Input.Keys.Z)){
            camera.zoom += .01;
        } else if(Gdx.input.isKeyPressed(Input.Keys.X)){
            generateStuff(cameraMovement, false);
            camera.zoom -= .01;
        }
        //camera.update();
    }

    private void generateStuff(float cameraMovement, boolean direction){
        if(direction){
            needToCreateStuff += cameraMovement;
            if (needToCreateStuff > SLICE_WIDTH) {
                needToCreateStuff -= SLICE_WIDTH;
                createSlice(camera.position.x + width / 2f - needToCreateStuff);
            }
        }
        camera.update();
    }

    private void createPhysics() {
        world = new World(new Vector2(0, -8.91f), true);
        BodyDef bodyDef = new BodyDef();
        groundBody = world.createBody(bodyDef);

        aa = world.createBody(bodyDef);
//        slicePoly = new EdgeShape();
//
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.density = 5;
//        fixtureDef.restitution = .3f;
//
//        FixtureDef wheelFixtureDef = new FixtureDef();
//        wheelFixtureDef.density = fixtureDef.density * 5.5f;
//        wheelFixtureDef.friction = 0;
//        wheelFixtureDef.restitution = .4f;

//        car = new Car(world, fixtureDef, wheelFixtureDef, 0, 3, 3, 1.5f);
//        Gdx.input.setInputProcessor(new InputMultiplexer(new InputAdapter(){}, car));

        for (float x = 0; x <= width; x += SLICE_WIDTH){
            createSlice(x);
        }
    }

    private float nextY;

    private void createSlice(float x) {
        float y = nextY;
        int high = 5;
        float nelygumas = 2 * x;
        float interval = 10 * x;
        nextY = (float) (4f * Math.sin(nelygumas) + high * Math.sin(interval) * 1f);
        slicePoly.set(x, y, x + SLICE_WIDTH, nextY);
        sliceFixture.shape = slicePoly;
        sliceFixture.friction = 10;
        groundBody.createFixture(sliceFixture);
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        slicePoly.dispose();
        world.dispose();
    }

    @Override
    public void resize(int width, int height) {
    }
}