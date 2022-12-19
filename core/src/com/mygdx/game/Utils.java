package com.mygdx.game;

import com.badlogic.gdx.Gdx;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Utils {
    private static float missleSpeed = 100;
    static private float buttonHeight = 10;
    static private float buttonWidth = 25;
    static private float buttonPadding = 10;
    private static float barrelSpeed = 0.5f;
    static private float TIME_STEP = 1 / 60f;
    static private int VELOCITY_ITERATIONS = 6;
    static private int POSITION_ITERATIONS = 2;
    static private float height = 720;
    static private float width = 1280;
    static private float accumulator;

    public static float getAccumulator() {
        return accumulator;
    }

    public static void setAccumulator(float accumulator) {
        Utils.accumulator = accumulator;
    }

    public static float getButtonHeight() {
        return buttonHeight;
    }
    public static float getButtonWidth() {
        return buttonWidth;
    }

    public static void writes(Serializable st) {
        try {
            FileOutputStream fs = new FileOutputStream("output1");
            ObjectOutputStream inp = new ObjectOutputStream(fs);
            inp.writeObject(st);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static float getButtonPadding() {
        return buttonPadding;
    }

    public static float getTimeStep() {
        return TIME_STEP;
    }

    public static int getVelocityIterations() {
        return VELOCITY_ITERATIONS;
    }

    public static int getPositionIterations() {
        return POSITION_ITERATIONS;
    }

    public static float getHeight() {
        return height;
    }

    public static float getWidth() {
        return width;
    }

    public static float getMissleSpeed() {
        return missleSpeed;
    }

    public static float getBarrelSpeed() {
        return barrelSpeed;
    }
}
