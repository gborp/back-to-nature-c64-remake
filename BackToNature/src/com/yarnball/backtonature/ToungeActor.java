package com.yarnball.backtonature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ToungeActor extends Actor {

	private TextureRegion toungeSection;
	private TextureRegion toungeEnd;
	private TextureRegion toungeEnd2;
	private Sprite sprSection;
	private Sprite sprEnd;
	private Sprite sprEnd2;
	private int toungePos;
	private boolean toungeEndIsUp;

	public ToungeActor() {
		Texture txtPos2 = new Texture(
				Gdx.files.internal("data/tonguesection.png"));
		txtPos2.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		toungeSection = new TextureRegion(txtPos2, 0, 0, 8, 8);

		Texture txtPos1 = new Texture(Gdx.files.internal("data/tongueend.png"));
		txtPos1.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		toungeEnd = new TextureRegion(txtPos1, 0, 0, 8, 8);

		Texture txtPos3 = new Texture(Gdx.files.internal("data/tongueend2.png"));
		txtPos3.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		toungeEnd2 = new TextureRegion(txtPos3, 0, 0, 8, 8);

		sprSection = new Sprite(toungeSection);
		sprEnd = new Sprite(toungeEnd);
		sprEnd2 = new Sprite(toungeEnd2);
	}

	public void setToungeEndIsUp(boolean toungeEndIsUp) {
		this.toungeEndIsUp = toungeEndIsUp;
	}

	public void setToungePos(int toungePos) {
		this.toungePos = toungePos;
	}

	public void setFrogVerticalPos(int toungePos) {
		if (toungePos == 1) {
			setPosition(88, 80);
		} else if (toungePos == 2) {
			setPosition(88, 112);
		} else if (toungePos == 3) {
			setPosition(88, 144);
		}
	}

	public int getToungePos() {
		return toungePos;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if (toungePos > 0) {
			for (int i = 0; i < toungePos; i++) {
				if (i == toungePos - 1) {
					if (toungeEndIsUp) {
						sprEnd2.setPosition(getX() + i * 8, getY());
						sprEnd2.draw(batch, parentAlpha);
					} else {
						sprEnd.setPosition(getX() + i * 8, getY());
						sprEnd.draw(batch, parentAlpha);
					}
				} else {
					sprSection.setPosition(getX() + i * 8, getY());
					sprSection.draw(batch, parentAlpha);
				}
			}
		}
	}

}
