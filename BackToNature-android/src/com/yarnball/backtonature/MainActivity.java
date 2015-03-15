package com.yarnball.backtonature;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		// use false for debugging
		cfg.useGL20 = true;
		cfg.useWakelock = true;

		initialize(new BackToNatureMain(), cfg);
	}
}