package com.mygdx.game;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils implements Serializable{
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
    private static int totalLoaded = 0, loadedNum = 0;
    private static Utils utils;
    private int totalGame = 0, loadedNumber = 0;
    private static ObjectOutputStream out;
    private static ObjectInputStream inp;
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
    public void setter() {
        totalGame = totalLoaded;
        loadedNumber = loadedNum;
    }
    public void getter() {
        totalLoaded = totalGame;
        loadedNum = loadedNumber;
    }
    public static void writes(Serializable st, int i) {
        try {
            out = new ObjectOutputStream(Files.newOutputStream(Paths.get("output" + i)));
            out.writeObject(st);
            out.close();
            if (utils == null) utils = new Utils();
            utils.setter();
            out = new ObjectOutputStream(Files.newOutputStream(Paths.get("utils")));
            out.writeObject(utils);
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void readGame() {
        try {
            inp = new ObjectInputStream(Files.newInputStream(Paths.get("utils")));
            utils = (Utils) inp.readObject();
            utils.getter();
            inp.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static GameScreen reads(int i) {
        GameScreen t;
        try {
            inp = new ObjectInputStream(Files.newInputStream(Paths.get("output" + i)));
            t = (GameScreen) inp.readObject();
            inp.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return t;
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

    public static int getLoadedNum() {
        return loadedNum;
    }

    public static void setLoadedNum(int loadedNum) {
        Utils.loadedNum = loadedNum;
    }

    public static int getTotalLoaded() {
        return totalLoaded;
    }

    public static void setTotalLoaded(int totalLoaded) {
        Utils.totalLoaded = totalLoaded;
    }
}
