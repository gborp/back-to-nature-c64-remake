package com.yarnball.backtonature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class IntroTextActor extends Actor {

	private Color[] colors;
	private int colorAnimPhase;
	private Sprite sprite;
	private TextureRegion regLine1;
	private TextureRegion regLine2;
	private int colorChangeWait;
	private int displayLine;

	public IntroTextActor() {
		Texture texture = new Texture(Gdx.files.internal("data/introtext.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		regLine1 = new TextureRegion(texture, 0, 0, 272, 8);
		regLine2 = new TextureRegion(texture, 0, 8, 272, 8);

		sprite = new Sprite(regLine1);
		sprite.setPosition(8, 200 - 16);

		colors = new Color[6];
		colors[0] = new Color(0f / 255f, 0f / 255f, 0f / 255f, 1);
		colors[1] = new Color(96f / 255f, 96f / 255f, 96f / 255f, 1);
		colors[2] = new Color(146f / 255f, 74f / 255f, 64f / 255f, 1);
		colors[3] = new Color(179f / 255f, 179f / 255f, 179f / 255f, 1);
		colors[4] = new Color(213f / 255f, 223f / 255f, 124f / 255f, 1);
		colors[5] = new Color(255f / 255f, 255f / 255f, 255f / 255f, 1);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		sprite.draw(batch, parentAlpha);
	}

	private void setColorAnimPhase() {
		if (colorAnimPhase < 6) {
			sprite.setColor(colors[colorAnimPhase]);
		} else {
			sprite.setColor(colors[5 - (colorAnimPhase - 6)]);
		}
	}

	public void tick() {
		colorChangeWait--;
		if (colorChangeWait < 0) {
			colorAnimPhase++;
			if (colorAnimPhase == 12) {
				colorAnimPhase = 0;
				displayLine++;
				if (displayLine > 1) {
					displayLine = 0;
				}
				if (displayLine == 0) {
					sprite.setRegion(regLine1);
				} else if (displayLine == 1) {
					sprite.setRegion(regLine2);
				}
			}
			setColorAnimPhase();
			colorChangeWait = 5;
		}
	}
}
