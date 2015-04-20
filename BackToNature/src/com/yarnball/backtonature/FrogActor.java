package com.yarnball.backtonature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FrogActor extends Actor {

	private Sprite sprite;
	private TextureRegion regPos1;
	private TextureRegion regPos2;
	private TextureRegion regPos3;
	private int verticalPos;
	private boolean blink;

	public FrogActor() {
		Texture txtPos1 = new Texture(Gdx.files.internal("data/pos1.png"));
		txtPos1.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		regPos1 = new TextureRegion(txtPos1, 0, 0, 80, 126);

		Texture txtPos2 = new Texture(Gdx.files.internal("data/pos2.png"));
		txtPos2.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		regPos2 = new TextureRegion(txtPos2, 0, 0, 80, 126);

		Texture txtPos3 = new Texture(Gdx.files.internal("data/pos3.png"));
		txtPos3.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		regPos3 = new TextureRegion(txtPos3, 0, 0, 80, 126);

		sprite = new Sprite(regPos1);
		setPosition(8, 37);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		sprite.setPosition(getX(), getY());
		sprite.draw(batch, parentAlpha);
	}

	public void setVerticalPos(int verticalPos) {
		this.verticalPos = verticalPos;
		if (verticalPos == 1) {
			sprite.setRegion(regPos1);
		} else if (verticalPos == 2) {
			sprite.setRegion(regPos2);
		} else if (verticalPos == 3) {
			sprite.setRegion(regPos3);
		}
	}

	public int getVerticalPos() {
		return verticalPos;
	}

	public boolean isBlink() {
		return blink;
	}

	public void setBlink(boolean blink) {
		if (this.blink != blink) {
			this.blink = blink;
			// if (blink) {
			// sprite.setColor(1f, 0.5f, 0.5f, 1);
			// } else {
			// sprite.setColor(1f, 1f, 1f, 1);
			// }
		}
	}

	public void resetDefaultPosition() {
		setPosition(8, 37);
	}

}
