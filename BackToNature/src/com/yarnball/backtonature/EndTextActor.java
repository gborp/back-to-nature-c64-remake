package com.yarnball.backtonature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EndTextActor extends Actor {

	private Sprite sprite;
	private TextureRegion reg1;
	private TextureRegion reg2;
	private TextureRegion reg3;
	private TextureRegion reg4;
	private TextureRegion reg5;

	public EndTextActor() {
		Texture txt1 = new Texture(Gdx.files.internal("data/end1.png"));
		txt1.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		reg1 = new TextureRegion(txt1, 72, 8);

		Texture txt2 = new Texture(Gdx.files.internal("data/end2.png"));
		txt2.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		reg2 = new TextureRegion(txt2, 88, 8);

		Texture txt3 = new Texture(Gdx.files.internal("data/end3.png"));
		txt3.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		reg3 = new TextureRegion(txt3, 178, 8);

		Texture txt4 = new Texture(Gdx.files.internal("data/end4.png"));
		txt4.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		reg4 = new TextureRegion(txt4, 232, 8);

		Texture txt5 = new Texture(Gdx.files.internal("data/end5.png"));
		txt5.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		reg5 = new TextureRegion(txt5, 272, 32);

		sprite = new Sprite(reg1);
		sprite.setPosition(108, 200 - 32);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		sprite.draw(batch, parentAlpha);
	}

	public void setEndText(int id) {
		TextureRegion reg = null;
		if (id == 1) {
			reg = reg1;
		} else if (id == 2) {
			reg = reg2;
		} else if (id == 3) {
			reg = reg3;
		} else if (id == 4) {
			reg = reg4;
		} else if (id == 5) {
			reg = reg5;
		}

		int w = reg.getRegionWidth();
		int h = reg.getRegionHeight();

		sprite.setRegion(reg);
		sprite.setSize(w, h);
		sprite.setPosition(0 + ((320 - 0) - w) / 2, 112 + (32 - h) / 2);
	}
}
