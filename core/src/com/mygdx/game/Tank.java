package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import jdk.internal.org.jline.reader.Widget;

public class Tank {
    private Body body, barrelBody;
    private BodyDef bodyDef;
    private Texture texture, barrelTexture;
    private TextureRegion textRegion, barrelTextureRegion;
    private float length, breadth, density;
    private float[] points, flipPoints;
    private FixtureDef tankfixdef, wheelfixdef;
    private CircleShape wheels;
    private Body[] wheel;
    private WheelJoint[] jointarr;
    private WheelJoint barrelJoint;
    private WheelJointDef joints;
    private int numWheel;
    private PolygonShape shape, barrelShape;
    private float x, y;
    private boolean flip;
    Tank(float x, float y, int type, boolean flip) {
        bodyDef = new BodyDef();
        this.x = x; this.y = y;
        bodyDef.position.set(x, y);
//        bodyDef.fixedRotation = true;
        numWheel = 5;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        if (type >= 1 && type <= 5) {
            texture = new Texture("TANKS/tank" + type + ".png");
            barrelTexture = new Texture("TANKS/barrel" + type + ".png");
        }
        else {
            texture = new Texture("TANKS/tank2.png");
            barrelTexture = new Texture("TANKS/barrel2.png");
        }
        textRegion = new TextureRegion(texture);
        barrelTextureRegion = new TextureRegion(barrelTexture);
        points = new float[]{10, 10, 80, 10, 80, 20, 35, 45, 10, 45, 10, 10};
        this.flip = flip;
        flipPoints = new float[]{10, 10, 80, 10, 80, 45, 55, 45, 10, 20, 10, 10};
        shape = new PolygonShape();
        if (flip) {
            shape.set(flipPoints);
            textRegion.flip(true, false);
        }
        else shape.set(points);
        wheels = new CircleShape();
        wheels.setRadius(10);
        joints = new WheelJointDef();
        wheelfixdef = new FixtureDef();
        barrelShape = new PolygonShape();
        barrelShape.setAsBox(40, 5);
        wheelfixdef.shape = wheels;
        wheelfixdef.restitution = 1f;
        wheelfixdef.density = 5f;
        wheelfixdef.friction = 50f;
        wheel = new Body[numWheel];
        length = 80;
        breadth = 45;
        density = 5f;
        jointarr = new WheelJoint[numWheel];
    }
    Tank(float x, float y, boolean flip) {
        this(x, y, 3, flip);
    }
    public void render(World world) {
        body = world.createBody(bodyDef);
        body.setGravityScale(10);
        tankfixdef = new FixtureDef();
        tankfixdef.shape = shape;
        tankfixdef.density = density;
        tankfixdef.restitution = 1f;
        tankfixdef.friction = 50f;
        body.createFixture(tankfixdef);
        body.setGravityScale(10f);
        joints.bodyA = body;
        joints.dampingRatio = 10f;
        joints.maxMotorTorque = 100000000;
        joints.enableMotor = true;
        if (flip) {
            bodyDef.position.set(x, y + breadth);
            joints.localAnchorB.set(new Vector2(30, 0));
            joints.localAnchorA.set(new Vector2(length * 0.5f, breadth * 0.9f));
        } else {
            bodyDef.position.set(x + length, y + breadth);
            joints.localAnchorB.set(new Vector2(-30, 0));
            joints.localAnchorA.set(new Vector2(length * 0.65f, breadth * 0.9f));
        }
        barrelBody = world.createBody(bodyDef);
        bodyDef.position.set(x, y);
        barrelBody.createFixture(barrelShape, density);
        barrelBody.setGravityScale(10);
        joints.bodyB = barrelBody;
        barrelJoint = (WheelJoint) world.createJoint(joints);
        joints.frequencyHz = density;
        for (int i = 0; i < numWheel; ++i) {
            wheel[i] = world.createBody(bodyDef);
            wheel[i].createFixture(wheels, density);
            wheel[i].setGravityScale(10);
        }
        joints.localAxisA.set(Vector2.Y);
        joints.localAnchorB.set(0, 0);
        joints.localAnchorA.set(-15, 0);
        for (int i = 0; i < numWheel; ++i) {
            joints.bodyB = wheel[i];
            joints.localAnchorA.x += 20;
            jointarr[i] = (WheelJoint) world.createJoint(joints);
        }
    }
    public void move(float deltaX, float deltaY) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            for (WheelJoint t : jointarr) t.setMotorSpeed(6);
            body.setAngularVelocity(-0.1f);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            for (WheelJoint t : jointarr) t.setMotorSpeed(-6);
            body.setAngularVelocity(0.1f);
        } else if (barrelBody.getAngle() > Math.atan(deltaY / deltaX)) {
            barrelJoint.setMotorSpeed(1);
        } else if (barrelBody.getAngle() < Math.atan(deltaY / deltaX)) {
            barrelJoint.setMotorSpeed(-1);
        } else {
            barrelJoint.setMotorSpeed(0);
            body.setAngularDamping(0);
            for (WheelJoint t : jointarr) t.setMotorSpeed(0);
        }
    }
    public void update(SpriteBatch batch) {
        if (flip) {
            batch.draw(textRegion, body.getPosition().x - 20, body.getPosition().y - 5, 20, 0, length, breadth, 1.5f, 1.5f, (float) Math.toDegrees(body.getAngle()));
            batch.draw(barrelTextureRegion, barrelBody.getPosition().x - 30, barrelBody.getPosition().y - 5, 30, 5, 60, 10, 1.5f, 1.5f, (float) Math.toDegrees(barrelBody.getAngle()) - 180);
        }
        else {
            batch.draw(textRegion, body.getPosition().x, body.getPosition().y - 5, 0, 0, length, breadth, 1.5f, 1.5f, (float) Math.toDegrees(body.getAngle()));
            batch.draw(barrelTextureRegion, barrelBody.getPosition().x - 30, barrelBody.getPosition().y - 5, 30, 5, 60, 10, 1.5f, 1.5f, (float) Math.toDegrees(barrelBody.getAngle()));
        }
    }
}
