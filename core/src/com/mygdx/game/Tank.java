package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;

public class Tank {
    private Body body;
    private BodyDef bodyDef;
    private Texture texture;
    private TextureRegion textRegion;
    private float length;
    private float breadth;
    private float density;
    private float[] points;
    private FixtureDef tankfixdef;
    private FixtureDef wheelfixdef;
    private CircleShape wheels;
    private Body[] wheel;
    private Joint[] jointarr;
    private WheelJointDef[] joints;
    private int numWheel;
    private PolygonShape shape;
    Tank(float x, float y, int type, boolean flip) {
        bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
//        bodyDef.fixedRotation = true;
        numWheel = 4;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.angularDamping = 3;
        if (type >= 1 && type <= 5) texture = new Texture("TANKS/tank" + type + ".png");
        else texture = new Texture("TANKS/tank2.png");
        textRegion = new TextureRegion(texture);
//        textRegion.flip(flip, false);
//        points = new float[]{0, 0, 81, 0, 81, 44, 0, 44, 0, 0};
        points = new float[]{62.39999055862427f,19.199999570846558f,36.29999399185181f,46.79999828338623f,11.699992418289185f,41.999995708465576f,11.699992418289185f,8.700002431869507f,81.89999341964722f,9.900004863739014f,82.50000715255737f,18.59999656677246f};
        shape = new PolygonShape();
        shape.set(points);
        wheels = new CircleShape();
        wheels.setRadius(10);
        joints = new WheelJointDef[numWheel];
        wheelfixdef = new FixtureDef();
        wheelfixdef.shape = wheels;
        wheelfixdef.restitution = 0.4f;
        wheelfixdef.density = 5f;
        wheelfixdef.friction = 1f;
        wheel = new Body[numWheel];
        length = 81;
        breadth = 44;
        density = 1f;
        jointarr = new Joint[numWheel];
    }
    Tank(float x, float y, boolean flip) {
        this(x, y, 3, flip);
    }
    public void render(World world) {
        body = world.createBody(bodyDef);
        tankfixdef = new FixtureDef();
        tankfixdef.shape = shape;
        tankfixdef.density = 5;
        tankfixdef.restitution = 0.4f;
        tankfixdef.friction = 0.3f;
        body.createFixture(shape, density);
        body.setGravityScale(10f);
//        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(length / 2,breadth / 2);
        for (int i = 0; i < numWheel; ++i) {
            wheel[i] = world.createBody(bodyDef);
            wheel[i].createFixture(wheels, 1f);
        }
        for (int i = 0; i < numWheel; ++i) {
            joints[i] = new WheelJointDef();
            joints[i].bodyA = body;
            joints[i].bodyB = wheel[i];
            joints[i].localAnchorA.set(i * 20 + 20, 0);
            joints[i].frequencyHz = 5f;
//            joints[i].dampingRatio = 1;
            joints[i].localAxisA.set(Vector2.Y);
            joints[i].dampingRatio = 1f;
            joints[i].maxMotorTorque = Integer.MAX_VALUE;
            joints[i].enableMotor = true;
//            joints[i].motorSpeed = -10f;
            jointarr[i] = world.createJoint(joints[i]);
        }
    }
    public void move() {
//        body.setGravityScale(5);
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//            for (WheelJointDef t : joints){
////                body.setLinearVelocity(-100, 0);
//                t.enableMotor = true;
//                t.motorSpeed = -1000000;
//            }
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//            for (WheelJointDef t : joints){
////                body.setLinearVelocity(100, 0);
//                t.enableMotor = true;
//                t.motorSpeed = 10000000;
//            }
//        }
    }
    public void update(SpriteBatch batch) {
//        batch.draw(textRegion, (body.getPosition().x + Utils.width / 2) / 2f, (body.getPosition().y + Utils.height / 2) / 1.5f, length, breadth);
        batch.draw(textRegion, body.getPosition().x, body.getPosition().y - 5,0, 0, length, breadth, 1.5f, 1.5f, (float) Math.toDegrees(body.getAngle()));
    }
}
