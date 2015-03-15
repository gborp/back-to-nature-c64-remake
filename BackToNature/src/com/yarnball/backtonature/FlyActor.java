package com.yarnball.backtonature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FlyActor extends Actor {

	private static final int SOUND_INTERVAL = 11;
	private static final int SOUND_INTERVAL_MAX_EXTRA = 5;

	private static final int MIN_X = 136;
	private static final int MAX_X = 250;

	private static final int MIN_Y = 0;
	private static final int MAX_Y = 192;

	private Sprite sprite;
	private TextureRegion regFly;
	private TextureRegion regFlyHit;
	private Sound sndFLy;

	private int soundCountDown = SOUND_INTERVAL;
	private float speed;
	private float moveCountDown;
	private boolean hit;

	private int bornCounter;
	private TextureRegion regFlyBorn1;
	private TextureRegion regFlyBorn2;
	private TextureRegion regFlyBorn3;

	public FlyActor() {
		Texture txtFly = new Texture(Gdx.files.internal("data/fly.png"));
		txtFly.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		regFly = new TextureRegion(txtFly, 0, 0, 8, 8);
		regFlyBorn1 = new TextureRegion(txtFly, 0, 24, 8, 8);
		regFlyBorn2 = new TextureRegion(txtFly, 0, 16, 8, 8);
		regFlyBorn3 = new TextureRegion(txtFly, 0, 8, 8, 8);

		Texture txtFlyHit = new Texture(Gdx.files.internal("data/flyhit.png"));
		txtFlyHit.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		regFlyHit = new TextureRegion(txtFlyHit, 0, 0, 24, 24);

		sndFLy = Gdx.audio.newSound(Gdx.files.internal("data/fly.mp3"));
		sprite = new Sprite(regFly);
		setVisible(false);
	}

	public void born() {
		bornCounter = 3;
		setFigure();
		float newX = (float) (MIN_X + Math.random() * (MAX_X - MIN_X));
		float newY;
		if (Math.random() > 0.5f) {
			newY = MAX_Y - 16;
		} else {
			newY = MIN_Y + 16;
		}
		setPosition(newX, newY);
		setHit(false);
		setVisible(true);
	}

	private void setFigure() {
		switch (bornCounter) {
		case 3:
			sprite.setRegion(regFlyBorn1);
			break;
		case 2:
			sprite.setRegion(regFlyBorn2);
			break;
		case 1:
			sprite.setRegion(regFlyBorn3);
			break;
		case 0:
			sprite.setRegion(regFly);
			break;
		}
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if (hit) {
			sprite.setPosition(getX() - 8, getY() - 8);
		} else {
			sprite.setPosition(getX(), getY());
		}
		sprite.draw(batch, parentAlpha);
	}

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
		if (hit) {
			sprite.setRegion(regFlyHit);
			sprite.setSize(24, 24);
		} else {
			sprite.setSize(8, 8);
			sprite.setRegion(regFly);
		}
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void tick() {

		if (bornCounter > 0) {
			bornCounter--;
			setFigure();
		}

		if (hit) {
			return;
		}

		if (Math.random() > 0.5) {

			float newX = getX();
			float newY = getY();

			if (Math.random() > 0.5) {
				newX += 1;
			} else {
				newX -= 1;
			}

			if (Math.random() > 0.5) {
				newY += 1;
			} else {
				newY -= 1;
			}

			if (newX >= MIN_X && newX <= MAX_X) {
				setX(newX);
			}

			if (newY >= MIN_Y && newY <= MAX_Y) {
				setY(newY);
			}
		}

		if (moveCountDown < 1) {
			float newX = getX();
			float newY = getY();

			if (Math.random() > 0.5) {
				newX += 8;
			} else {
				newX -= 8;
			}

			float verticalChance = 0.3f;
			if (newY > 100 && newY < 150) {
				verticalChance = 0.5f;
			} else if (newY > 150) {
				verticalChance = 0.7f;
			}

			if (Math.random() > verticalChance) {
				newY += 8;
			} else {
				newY -= 8;
			}

			if (newX >= MIN_X && newX <= MAX_X) {
				setX(newX);
			}

			if (newY >= MIN_Y && newY <= MAX_Y) {
				setY(newY);
			}

			soundCountDown--;
			if (soundCountDown < 0) {
				soundCountDown = (int) (SOUND_INTERVAL + Math.random()
						* SOUND_INTERVAL_MAX_EXTRA);
				if (!EngineState.isMute()) {
					sndFLy.play(0.6f);
				}
			}

			moveCountDown += speed;
		} else {
			moveCountDown--;
		}
	}

}
