package com.yarnball.backtonature;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class BackToNatureDesktopMain {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Back To Nature";
		cfg.useGL20 = false;
		cfg.width = 800;
		cfg.height = 480;

		new LwjglApplication(new BackToNatureMain(), cfg);
	}
}
