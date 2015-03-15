package com.yarnball.backtonature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FlyCounterActor extends Actor {

	private static final int X = 8;
	private static final int Y = 200 - 8 * 2;

	private Sprite spriteNumber;
	private Sprite spriteBugs;
	private TextureRegion[] lstNumberTexture;

	/** max 99 */
	private int number;

	public FlyCounterActor() {
		Texture txtNumbers = new Texture(
				Gdx.files.internal("data/fontnumbers.png"));

		lstNumberTexture = new TextureRegion[10];
		for (int i = 0; i < 10; i++) {
			lstNumberTexture[i] = new TextureRegion(txtNumbers, i * 8, 0, 8, 8);
		}

		Texture txtBugs = new Texture(Gdx.files.internal("data/textbugs.png"));

		spriteNumber = new Sprite(lstNumberTexture[0]);
		spriteNumber.setColor(147f / 255f, 81f / 255f, 182f / 255f, 1);
		spriteBugs = new Sprite(txtBugs);
		spriteBugs.setPosition(X + 8 * 3, Y);
		spriteBugs.setColor(147f / 255f, 81f / 255f, 182f / 255f, 1);

	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {

		if ((number / 10) > 0) {
			spriteNumber.setRegion(lstNumberTexture[number / 10]);
			spriteNumber.setPosition(X, Y);
			spriteNumber.draw(batch, parentAlpha);
		}

		spriteNumber.setRegion(lstNumberTexture[number % 10]);
		spriteNumber.setPosition(X + 8, Y);
		spriteNumber.draw(batch, parentAlpha);

		spriteBugs.draw(batch, parentAlpha);
	}

}
