package com.yarnball.backtonature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MuteActor extends Actor {

	private Sprite sprite;
	private Texture txtMuteOn;
	private Texture txtMuteOff;

	public MuteActor() {
		txtMuteOn = new Texture(Gdx.files.internal("data/muteon.png"));
		txtMuteOn.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		txtMuteOff = new Texture(Gdx.files.internal("data/muteoff.png"));
		txtMuteOff.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		sprite = new Sprite(txtMuteOff);
		setPosition(320 - 64 + 32, 200 - 64 + 32);

	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		sprite.setPosition(getX(), getY());
		sprite.draw(batch, parentAlpha);
	}

	public void setMute(boolean mute) {
		if (mute) {
			sprite.setTexture(txtMuteOn);
		} else {
			sprite.setTexture(txtMuteOff);
		}

	}

}
