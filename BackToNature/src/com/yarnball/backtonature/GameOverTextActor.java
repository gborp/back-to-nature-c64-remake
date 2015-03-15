package com.yarnball.backtonature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameOverTextActor extends Actor {

	private Sprite sprite;
	private TextureRegion reg1;

	public GameOverTextActor() {
		Texture txt1 = new Texture(Gdx.files.internal("data/gameover.png"));
		txt1.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		reg1 = new TextureRegion(txt1, 72, 8);

		sprite = new Sprite(reg1);
		sprite.setPosition((320 - reg1.getRegionWidth()) / 2, 200 - 32);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		sprite.draw(batch, parentAlpha);
	}
}
