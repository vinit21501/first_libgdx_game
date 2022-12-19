package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

import java.io.Serializable;

public class Tank implements Serializable {
    private transient Body body, barrelBody;
    private transient BodyDef bodyDef;
    private transient Texture texture, barrelTexture;
    private transient TextureRegion textRegion, barrelTextureRegion;
    private float length, breadth, density;
    private float[] points, flipPoints;
    private transient FixtureDef tankfixdef, wheelfixdef;
    private transient CircleShape wheels;
    private transient Body[] wheel;
    private transient WheelJoint[] jointarr;
    private transient WheelJoint barrelJoint;
    private transient WheelJointDef joints;
    private int numWheel, type;
    private transient PolygonShape shape, barrelShape;
    private float x, y, power, barrelX, barrelY;
    private boolean flip, fliping;
    Tank(float x, float y, int type, boolean flip) {
        this.x = x; this.y = y;
        numWheel = 5;
        this.type = type;
        this.flip = flip;
        fliping = true;
        bodyDef = new BodyDef();
        points = new float[]{10, 10, 80, 10, 80, 20, 35, 45, 10, 45, 10, 10};
        flipPoints = new float[]{10, 10, 80, 10, 80, 45, 55, 45, 10, 20, 10, 10};
        bodyDef.position.set(x, y);
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
        shape = new PolygonShape();
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
        if (flip) {
            textRegion.flip(true, false);
            shape.set(flipPoints);
        }
        else shape.set(points);
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
        bodyDef.position.set(x, y + 10);
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
    public void move(Touchpad touchpad, float moved) {
        if (touchpad.isTouched()) {
            power = (float) Math.sqrt(Math.pow(touchpad.getKnobPercentX(), 2) + Math.pow(touchpad.getKnobPercentY(), 2));
        }
        if (moved > 0) {
            if (flip) {
                for (WheelJoint t : jointarr) t.setMotorSpeed(6);
                body.setAngularVelocity(-Utils.getBarrelSpeed());
            } else {
                for (WheelJoint t : jointarr) t.setMotorSpeed(-6);
                body.setAngularVelocity(Utils.getBarrelSpeed());
            }
        } else if (moved < 0) {
            if (flip) {
                for (WheelJoint t : jointarr) t.setMotorSpeed(-6);
                body.setAngularVelocity(Utils.getBarrelSpeed());
            } else {
                for (WheelJoint t : jointarr) t.setMotorSpeed(6);
                body.setAngularVelocity(-Utils.getBarrelSpeed());
            }
        } else if (touchpad.getKnobPercentX() > 0) {
            if (flip) {
                fliping = true;
                flip = false;
            }
            calculateAngle(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());
        } else if (touchpad.getKnobPercentX() < 0) {
            if (!flip) {
                flip = true;
                fliping = true;
            }
            calculateAngle(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());
        } else {
            barrelJoint.setMotorSpeed(0);
            body.setAngularDamping(0);
            for (WheelJoint t : jointarr) t.setMotorSpeed(0);
        }
    }
    private void calculateAngle(float delX, float delY) {
        if (Math.atan(delY / delX) < barrelBody.getAngle()) {
            barrelJoint.setMotorSpeed(-Utils.getBarrelSpeed());
        } else {
            barrelJoint.setMotorSpeed(Utils.getBarrelSpeed());
        }
    }
    public float getAngle() {
        if (flip) {
            if (barrelBody.getAngle() > 0) {
                return (float) Math.toDegrees((Math.PI / 2) - barrelBody.getAngle());
            } else {
                return (float) Math.toDegrees(barrelBody.getAngle() + (Math.PI / 2));
            }
        } else {
            if (barrelBody.getAngle() > 0) {
                return -(float) Math.toDegrees((Math.PI / 2) - barrelBody.getAngle());
            } else {
                return -(float) Math.toDegrees(-barrelBody.getAngle() + (Math.PI / 2));
            }
        }
    }
    public float getPower() {
        return power;
    }
    public Vector2 getBarrelPosition() {
        return new Vector2(barrelX, barrelY);
    }
    public void update(MyGdxGame game) {
        if (fliping) {
            if (body != null) {
                game.world.destroyBody(body);
                game.world.destroyBody(barrelBody);
                for (int i = 0; i < numWheel; ++i) {
                    game.world.destroyBody(wheel[i]);
                }
            }
            this.render(game.world);
            fliping = false;
        }
        x = body.getPosition().x;
        y = body.getPosition().y;
        barrelX = barrelBody.getPosition().x;
        barrelY = barrelBody.getPosition().y;
        if (flip) {
            game.batch.draw(textRegion, x - 20, y - 5, 20, 0, length, breadth, 1.5f, 1.5f, (float) Math.toDegrees(body.getAngle()));
            game.batch.draw(barrelTextureRegion, barrelX - 30, barrelY - 5, 30, 5, 60, 10, 1.5f, 1.5f, (float) Math.toDegrees(barrelBody.getAngle()) - 180);
        }
        else {
            game.batch.draw(textRegion, x, y - 5, 0, 0, length, breadth, 1.5f, 1.5f, (float) Math.toDegrees(body.getAngle()));
            game.batch.draw(barrelTextureRegion, barrelX - 30, barrelY - 5, 30, 5, 60, 10, 1.5f, 1.5f, (float) Math.toDegrees(barrelBody.getAngle()));
        }
    }
}
