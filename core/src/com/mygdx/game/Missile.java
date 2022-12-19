package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.*;

public class Missile {
    private final TextureRegion misssleTexture;
    private float speed;
    private boolean remove;
    private float missleHeight, missleWidth, scale, x, y;
    private Body missle;
    private float degrees;
    private PolygonShape shap;
    private boolean right, firing;
    private static boolean destroyed;
    Missile() {
        destroyed = false;
        missleHeight = 5;
        missleWidth = 20;
        scale = 3;
        firing = false;
        remove = false;
        misssleTexture = new TextureRegion(new Texture("WEAPONS/missile1.png"));
    }

    private void setter(Vector2 position, float power, float degrees) {
        this.speed = Utils.getMissleSpeed() * power;
        this.degrees = degrees;
        Vector2 t = new Vector2(40, 5);
        t.rotateDeg(Math.abs(degrees));
        this.x = position.x + t.x;
        this.y = position.y + t.y;
    }

    public static void setDestroyed(boolean destroy) {
        Missile.destroyed = destroy;
    }

    public void render(World world) {
        BodyDef misdef = new BodyDef();
        misdef.type = BodyDef.BodyType.DynamicBody;
        misdef.position.set(x, y);
        misdef.bullet = true;
        missle = world.createBody(misdef);
        shap = new PolygonShape();
        shap.setAsBox(missleWidth, missleHeight);
        missle.createFixture(shap, 0.2f).setUserData("missile");
        shap.dispose();
        Vector2 sped = new Vector2(0, speed);
        sped.rotateDeg(degrees);
        missle.setLinearVelocity(sped);
        Utils.setAccumulator(0.05f);
    }
    public void update(MyGdxGame myGdxGame, Tank tank) {
        if (firing) {
            x = missle.getPosition().x;
            y = missle.getPosition().y;
            missle.setTransform(missle.getPosition(), (float) Math.atan(missle.getLinearVelocity().y / missle.getLinearVelocity().x));
            if (right)
                myGdxGame.batch.draw(misssleTexture, x - missleWidth / 2f, y - missleHeight / 2f, missleWidth / 2f, missleHeight / 2f, missleWidth, missleHeight, 3f, 3f, (float) Math.toDegrees(missle.getAngle()));
            else
                myGdxGame.batch.draw(misssleTexture, x - missleWidth / 2f, y - missleHeight / 2f, missleWidth / 2f, missleHeight / 2f, missleWidth, missleHeight, 3f, 3f, (float) Math.toDegrees(missle.getAngle()) + 180);
            if (destroyed) {
                myGdxGame.world.destroyBody(missle);
                firing = false;
                destroyed = false;
                Utils.setAccumulator(0.005f);
                GameScreen.setFired(false);
            }
        } else {
            this.setter(tank.getBarrelPosition(), tank.getPower(), tank.getAngle());
            this.render(myGdxGame.world);
            firing = true;
        }
    }
}
