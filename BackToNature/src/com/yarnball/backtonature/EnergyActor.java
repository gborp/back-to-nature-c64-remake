package com.yarnball.backtonature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EnergyActor extends Actor {

	private Sprite sprite;
	private Texture txtEnergy;
	private Texture txtEnergyBlink;
	private boolean blink;

	public EnergyActor() {
		txtEnergy = new Texture(Gdx.files.internal("data/energybar.png"));
		txtEnergy.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		txtEnergyBlink = new Texture(
				Gdx.files.internal("data/energybarblink.png"));
		txtEnergyBlink.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		sprite = new Sprite(txtEnergy);
		sprite.setPosition(8, 200 - 32);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		sprite.draw(batch, parentAlpha);
	}

	/**
	 * 0: 0%<br>
	 * 1: 100%
	 */
	public void setEnergy(float energy) {
		sprite.setSize(energy * 7 * 8, 8);
	}

	public void setBlink(boolean blink) {
		if (this.blink != blink) {
			this.blink = blink;
			if (blink) {
				sprite.setTexture(txtEnergyBlink);
			} else {
				sprite.setTexture(txtEnergy);
			}
		}
	}

}
