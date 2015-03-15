package com.yarnball.backtonature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class IntroBackgroundActor extends Actor {

	private Sprite sprite;

	public IntroBackgroundActor() {
		Texture txtBackground = new Texture(
				Gdx.files.internal("data/introbackground.png"));
		txtBackground.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		TextureRegion region = new TextureRegion(txtBackground, 0, 0, 320, 200);

		sprite = new Sprite(region);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		sprite.setPosition(getX(), getY());
		sprite.draw(batch, parentAlpha);
	}

}
