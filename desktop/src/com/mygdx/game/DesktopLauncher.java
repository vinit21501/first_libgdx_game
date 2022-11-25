package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.useVsync(true);
		config.setForegroundFPS(60);
		config.setWindowIcon("logo.png");
		config.setTitle("TANK STARS");
		new Lwjgl3Application(new MyGdxGame(), config);
	}
}
