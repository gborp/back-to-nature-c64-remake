package com.yarnball.backtonature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class DifficultyLevelTextActor extends Actor {

	private Sprite sprite;
	private TextureRegion reg1;
	private TextureRegion reg2;
	private TextureRegion reg3;

	public DifficultyLevelTextActor() {
		Texture txt1 = new Texture(Gdx.files.internal("data/easytext.png"));
		txt1.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		reg1 = new TextureRegion(txt1, 32, 8);

		Texture txt2 = new Texture(Gdx.files.internal("data/normaltext.png"));
		txt2.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		reg2 = new TextureRegion(txt2, 48, 8);

		Texture txt3 = new Texture(Gdx.files.internal("data/hardtext.png"));
		txt3.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		reg3 = new TextureRegion(txt3, 32, 8);

		sprite = new Sprite(reg1);
		sprite.setPosition(108, 200 - 32);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		sprite.draw(batch, parentAlpha);
	}

	public void setText(DifficultyLevel level) {
		TextureRegion reg = null;
		if (level == DifficultyLevel.EASY) {
			reg = reg1;
		} else if (level == DifficultyLevel.NORMAL) {
			reg = reg2;
		} else if (level == DifficultyLevel.HARD) {
			reg = reg3;
		}

		int w = reg.getRegionWidth();
		int h = reg.getRegionHeight();

		sprite.setRegion(reg);
		sprite.setSize(w, h);
		// sprite.setPosition(32 + (48 - w) / 2, 16);
		sprite.setPosition(32, 8);
	}
}
